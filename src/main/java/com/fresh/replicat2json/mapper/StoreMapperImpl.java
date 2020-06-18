package com.fresh.replicat2json.mapper;

import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Store;
import com.fresh.replicat2json.model.HeaderDTO;
import com.fresh.replicat2json.model.StoreDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
public class StoreMapperImpl implements StoreMapper {
    private final ReplicatConfig replicatConfig;

    public StoreMapperImpl(ReplicatConfig replicatConfig) {
        this.replicatConfig = replicatConfig;
    }

    @Override
    public StoreDTO storeToStoreDTO(Store store, LocalDate date) {
        if (store == null) {
            return null;
        } else {
            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setHeader(createHeader(store, date));
            return storeDTO;
        }
    }

    private HeaderDTO createHeader(Store store, LocalDate date) {
        HeaderDTO headerDTO = new HeaderDTO();
        headerDTO.setStoreCode(store.getStoreId());
        headerDTO.setMerchantId(replicatConfig.getMerchantId());
        headerDTO.setBusinessDayStarted(date);
        headerDTO.setFileDate(ZonedDateTime.now());
        return headerDTO;
    }

}
