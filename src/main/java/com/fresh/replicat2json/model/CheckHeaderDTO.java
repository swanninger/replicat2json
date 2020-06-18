package com.fresh.replicat2json.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckHeaderDTO {
    private String number; //check number

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime openTime;

    private Boolean isReturn = false;

}