package com.fresh.replicat2json.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@ConfigurationProperties(prefix = "replicat")
@Component
@Getter
@Setter
public class ReplicatConfig {
    List<Integer> storesToSkip;
    List<Integer> regionsToSkip;
    LocalDate dateToRun;
    Integer merchantId;
}
