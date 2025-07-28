package com.progresssoft.clustereddata.infrastructure.currency;

import com.progresssoft.clustereddata.shared.CurrencyHolder;
import com.progresssoft.clustereddata.shared.FileReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class CsvCurrencyHolder implements CurrencyHolder {

    private final Set<String> currencyCodes;

    public CsvCurrencyHolder(FileReader fileReader) {
        try {
            Path path = Paths.get(new ClassPathResource("currencies.csv").getURI());
            this.currencyCodes = fileReader.read(path);
            validate(currencyCodes);
        } catch (IOException e) {
            throw new RuntimeException("Could not load currencies.csv", e);
        }
    }

    @Override
    public boolean exists(String code) {
        return currencyCodes.contains(code);
    }

    private void validate(Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            throw new IllegalArgumentException("Currency list is invalid or empty.");
        }
    }
}