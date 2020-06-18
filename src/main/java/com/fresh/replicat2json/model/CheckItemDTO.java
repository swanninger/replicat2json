package com.fresh.replicat2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckItemDTO {
    private String type = "menuItem";
    private String itemId;
    private String name;
    private String price; //price
    private String fullPrice; //DiscPrice
    private List<CategoryDTO> categories = new LinkedList<>(); //only one

}
