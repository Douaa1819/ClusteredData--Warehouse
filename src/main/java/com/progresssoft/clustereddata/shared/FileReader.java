package com.progresssoft.clustereddata.shared;

import java.nio.file.Path;
import java.util.Set;

@FunctionalInterface
public interface FileReader {
    Set<String> read(Path path);
}