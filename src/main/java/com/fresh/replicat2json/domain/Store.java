package com.fresh.replicat2json.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "gblStore")
@Data
public class Store {
    @Id
    private int storeId;
    private String name;
    private LocalDateTime lastImportDate;
//    private long owner;
//    private long userNumber;
//    private String netName;
//    private String iberdir;
//    private int fkRegionId;
//    private long recordStatus;
//    private long fkAreaId;
//    private long type;
//    private long polling;
//    private java.sql.Timestamp pollStart;
//    private java.sql.Timestamp pollEnd;
//    private long pollSuccess;
//    private long pollWeekend;
//    private String alohaVersion;
//    private long existing;
//    private long ftp;
//    private long storeAccess;
//    private long parentAccess;
//    private long fkConceptId;
//    private java.sql.Timestamp openDate;
//    private long deleteStore;
//    private String adpBatchId;
//    private String adpBatchDescription;
//    private String address;
//    private String city;
//    private String state;
//    private String zipcode;
//    private long operatingSystem;
//    private java.sql.Timestamp installedDate;
//    private String adminPwd;
//    private String ispName;
//    private String ispLogin;
//    private String ispPassword;
//    private String ispPhone;
//    private String pcaLogin;
//    private String pcaPhone;
//    private String pcaPassword;
//    private String wanPolling;
//    private String tenDigitDialing;
//    private String outsideLine;
//    private String duelPollingSlave;
//    private long openHour;
//    private long closeHour;
//    private String adminUserName;
//    private String storePhone;
//    private String useBackupIsp;
//    private String backupIspName;
//    private String backupIspLogin;
//    private String backupIspPassword;
//    private String backupIspPhone;
//    private long openMinute;
//    private long closeMinute;
//    private String open24HourFlag;
//    private String countryName;
//    private String adpCompanyCode;
//    private long fkAccountingId;
//    private long posSoftwareKeyId;
//    private String importCheckSumEnable;
//    private String pulsePollingEnabled;
//    private long fkStoreTypeId;
//    private long otPremiumThreshold;
//    private String shortStoreName;
//    private String pulseStoreId;
//    private long posType;
//    private String secondaryStoreId;
//    private String atomicLockHst;
//    private long timeZoneId;
//    private long autoRepollExpectedEodHour;
//    private String modifiedByUserId;
//    private java.sql.Timestamp modifiedDate;
//    private String lockEnable;

}
