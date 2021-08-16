package io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvConnectorTest {

  CsvConnector csvConnector;

  @BeforeEach
  public void setUp(){
    csvConnector = new CsvConnector();
  }

  @Test
  public void shouldReadFileCommaSuccessfullyReturnLines() {
    String path = new File(getClass().getClassLoader().getResource("test-comma.csv").getPath()).toString();
    Optional<List<String>> linesOpt = csvConnector.readFromCsv(path);
    Assertions.assertTrue(linesOpt.isPresent());
    Assertions.assertFalse(linesOpt.get().isEmpty());
  }

  @Test
  public void shouldReadFileSemicolonSuccessfullyReturnLines(){
    String path = new File(getClass().getClassLoader().getResource("test-comma.csv").getPath()).toString();
    Optional<List<String>> linesOpt = csvConnector.readFromCsv(path);
    Assertions.assertTrue(linesOpt.isPresent());
    Assertions.assertFalse(linesOpt.get().isEmpty());
  }

  @Test
  public void shouldReadFileFailed(){
    String path = new File(getClass().getClassLoader().getResource("test-comma.csv").getPath()).toString();
    Optional<List<String>> linesOpt = csvConnector.readFromCsv(path.replace("test-comma.csv", "test-fail.csv"));
    Assertions.assertTrue(linesOpt.isEmpty());
  }

}
