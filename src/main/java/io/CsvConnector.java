package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CsvConnector {

  public Optional<List<String>> readFromCsv(String path) {
    var linesOpt = Optional.<List<String>>empty();
    try {
      linesOpt = Optional.of(Files.readAllLines(Path.of(path)));
    } catch (IOException e) {
      log.error("Unable to read file, doesn't exist", e);
    }
    return linesOpt;
  }

}
