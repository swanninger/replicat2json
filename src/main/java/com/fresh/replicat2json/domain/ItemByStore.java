package com.fresh.replicat2json.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CfgItemByStore")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class ItemByStore implements Serializable {
  @Id
  private int itemId;
  private int fkStoreId;

  @ToString.Include
  private String longName;


//  private long owner;
//  private long userNumber;
//  private String shortName;
//  private String chitName;
//  private double price;
//  private String bohName;
//  private String abbrevName;
//  private long priority;
//  private long fkRouting;
//  private String printOnCheck;
//  private String neverPrintOnChit;
//  private long printRecipe;
//  private String combineOnCheck;
//  private String highlight;
//  private String surchargeModifier;
//  private double cost;
//  private String askDesc;
//  private String askPrice;
//  private String isRefill;
//  private long fkVrouting;
//  private long isKvi;
//  private String trackFoh;
//  private long slaveToItem;
//  private long defaultWeight;
//  private String sku;
//  private String modItemPrice;
//  private long modPriceId;
//  private double modPrice;
//  private long autoMenuPriority;
//  private long itemMultMod;
//  private double itemPercentMod;
//  private String noGratuity;
//  private long fkCompositeTrackItemId;
//  private long fkSurchargeId;
//  private long fkTaxId;
//  private long fkTax2Id;
//  private long fkVtaxId;
//  private long recordStatus;
//  private String printIndependently;
//  private long fkPriceLevelId;
//  private String printIfHeldItem;
//  private String doNotShowOnVideo;
//  private String showIndependently;
//  private String giftCertificate;
//  private String itemHighlight;
//  private String doNotShowModifierOnVideo;
//  private long delayTime;
//  private long label;
//  private double guests;
//  private long siteAccessFlags;
//  private long fkFlexTaxId;
//  private String cashCard;
//  private long storeAccess;
//  private long parentAccess;
//  private String sku2;
//  private String sku3;
//  private String sku4;
//  private String sku5;
//  private String chitName2;
//  private String longname2;
//  private long mod1;
//  private long mod2;
//  private long mod3;
//  private long mod4;
//  private long mod5;
//  private long mod6;
//  private long mod7;
//  private long mod8;
//  private long mod9;
//  private long mod10;
//  private long ampri;
//  private String adisprecp;
//  private long concept;
//  private long multiplier;
//  private String revitem;
//  private String con1Stmod;
//  private long chitjust;
//  private String chitbold;
//  private long flextax2;
//  private String dntshwsm;
//  private String swtrcksm;
//  private String giftcard;
//  private String gcactivate;
//  private String gcaddvalue;
//  private String tokenover;
//  private long tokenqty;
//  private double guestwght;
//  private String dntshwsmmd;
//  private String usebkclr;
//  private long bkred;
//  private long bkgreen;
//  private long bkblue;
//  private String usetxtclr;
//  private long txtred;
//  private long txtgreen;
//  private long txtblue;
//  private String dispbmp;
//  private String bitmapfile;
//  private String hidetxt;
//  private long ctxpnlid;
//  private String mdspctxpnl;
//  private String pizza;
//  private String topping;
//  private long inittop;
//  private long pizzasize;
//  private String mustbwhole;
//  private String fraction;
//  private long fractype;
//  private long fracprcovr;
//  private long typeId;
//  private String fsItem;
//  private long fsMatrix;
//  private long exportId;
//  private String eImporterFileGuid;

}
