package io;

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
  public void shouldReadFileCommaSuccessfullyReturnLines() throws IOException {
    String path = getClass().getClassLoader().getResource("test-comma.csv").getPath();
    Optional<List<String>> linesOpt = csvConnector.readFromCsv(path);
    Assertions.assertTrue(linesOpt.isPresent());
    Assertions.assertFalse(linesOpt.get().isEmpty());
  }

  @Test
  public void shouldReadFileSemicolonSuccessfullyReturnLines(){
    String path = getClass().getClassLoader().getResource("test-comma.csv").getPath();
    Optional<List<String>> linesOpt = csvConnector.readFromCsv(path);
    Assertions.assertTrue(linesOpt.isPresent());
    Assertions.assertFalse(linesOpt.get().isEmpty());
  }

  @Test
  public void shouldReadFileFailed(){
    String path = getClass().getClassLoader().getResource("").getPath() + "test-fail.csv";
    Optional<List<String>> linesOpt = csvConnector.readFromCsv(path);
    Assertions.assertTrue(linesOpt.isEmpty());
  }

}
