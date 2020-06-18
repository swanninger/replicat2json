package com.fresh.replicat2json.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dpvHstGndItem")
@ToString(onlyExplicitlyIncluded = true)
public class CheckItem {
    @Id
    @JsonIgnore
    private long uniqueId;
    private int fkEmployeeNumber;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "fkItemId", referencedColumnName = "itemId", insertable = false, updatable = false),
            @JoinColumn(name = "fkStoreId", referencedColumnName = "fkStoreId", insertable = false, updatable = false)
    })
    @ToString.Include
    private ItemByStore itemName;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "fkCategoryId", referencedColumnName = "categoryId"),
            @JoinColumn(name = "fkStoreId", referencedColumnName = "fkStoreId")
    })
    private Category category;

    @ToString.Include
    private int fkItemId;
    private int parentId;

    @ToString.Include
    private double price;
    private double discPric;

//  private long fkOccasionId;
//  private long fkStoreId;
//  private LocalDateTime dateOfBusiness;
//  private long fkCategoryId;
//  private long entryId;
//  private long type;
//  private long fkOrderModeId;
//  private long fkDayPartId;
//  private long modCode;
//  private String importId;
//  private long hour;
//  private long minute;
//  private long fkTaxId;
//  private long fkRevenueId;
//  private long fkTerminalId;
//  private long menuPanel;
//  private long origin;
//  private long seatNumber;
//  private double quantity;
//  private long importCheckSum;
//  private java.sql.Timestamp dateTimeStamp;
//  private double inclTax;
//  private double exclTax;
//  private java.sql.Timestamp systemDate;
//  private long periodId;
//  private long secondaryTaxId;
//  private long revenueCenterItemOrder;
//  private long conceptItemOrder;
//  private long itemCourseNumber;
//  private String bohControlName;
//  private String bohControlNameParent;
//  private long modifierFunctionTypes;
//  private long qsQuickComboId;
//  private long groupQuickComboId;
//  private long parentModifierId;
//  private long hourItemAdded;
//  private long minuteItemAdded;
//  private long secondItemAdded;
//  private long fohUniqueTableId;
//  private double quantityUnit;
//  private long substituteItemId;
//  private long familyStylePortion;
//  private double orgPrice;
//  private long parentEntryId;
//  private String eImporterFileGuid;

}
