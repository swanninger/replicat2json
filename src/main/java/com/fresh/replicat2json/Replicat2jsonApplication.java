package com.fresh.replicat2json;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Replicat2jsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(Replicat2jsonApplication.class, args);
    }

    public static void exitApplication(ConfigurableApplicationContext ctx) {
        int exitCode = SpringApplication.exit(ctx, () -> {
            // no errors
            return 0;
        });
        System.exit(exitCode);
    }
}
