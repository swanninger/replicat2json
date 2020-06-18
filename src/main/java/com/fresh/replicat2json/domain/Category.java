package com.fresh.replicat2json.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "cfgCategoryByStore")
public class Category implements Serializable {
    @Id
    private int categoryId;
    private int fkStoreId;
    private String name;
}
