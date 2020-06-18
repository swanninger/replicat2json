package com.fresh.replicat2json.services;

import com.fresh.replicat2json.domain.Store;

import java.util.List;

public interface StoreService {

    List<Store> getActiveStores();

    Store getStoreById(Integer id);
}
