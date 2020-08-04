package com.fresh.replicat2json.mapper;

import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.domain.CheckItem;
import com.fresh.replicat2json.model.*;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class CheckMapperImpl implements CheckMapper {

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

    private List<CheckItemDTO> createCheckItems(Check check) throws Exception{
        List<CheckItemDTO> checkItems = new LinkedList<>();
        for (CheckItem checkItem : check.getCheckItems()) {
            if (checkItem.getParentId() != null && checkItem.getParentId() == 0) {
                CheckItemDTO checkItemDTO = new CheckItemDTO();
                checkItemDTO.setItemId(Integer.toString(checkItem.getFkItemId()));

                try {
                    if (checkItem.getItemName() != null) {
                        checkItemDTO.setName(checkItem.getItemName().getLongName());
                    } else {
                        log.warn("Item " + checkItemDTO.getItemId() + " at location " + check.getFkStoreId() + " missing name");
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
