package arrayops;

import static java.util.stream.Collectors.toList;

import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import exception.InvalidPlotRepresentation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

@Slf4j
public class PlotHelper {

  public static final int HEADER_INDEX = 0;
  public static final int INDEX_NOT_FOUND = -1;

  PlotType resolvePlotType(PlotData plotData){
    if (!plotData.isEmpty()) {
      if (isAggregation(plotData)) {
        return PlotType.AGGREGATION;
      }
      return PlotType.STANDARD;
    } else {
      throw new InvalidPlotRepresentation();
    }
  }

  private boolean isAggregation(PlotData plotData) {
    return plotData.getArgSeries().getValues().stream()
        .collect(
            Collectors.groupingBy(
                Function.identity(),
                Collectors.reducing(0, x -> 1, Integer::sum)
            )
        )
        .entrySet()
        .stream()
        .anyMatch(entry -> entry.getValue() > 1);
  }

  List<Number> extractValuesFromColumn(
      String[][] array,
      int valuesIndex,
      ColumnType columnType
  ) {
    List<Number> numValues = new ArrayList<>();
    if (containsAnyData(array)) {
      for (int i = 1; i < array.length; i++) {
        switch (columnType) {
          case INTEGER:
            numValues.add(Integer.parseInt(array[i][valuesIndex]));
            break;
          case DECIMAL:
            numValues.add(parseDecimalWithDefaultFormat(array[i][valuesIndex]));
            break;
          default:
            throw new ColumnTypeMismatchException();
        }
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

  double parseDecimalWithDefaultFormat(String value) {
    try {
      String processedValue = value.replace(",", ".");
      return Double.parseDouble(processedValue);
    } catch (NumberFormatException exc) {
      throw new InvalidDecimalRepresentation(value);
    }
  }

  List<String> extractArgsFromColumn(String[][] array, String argsColumnName) {
    int argsIndex = getIndex(array, argsColumnName);
    return IntStream.range(1, array.length)
        .mapToObj(i -> array[i][argsIndex])
        .collect(toList());
  }

}
