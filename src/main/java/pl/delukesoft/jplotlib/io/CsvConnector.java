package pl.delukesoft.jplotlib.io;

import static java.util.stream.Collectors.toList;

import pl.delukesoft.jplotlib.exception.CsvNotExistingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvConnector {

  public String[][] readFromCsv(String path, String delimiter) {
    try {
      List<String[]> valuesList = Files.readAllLines(Path.of(path)).stream()
          .map(values -> values.split(delimiter))
          .collect(toList());
      String[][] array = new String[valuesList.size()][];
      for(int i=0; i<valuesList.size(); i++){
        array[i] = valuesList.get(i);
      }
      return array;
    } catch (IOException e) {
      throw new CsvNotExistingException();
    }
  }

}
