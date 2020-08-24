package com.fresh.replicat2json.mapper;

import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.domain.CheckItem;
import com.fresh.replicat2json.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class CheckMapperImpl implements CheckMapper {
    private final ReplicatConfig config;

    public CheckMapperImpl(ReplicatConfig config) {
        this.config = config;
    }

    @Override
    public CheckDTO CheckToCheckDTO(Check check) {
        if (check == null) {
            return null;
        } else {
            CheckDTO checkDTO = new CheckDTO();
            checkDTO.setHeader(createHeader(check));

            try {
                if (!check.getCheckItems().isEmpty()) {
                    checkDTO.setItems(createCheckItems(check));
                }
            } catch (Exception e) {
                log.error(check.toString());
                e.printStackTrace();
            }

            checkDTO.setTotals(createTotals(check));
            return checkDTO;
        }
    }

    private CheckHeaderDTO createHeader(Check check) {
        CheckHeaderDTO checkHeaderDTO = new CheckHeaderDTO();
        checkHeaderDTO.setNumber(Long.toString(check.getCheckId()));
        checkHeaderDTO.setIsReturn(false);
        String time = Short.toString(check.getOpenTime());

        long minutes;

        switch (time.length()) {
            case 4:
                minutes = Long.parseLong(time.substring(0, 2)) * 60 + Long.parseLong(time.substring(2));
                break;
            case 3:
                minutes = Long.parseLong(time.substring(0, 1)) * 60 + Long.parseLong(time.substring(1));
                break;
            default:
                minutes = Long.parseLong(time);
                break;
        }

//        while (time.length() < 4) { //need to make length for so that substring works correctly.
//            time = "0" + time;
//        }
//        Long minutes = Long.parseLong(time.substring(0, 2)) * 60 + Long.parseLong(time.substring(2)); // convert time string to total minutes
        checkHeaderDTO.setOpenTime(ZonedDateTime.of(check.getDateOfBusiness().plusMinutes(minutes), ZoneId.of("EST5EDT")));
        return checkHeaderDTO;
    }

    private List<CheckItemDTO> createCheckItems(Check check) throws Exception {
        List<CheckItemDTO> checkItems = new LinkedList<>();

        log.info(check.getCheckItems().toString());

        for (CheckItem checkItem : check.getCheckItems()) {
            if (config.getItemsToSkip().contains(checkItem.getFkItemId())) continue;

            if (checkItem.getParentId() == 0) { // then its a base item
                CheckItemDTO checkItemDTO = new CheckItemDTO();
                checkItemDTO.setItemId(Integer.toString(checkItem.getFkItemId()));

                try {
                    if (checkItem.getItemName() != null) {
                        checkItemDTO.setName(checkItem.getItemName().getLongName());
                    } else {
                        log.warn("Item " + checkItem.getFkItemId() + " at location " + check.getFkStoreId() + " missing name");
                        checkItemDTO.setName("null");
//                        log.error(checkItem.toString());
                    }
                } catch (Exception e) {
                    log.error(checkItem.toString());
                    e.printStackTrace();
                }

                checkItemDTO.setPrice(Double.toString(checkItem.getPrice()));
                checkItemDTO.setFullPrice(Double.toString(checkItem.getDiscPric()));

                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(Integer.toString(checkItem.getCategory().getCategoryId()));
                categoryDTO.setName(checkItem.getCategory().getName());
                checkItemDTO.getCategories().add(categoryDTO);

                checkItems.add(checkItemDTO);
            } else if (config.getMods()) { // then its a mod
                ModifierDTO modifierDTO = new ModifierDTO();
                modifierDTO.setItemId(Integer.toString(checkItem.getFkItemId()));

                try {
                    if (checkItem.getItemName() != null) {
                        modifierDTO.setName(checkItem.getItemName().getLongName());
                    } else {
                        log.warn("Item " + checkItem.getFkItemId() + " at location " + check.getFkStoreId() + " missing name");
                        modifierDTO.setName("null");
                    }
                } catch (Exception e) {
                    log.error(checkItem.toString());
                    e.printStackTrace();
                }

                modifierDTO.setPrice(Double.toString(checkItem.getPrice()));
                modifierDTO.setFullPrice(Double.toString(checkItem.getDiscPric()));

                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(Integer.toString(checkItem.getCategory().getCategoryId()));
                categoryDTO.setName(checkItem.getCategory().getName());
                modifierDTO.getCategories().add(categoryDTO);

                try {
                    checkItems.get(checkItems.size() - 1).getModifiers().add(modifierDTO); //add to the last check item's mod list

                } catch (Exception e) {
                    log.warn("Check items: " + checkItems);
                    log.warn(check.toString());
                    e.printStackTrace();
                }
            }
        }

        return checkItems;
    }

    private CheckTotalsDTO createTotals(Check check) {
        CheckTotalsDTO checkTotals = new CheckTotalsDTO();
        double subtotal = check.getNetSales();
        double taxTotal = check.getTax();
        double surcharge = check.getSurCharge();

        checkTotals.setSubTotal(Double.toString(subtotal));
        checkTotals.setTaxTotal(Double.toString(taxTotal));
        checkTotals.setDiscountTotal(Double.toString(-check.getPromos()));
        checkTotals.setServiceChargeTotal(Double.toString(surcharge));
        checkTotals.setCheckTotal(Double.toString(subtotal + taxTotal + surcharge));
        checkTotals.setTenderTotal(Double.toString(check.getPayment()));

        return checkTotals;
    }
}
