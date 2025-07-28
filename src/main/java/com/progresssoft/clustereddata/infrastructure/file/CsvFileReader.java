package com.progresssoft.clustereddata.infrastructure.file;

import com.progresssoft.clustereddata.shared.FileReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CsvFileReader implements FileReader {

    @Override
    public Set<String> read(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .skip(1)
                    .map(line -> line.split(",")[0].trim())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file: " + path, e);
        }
    }
}
