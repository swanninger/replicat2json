package com.fresh.replicat2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckDTO {
    private CheckHeaderDTO header;
    private List<CheckItemDTO> items;
    private CheckTotalsDTO totals;
}
