package com.fresh.replicat2json.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "cfgCategoryItembyStore")
public class CategoryItembyStore {
    @Id
    private long categoryItemId;
    private long fkItemId;
    private long fkStoreId;
    private long recordStatus;
    private long owner;
    private String eImporterFileGuid;

}
