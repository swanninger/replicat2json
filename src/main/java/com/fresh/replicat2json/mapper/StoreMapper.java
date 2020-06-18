package com.fresh.replicat2json.mapper;

import com.fresh.replicat2json.domain.Store;
import com.fresh.replicat2json.model.StoreDTO;

import java.time.LocalDate;

public interface StoreMapper {
    StoreDTO storeToStoreDTO(Store store, LocalDate localDate);
}
