package com.fresh.replicat2json.repositories;

import com.fresh.replicat2json.domain.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findTop10ChecksByDateOfBusiness(LocalDateTime localDateTime);

    List<Check> findChecksByFkStoreIdAndDateOfBusiness(Integer storeId, LocalDateTime localDateTime);

    List<Check> findTop10ChecksByFkStoreIdAndDateOfBusiness(Integer storeId, LocalDateTime localDateTime);
}
