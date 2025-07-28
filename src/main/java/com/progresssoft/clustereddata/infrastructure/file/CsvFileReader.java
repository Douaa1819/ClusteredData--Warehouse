package com.progresssoft.clustereddata.infrastructure.file;

import com.progresssoft.clustereddata.shared.FileReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CsvFileReader implements FileReader {

    @Override
    public Set<String> read(Path path) {
        try {
            return Files.lines(path)
                    .skip(1)
                    .map(line -> line.split(",")[0].trim())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file: " + path, e);
        }
    }
}