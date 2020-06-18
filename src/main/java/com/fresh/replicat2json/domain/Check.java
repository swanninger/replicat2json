package com.fresh.replicat2json.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/*
 * Top level check table
 * */

@Entity
@Table(name = "dpvHstCheckSummary")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Check implements Serializable {
    @Id
    @JsonIgnore
    private long uniqueId;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "fkStoreId", referencedColumnName = "fkStoreId"),
            @JoinColumn(name = "checkNumber", referencedColumnName = "checkId"),
            @JoinColumn(name = "dateOfBusiness", referencedColumnName = "dateOfBusiness")
    })
    @ToString.Include
    private List<CheckItem> checkItems = new LinkedList<>();

    private LocalDateTime dateOfBusiness;

    @ToString.Include
    private int fkStoreId;

    @ToString.Include
    private int checkId;
    private double comps;
    private double itemSales;
    private double promos;
    private double payment;
    private double surCharge;
    private double tax;
    private short openTime;
    private short closeTime;
    private double netSales;

//  private long fkEmployeeNumber;
//  private String tableDescription;
//  private double guestCount;
//  private long itemCount;
//  private double modeCharge;
//  private long firstOrderTime;
//  private long lastOrderTime;
//  private long actualCloseTime;
//  private long checkCloseTime;
//  private long checkTime;
//  private long fkDayPartId;
//  private long fkRevenueId;
//  private double taxExemptAmt;
//  private long lastPaymentTime;
//  private long seatTime;
//  private long lastBumpTime;
//  private double additionalCharges;
//  private double gstTax;
//  private long fkOccasionId;
//  private String importId;
//  private long importCheckSum;
//  private java.sql.Timestamp dateTimeStamp;
//  private double tippableSales;
//  private double tippableChargeSales;
//  private long checkCounter;
//  private String eImporterFileGuid;

}
