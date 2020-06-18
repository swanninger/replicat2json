package com.fresh.replicat2json.bootstrap;

import com.fresh.replicat2json.Replicat2jsonApplication;
import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.services.CheckService;
import com.fresh.replicat2json.services.JSONService;
import com.fresh.replicat2json.services.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class Init implements ApplicationListener<ContextRefreshedEvent> {
    private final ConfigurableApplicationContext context;
    private final StoreService storeService;
    private final ReplicatConfig replicatConfig;
    private final CheckService checkService;
    private final JSONService jsonService;

    public Init(ConfigurableApplicationContext context, StoreService storeService, ReplicatConfig replicatConfig, CheckService checkService, JSONService jsonService) {
        this.context = context;
        this.storeService = storeService;
        this.replicatConfig = replicatConfig;
        this.checkService = checkService;
        this.jsonService = jsonService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.warn("repl Start.");
        LocalDate date;
        if (replicatConfig.getDateToRun() != null){
            date = replicatConfig.getDateToRun();
        }else{
            date = LocalDate.now();
        }
        jsonService.generateCheckFiles(date.minusDays(1));
//        queryStores(LocalDateTime.of(date.minusDays(1), LocalTime.now()));
//        getChecksByDateAndStore(LocalDateTime.of(date.minusDays(1), LocalTime.of(0,0)));

        log.warn("repl complete.");
        Replicat2jsonApplication.exitApplication(context);
    }

    public void getChecksByDateAndStore(Integer storeId, LocalDateTime localDateTime) {
        Iterable<Check> checks = checkService.get10ChecksByStoreAndDate(2, localDateTime);
        jsonService.saveToJSONFile("target/checks.json", checks);
    }
}
