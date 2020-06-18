package com.fresh.replicat2json.services;

import com.fresh.replicat2json.domain.Check;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckService {
    List<Check> getChecksByStoreAndDate(Integer storeId, LocalDateTime dateOfBusiness);

    List<Check> get10ChecksByStoreAndDate(Integer storeId, LocalDateTime dateOfBusiness);
}
