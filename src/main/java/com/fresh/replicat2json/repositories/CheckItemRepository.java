package com.fresh.replicat2json.repositories;

import com.fresh.replicat2json.domain.CheckItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckItemRepository extends JpaRepository<CheckItem, Long> {
}
