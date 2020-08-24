package com.fresh.replicat2json.config;

import com.fresh.replicat2json.Replicat2jsonApplication;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
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
    List<Integer> itemsToSkip;
    LocalDate dateToRun;
    Integer merchantId;
    String merchantIdFull;
    String sftpUser;
    String sftpHost;
    String sftpPass;
    Integer sftpPort;
    String sftpPrivateKey;
    String sftpPrivateKeyPass;
    String knownHostsFile;
    String sftpPath;
    String jarPath;
    Boolean mods;

    public String getJarPath() {
        if (jarPath.isEmpty()) {
            ApplicationHome home = new ApplicationHome(Replicat2jsonApplication.class);
            return home.getDir().toString();
        } else {
            return jarPath;
        }
    }
}
