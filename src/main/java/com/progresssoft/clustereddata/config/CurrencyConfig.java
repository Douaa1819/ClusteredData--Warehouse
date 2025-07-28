package com.progresssoft.clustereddata.config;

import com.progresssoft.clustereddata.infrastructure.currency.CsvCurrencyHolder;
import com.progresssoft.clustereddata.infrastructure.file.CsvFileReader;
import com.progresssoft.clustereddata.shared.CurrencyHolder;
import com.progresssoft.clustereddata.shared.FileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyConfig {

    @Bean
    public FileReader fileReader() {
        return new CsvFileReader();
    }

    @Bean
    public CurrencyHolder currencyHolder(FileReader fileReader) {
        return new CsvCurrencyHolder(fileReader);
    }
}
