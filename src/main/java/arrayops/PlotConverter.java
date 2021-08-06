package arrayops;

import exception.ColumnTypeMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.ColumnType;
import model.PlotData;
import model.Series;
import model.SeriesInfo;

@Slf4j
class PlotConverter {

  public Optional<PlotData> convertArrayToPlotData(
      String[][] array,
      SeriesInfo argsInfo,
      SeriesInfo valuesInfo
  ) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    int argsIndex = -1;
    int valuesIndex = -1;
    if(array.length > 0){
      String[] titleRow = array[0];
      for(int i=0; i<titleRow.length; i++){
        if(titleRow[i].equals(argsInfo.getName())) {
          argsIndex = i;
        }
        if(titleRow[i].equals(valuesInfo.getName())) {
          valuesIndex = i;
        }
      }
    }
    if(argsIndex > -1 && valuesIndex > -1) {
      List<String> args = extractArgsFromColumn(array, argsIndex);
      List<Number> values = extractValuesFromColumn(array, valuesIndex, valuesInfo.getColumnType());
      return Optional.of(new PlotData(
          new Series<>(argsInfo.getName(), args),
          new Series<>(valuesInfo.getName(), values)
      ));
    } else if(argsIndex == -1) {
      log.error("Args header name not valid");
    } else {
      log.error("Values header name not valid");
    }
    return plotDataOpt;
  }

  private List<Number> extractValuesFromColumn(
      String[][] array,
      int valuesIndex,
      ColumnType columnType
  ) {
    List<Number> numValues = new ArrayList<>();
    for(int i=1; i<array.length; i++){
      if(columnType.equals(ColumnType.INTEGER)) {
        numValues.add(Integer.parseInt(array[i][valuesIndex]));
      } else {
        numValues.add(Double.parseDouble(array[i][valuesIndex]));
      }
    }
    return numValues;
  }

  private List<String> extractArgsFromColumn(String[][] array, int argsIndex) {
    List<String> strValues = new ArrayList<>();
    for(int i=1; i<array.length; i++){
      strValues.add(array[i][argsIndex]);
    }
    return strValues;
  }


}
