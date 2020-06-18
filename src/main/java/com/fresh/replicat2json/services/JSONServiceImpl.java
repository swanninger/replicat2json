package com.fresh.replicat2json.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.domain.Store;
import com.fresh.replicat2json.mapper.CheckMapper;
import com.fresh.replicat2json.mapper.StoreMapper;
import com.fresh.replicat2json.model.StoreDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class JSONServiceImpl implements JSONService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final StoreService storeService;
    private final CheckService checkService;

    private final StoreMapper storeMapper;
    private final CheckMapper checkMapper;

    public JSONServiceImpl(StoreService storeService, CheckService checkService, StoreMapper storeMapper, CheckMapper checkMapper) {
        this.storeService = storeService;
        this.checkService = checkService;
        this.storeMapper = storeMapper;
        this.checkMapper = checkMapper;
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
        for (Store store : stores) {
//            if (store.getStoreId() == 2) //used for testing since all stores takes some times
            createStoreFile(store, date);
        }
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

    private void createStoreFile(Store store, LocalDate date) {
        StoreDTO storeDTO  = storeMapper.storeToStoreDTO(store, date); //format store object

        List<Check> checks = checkService.getChecksByStoreAndDate(store.getStoreId(), LocalDateTime.of(date, LocalTime.of(0, 0)));
        for (Check check : checks) {
            storeDTO.getChecks().add(checkMapper.CheckToCheckDTO(check)); //format check and add to store
        }
        saveToJSONFile("target/" + date.toString() + "_" + store.getStoreId() + ".json", storeDTO);
    }
}
