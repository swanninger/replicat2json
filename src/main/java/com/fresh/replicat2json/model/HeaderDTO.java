package com.fresh.replicat2json.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderDTO {
    private Integer storeCode;
    private Integer merchantId;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate businessDayStarted;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime fileDate;
}