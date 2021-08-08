package arrayops;

import static java.util.stream.Collectors.toList;

import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnType;
import model.PlotData;
import model.Series;
import model.SeriesInfo;
import model.enums.PlotType;

@Slf4j
class PlotConverter {

  public static final int HEADER_INDEX = 0;
  public static final int INDEX_NOT_FOUND = -1;

  public Optional<PlotData> convertArrayToPlotData(
      String[][] array,
      SeriesInfo argsInfo,
      SeriesInfo valuesInfo
  ) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    int argsIndex = getIndex(array, argsInfo.getName());
    int valuesIndex = getIndex(array, valuesInfo.getName());
    if( plotHeadersFound(argsIndex, valuesIndex) ) {
      List<String> args = extractArgsFromColumn(array, argsIndex);
      List<Number> values = extractValuesFromColumn(array, valuesIndex, valuesInfo.getColumnType());
      return Optional.of(new PlotData(
          new Series<>(argsInfo.getName(), args),
          new Series<>(valuesInfo.getName(), values),
          PlotType.STANDARD
      ));
    }
    return plotDataOpt;
  }

  private List<Number> extractValuesFromColumn(
      String[][] array,
      int valuesIndex,
      ColumnType columnType
  ) {
    List<Number> numValues = new ArrayList<>();
    if(containsAnyData(array)) {
      for(int i=1; i<array.length; i++){
        switch(columnType) {
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

  private int getIndex(String[][] array, String headerName){
    return IntStream.range(0, array[HEADER_INDEX].length)
        .filter(i -> array[HEADER_INDEX][i].equals(headerName))
        .findFirst().orElse(-1);
  }

  private boolean containsAnyData(String[][] array){
    boolean containsAnyData = array != null && array.length > 1;
    if(!containsAnyData){
      log.warn("No data found for array");
    }
    return containsAnyData;
  }

  private boolean plotHeadersFound(int argsColumnIndex, int valuesColumnIndex) {
    if(argsColumnIndex == INDEX_NOT_FOUND) {
      log.error("Args header name not valid");
    } else if(valuesColumnIndex == INDEX_NOT_FOUND) {
      log.error("Values header name not valid");
    }
    return argsColumnIndex != INDEX_NOT_FOUND && valuesColumnIndex != INDEX_NOT_FOUND;
  }

  double parseDecimalWithDefaultFormat(String value) {
    try {
      String processedValue = value.replace(",", ".");
      return Double.parseDouble(processedValue);
    }catch(NumberFormatException exc) {
      throw new InvalidDecimalRepresentation(value);
    }
  }

  private List<String> extractArgsFromColumn(String[][] array, int argsIndex) {
    return IntStream.range(1, array.length)
        .mapToObj(i -> array[i][argsIndex])
        .collect(toList());
  }


}
