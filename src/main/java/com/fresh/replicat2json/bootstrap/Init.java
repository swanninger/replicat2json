package com.fresh.replicat2json.bootstrap;

import com.fresh.replicat2json.Replicat2jsonApplication;
import com.fresh.replicat2json.config.ReplicatConfig;
import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.services.CheckService;
import com.fresh.replicat2json.services.JSONService;
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
    private final ReplicatConfig replicatConfig;
    private final CheckService checkService;
    private final JSONService jsonService;

    public Init(ConfigurableApplicationContext context, ReplicatConfig replicatConfig, CheckService checkService, JSONService jsonService) {
        this.context = context;
        this.replicatConfig = replicatConfig;
        this.checkService = checkService;
        this.jsonService = jsonService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        log.warn("jarpath = " + replicatConfig.getJarPath());
        log.warn("repl Start.");
        LocalDate date;
        if (replicatConfig.getDateToRun() != null) {
            date = replicatConfig.getDateToRun();
            log.info("Running for " + date.toString());
        } else {
            date = LocalDate.now().minusDays(1);
        }
        log.warn("Running for " + date.toString());
        jsonService.generateCheckFiles(date);

        log.warn("repl complete.");
        Replicat2jsonApplication.exitApplication(context);
    }

    public void getChecksByDateAndStore(Integer storeId, LocalDateTime localDateTime) {
        Iterable<Check> checks = checkService.get10ChecksByStoreAndDate(2, localDateTime);
        jsonService.saveToJSONFile("target/checks.json", checks);
    }


}
