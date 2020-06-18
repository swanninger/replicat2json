package com.fresh.replicat2json.mapper;

import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.domain.CheckItem;
import com.fresh.replicat2json.model.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class CheckMapperImpl implements CheckMapper {

    @Override
    public CheckDTO CheckToCheckDTO(Check check) {
        if (check == null) {
            return null;
        } else {
            CheckDTO checkDTO = new CheckDTO();
            checkDTO.setHeader(createHeader(check));
            checkDTO.setItems(createCheckItems(check));
            checkDTO.setTotals(createTotals(check));
            return checkDTO;
        }
    }

    private CheckHeaderDTO createHeader(Check check) {
        CheckHeaderDTO checkHeaderDTO = new CheckHeaderDTO();
        checkHeaderDTO.setNumber(Long.toString(check.getCheckId()));
        checkHeaderDTO.setIsReturn(false);
        String time = Short.toString(check.getOpenTime());

        while (time.length() < 4) { //need to make length for so that substring works correctly.
            time = "0" + time;
        }
        Long minutes = Long.parseLong(time.substring(0, 2)) * 60 + Long.parseLong(time.substring(2)); // convert time string to total minutes
        checkHeaderDTO.setOpenTime(ZonedDateTime.of(check.getDateOfBusiness().plusMinutes(minutes), ZoneId.of("EST5EDT")));
        return checkHeaderDTO;
    }

    private List<CheckItemDTO> createCheckItems(Check check) {
        List<CheckItemDTO> checkItems = new LinkedList<>();
        for (CheckItem checkItem : check.getCheckItems()) {
            if (checkItem.getParentId() == 0) {
                CheckItemDTO checkItemDTO = new CheckItemDTO();
                checkItemDTO.setItemId(Integer.toString(checkItem.getFkItemId()));
                checkItemDTO.setName(checkItem.getItemName().getLongName());
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
        checkTotals.setDiscountTotal(Double.toString(- check.getPromos()));
        checkTotals.setServiceChargeTotal(Double.toString(surcharge));
        checkTotals.setCheckTotal(Double.toString(subtotal + taxTotal + surcharge));
        checkTotals.setTenderTotal(Double.toString(check.getPayment()));

        return checkTotals;
    }
}
