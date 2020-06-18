package com.fresh.replicat2json.services;

import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.repositories.CheckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {
    private final CheckRepository checkRepository;

    public CheckServiceImpl(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @Override
    public List<Check> getChecksByStoreAndDate(Integer storeId, LocalDateTime dateOfBusiness) {
        return checkRepository.findChecksByFkStoreIdAndDateOfBusiness(storeId, dateOfBusiness);
    }

    @Override
    public List<Check> get10ChecksByStoreAndDate(Integer storeId, LocalDateTime dateOfBusiness) {
        return checkRepository.findTop10ChecksByFkStoreIdAndDateOfBusiness(storeId, dateOfBusiness);
    }
}
