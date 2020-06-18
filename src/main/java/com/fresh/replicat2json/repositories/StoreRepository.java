package com.fresh.replicat2json.repositories;

import com.fresh.replicat2json.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Store getByStoreId(Integer id);

    List<Store> getStoresByNameNotLike(String name);

    List<Store> getStoresByNameNotLikeAndStoreIdNotIn(String notName, Collection<Integer> id);
}
