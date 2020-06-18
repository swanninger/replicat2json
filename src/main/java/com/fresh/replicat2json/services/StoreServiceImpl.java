package com.fresh.replicat2json.services;

import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Store;
import com.fresh.replicat2json.exception.ResourceNotFoundException;
import com.fresh.replicat2json.repositories.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ReplicatConfig replicatConfig;

    public StoreServiceImpl(StoreRepository storeRepository, ReplicatConfig replicatConfig) {
        this.storeRepository = storeRepository;
        this.replicatConfig = replicatConfig;
    }

    @Override
    public List<Store> getActiveStores() {
        return storeRepository.getStoresByNameNotLikeAndStoreIdNotIn("x%", replicatConfig.getStoresToSkip());
    }

    @Override
    public Store getStoreById(Integer id) {
        return storeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }
}