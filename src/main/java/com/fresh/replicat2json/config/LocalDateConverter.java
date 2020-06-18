package com.fresh.replicat2json.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * This class helps Spring properly convert Strings in properties to LocalDateTime format
 */

@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        if (s == null) {
            return null;
        }
        return LocalDate.parse(s);
    }
}
