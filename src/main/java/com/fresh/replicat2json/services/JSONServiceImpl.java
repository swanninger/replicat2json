package com.fresh.replicat2json.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.domain.Store;
import com.fresh.replicat2json.mapper.CheckMapper;
import com.fresh.replicat2json.mapper.StoreMapper;
import com.fresh.replicat2json.model.StoreDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private final ReplicatConfig replicatConfig;

    public JSONServiceImpl(StoreService storeService, CheckService checkService, StoreMapper storeMapper, CheckMapper checkMapper, ReplicatConfig replicatConfig) {
        this.storeService = storeService;
        this.checkService = checkService;
        this.storeMapper = storeMapper;
        this.checkMapper = checkMapper;
        this.replicatConfig = replicatConfig;

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
            if (store.getStoreId() == 2 || store.getStoreId() == 3) //used for testing since all stores takes some times
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
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store, date); //format store object

        List<Check> checks = checkService.getChecksByStoreAndDate(store.getStoreId(), LocalDateTime.of(date, LocalTime.of(0, 0)));
        for (Check check : checks) {
            storeDTO.getChecks().add(checkMapper.CheckToCheckDTO(check)); //format check and add to store
        }
//        saveToJSONFile("target/" + date.toString() + "_" + store.getStoreId() + ".json", storeDTO);
        return storeDTO;
    }

    private void zipFiles(LocalDate date, List<StoreDTO> storeDTOs) {
        try {
            File f = new File(replicatConfig.getMerchantId() + "_" + date.toString() + ".zip");

            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(f));

            for (StoreDTO store : storeDTOs) {
                ZipEntry e = new ZipEntry(store.getHeader().getStoreCode() + "_" + date.toString() + ".json"); // new file in the zip
                zipOut.putNextEntry(e);

                objectMapper.writeValue(zipOut, store); //write JSON to file
                zipOut.closeEntry();
            }

            zipOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
