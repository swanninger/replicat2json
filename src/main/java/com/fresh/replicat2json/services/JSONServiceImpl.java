package com.fresh.replicat2json.services;

import com.Ostermiller.util.CircularByteBuffer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.domain.Store;
import com.fresh.replicat2json.mapper.CheckMapper;
import com.fresh.replicat2json.mapper.StoreMapper;
import com.fresh.replicat2json.model.StoreDTO;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class JSONServiceImpl implements JSONService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final StoreService storeService;
    private final CheckService checkService;

    private final StoreMapper storeMapper;
    private final CheckMapper checkMapper;

    private final ReplicatConfig config;

    public JSONServiceImpl(StoreService storeService, CheckService checkService, StoreMapper storeMapper, CheckMapper checkMapper, ReplicatConfig config) {
        this.storeService = storeService;
        this.checkService = checkService;
        this.storeMapper = storeMapper;
        this.checkMapper = checkMapper;
        this.config = config;

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }

    @Override
    public void saveToJSONFile(String path, Object o) {
        try {
            objectMapper.writeValue(new File(path), o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateCheckFiles(LocalDate date) {
        List<Store> stores = getUpdatedStores(date);
        List<StoreDTO> storeDTOs = new LinkedList<>();
        for (Store store : stores) {
            if (store.getStoreId() == 36) //used for testing since all stores takes some times
            storeDTOs.add(generateStoreDTO(store, date));
        }
        zipFiles(date, storeDTOs);
    }

    private List<Store> getUpdatedStores(LocalDate date) {
        List<Store> stores = new LinkedList<>();

        for (Store store : storeService.getActiveStores()) {
            if (store.getLastImportDate().isAfter(LocalDateTime.of(date, LocalTime.of(0, 0)))) {
                stores.add(store);
            }
        }

        return stores;
    }

    private StoreDTO generateStoreDTO(Store store, LocalDate date) {
        log.info("Generating DTO for " + store.getName());
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store, date); //format store object

        List<Check> checks = checkService.getChecksByStoreAndDate(store.getStoreId(), LocalDateTime.of(date, LocalTime.of(0, 0)));
        for (Check check : checks) {
            storeDTO.getChecks().add(checkMapper.CheckToCheckDTO(check)); //format check and add to store
        }
//        saveToJSONFile("target/" + date.toString() + "_" + store.getStoreId() + ".json", storeDTO);
        log.info(store.getName() + " DTO generated");
        return storeDTO;
    }

    private void zipFiles(LocalDate date, List<StoreDTO> storeDTOs) {
        try {
            CircularByteBuffer cbb = new CircularByteBuffer(CircularByteBuffer.INFINITE_SIZE);

            ZipOutputStream zipOut = new ZipOutputStream(cbb.getOutputStream());

            for (StoreDTO store : storeDTOs) {
                List<StoreDTO> storeList = new LinkedList<>();
                storeList.add(store);

                ZipEntry e = new ZipEntry(store.getHeader().getStoreCode() + "_" + date.toString() + ".json"); // new file in the zip
                zipOut.putNextEntry(e);

                objectMapper.writeValue(zipOut, storeList); //write JSON to file
                zipOut.closeEntry();
            }

            zipOut.close();

            ChannelSftp channel = connectToFtp();
            channel.connect();
            channel.put(cbb.getInputStream(), config.getSftpPath() + config.getMerchantIdFull() + "_" + date.toString() + ".zip");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException | SftpException e) {
            log.error("FTP Failed");
            e.printStackTrace();
        }
    }

    public ChannelSftp connectToFtp() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(config.getJarPath() + "/" + config.getKnownHostsFile());
        jsch.addIdentity(config.getJarPath() + "/" + config.getSftpPrivateKey(), config.getSftpPrivateKeyPass());

        Session session = jsch.getSession(config.getSftpUser(), config.getSftpHost(), config.getSftpPort());
        session.connect();

        return (ChannelSftp) session.openChannel("sftp");
    }
}
