package com.fresh.replicat2json.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckTotalsDTO {
    private String subTotal; //NetSales
    private String taxTotal; //Tax
    private String discountTotal; //promos neg
    private String serviceChargeTotal; //SurCharge
    private String checkTotal; //subtotal + tax + surcharge
    private String tenderTotal; //Payment
}
