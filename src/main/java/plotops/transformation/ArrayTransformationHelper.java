package plotops.transformation;

import static java.util.stream.Collectors.toList;

import exception.ColumnNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnType;
import model.input.PlotInfo;
import model.output.PlotData;

@Slf4j
public class ArrayTransformationHelper extends TransformationHelper {

  public static final int HEADER_INDEX = 0;
  /**
   * The constant representing invalid index.
   */
  public static final int INDEX_NOT_FOUND = -1;

  public PlotData convertArrayToPlotData(
      String[][] array,
      PlotInfo plotInfo
  ) {
    if (plotHeadersFound(array, plotInfo)) {
      List<String> args = extractArgsFromColumn(array, plotInfo.getArgsInfo().getColumnName());
      List<Number> values = extractValuesFromColumn(
          array,
          getIndex(array, plotInfo.getValuesInfo().getColumnName()),
          plotInfo.getValuesInfo().getColumnType()
      );
      List<String> columns = extractColumnNames(array);
      return new PlotData(
          args,
          values,
          plotInfo.getArgsInfo().getColumnName(),
          plotInfo.getValuesInfo().getColumnName(),
          plotInfo.getPlotType(),
          columns
      );
    } else {
      return null;
    }
  }

  private List<String> extractColumnNames(String[][] array) {
    String[] headerArray = array[0];
    return Arrays.asList(headerArray);
  }

  List<Number> extractValuesFromColumn(
      String[][] array,
      int valuesIndex,
      ColumnType columnType
  ) {
    List<Number> numValues = new ArrayList<>();
    if (containsAnyData(array)) {
      for (int i = 1; i < array.length; i++) {
        String cellValue = array[i][valuesIndex];
        numValues.add(parseValueToColumnType(columnType, cellValue));
      }
    }
    return numValues;
  }

  int getIndex(String[][] array, String headerName) {
    return IntStream.range(0, array[HEADER_INDEX].length)
        .filter(i -> array[HEADER_INDEX][i].equals(headerName))
        .findFirst().orElse(-1);
  }

  boolean containsAnyData(String[][] array) {
    boolean containsAnyData = array != null && array.length > 1;
    if (!containsAnyData) {
      log.warn("No data found for array");
    }
    return containsAnyData;
  }

  boolean plotHeadersFound(String[][] array, PlotInfo plotInfo) {
    int argsColumnIndex = getIndex(array, plotInfo.getArgsInfo().getColumnName());
    int valuesColumnIndex = getIndex(array, plotInfo.getValuesInfo().getColumnName());
    if (argsColumnIndex == INDEX_NOT_FOUND) {
      throw new ColumnNotFoundException("Args", plotInfo.getArgsInfo().getColumnName());
    } else if (valuesColumnIndex == INDEX_NOT_FOUND) {
      throw new ColumnNotFoundException("Values", plotInfo.getValuesInfo().getColumnName());
    }
    return true;
  }

  List<String> extractArgsFromColumn(String[][] array, String argsColumnName) {
    int argsIndex = getIndex(array, argsColumnName);
    return IntStream.range(1, array.length)
        .mapToObj(i -> array[i][argsIndex])
        .collect(toList());
  }

}
