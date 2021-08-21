package arrayops.transformation;

import static java.util.stream.Collectors.toList;

import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import exception.InvalidPlotRepresentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;
import model.output.Series;

@Slf4j
public class ArrayTransformationHelper extends TransformationHelper {

  public static final int HEADER_INDEX = 0;
  public static final int INDEX_NOT_FOUND = -1;

  public Optional<PlotData> convertArrayToPlotData(
      String[][] array,
      PlotInfo plotInfo
  ) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    if (plotHeadersFound(array, plotInfo)) {
      List<String> args = extractArgsFromColumn(array, plotInfo.getArgsInfo().getColumnName());
      List<Number> values = extractValuesFromColumn(
          array,
          getIndex(array, plotInfo.getValuesInfo().getColumnName()),
          plotInfo.getValuesInfo().getColumnType()
      );
      return Optional.of(new PlotData(
          new Series<>(plotInfo.getArgsInfo().getColumnName(), args),
          new Series<>(plotInfo.getValuesInfo().getColumnName(), values),
          plotInfo.getPlotType()
      ));
    }
    return plotDataOpt;
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
      log.error("Args header name not valid");
    } else if (valuesColumnIndex == INDEX_NOT_FOUND) {
      log.error("Values header name not valid");
    }
    return argsColumnIndex != INDEX_NOT_FOUND && valuesColumnIndex != INDEX_NOT_FOUND;
  }

  List<String> extractArgsFromColumn(String[][] array, String argsColumnName) {
    int argsIndex = getIndex(array, argsColumnName);
    return IntStream.range(1, array.length)
        .mapToObj(i -> array[i][argsIndex])
        .collect(toList());
  }

}
