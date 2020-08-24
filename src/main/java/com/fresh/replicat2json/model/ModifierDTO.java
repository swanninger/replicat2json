package com.fresh.replicat2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifierDTO {
    private String itemId;
    private String name;
    private String price;
    private String fullPrice; //DiscPrice
    private List<CategoryDTO> categories = new LinkedList<>();
}
