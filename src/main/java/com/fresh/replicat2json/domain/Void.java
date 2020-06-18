package com.fresh.replicat2json.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/*
*  Contains voided checks
* */

@Entity
@Data
@Table(name = "dpvHstGndVoid")
public class Void {
  @Id
  private long uniqueId;
  private LocalDateTime dateOfBusiness;
  private long fkStoreId;
  private long entryId;
  private long fkEmployeeNumber;
  private long fkManagerNumber;
  private long checkNumber;
  private String tableName;
  private long fkItemId;
  private double price;
  private long hour;
  private long minute;
  private long fkReasonId;
  private String inventory;
  private long fkOccasinId;
  private long importCheckSum;
  private LocalDateTime dateTimeStamp;
  private String eImporterFileGuid;
}
