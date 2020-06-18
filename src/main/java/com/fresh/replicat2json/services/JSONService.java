package com.fresh.replicat2json.services;

import java.time.LocalDate;

public interface JSONService {
    void saveToJSONFile(String fileName, Object o);

    void generateCheckFiles(LocalDate date);
}
