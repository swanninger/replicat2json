package com.fresh.replicat2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private HeaderDTO header;
    private List<CheckDTO> checks = new LinkedList<>();
}
