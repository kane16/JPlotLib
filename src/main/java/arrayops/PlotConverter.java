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

  public <X, Y extends Number> Optional<PlotData<X, Y>> convertArrayToPlotData(
      String[][] array,
      SeriesInfo argsInfo,
      SeriesInfo valuesInfo
  ) {
    Optional<PlotData<X, Y>> plotDataOpt = Optional.empty();
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
      List<X> args = extractValuesFromColumn(array, argsIndex, argsInfo.getColumnType());
      List<Y> values = extractValuesFromColumn(array, valuesIndex, valuesInfo.getColumnType());
      return Optional.of(new PlotData<>(
          new Series<>(argsInfo.getName(), args),
          new Series<>(valuesInfo.getName(), values)
      ));
    } else if(argsIndex == -1) {
      log.error("Args header name not valid");
    } else if(valuesIndex == -1) {
      log.error("Values header name not valid");
    }
    return plotDataOpt;
  }

  private <X> List<X> extractValuesFromColumn(
      String[][] array,
      int columnIndex,
      ColumnType columnType
  ) {
    switch(columnType) {
      case STRING:
        List<String> strValues = new ArrayList<>();
        for(int i=1; i<array.length; i++){
          strValues.add(array[i][columnIndex]);
        }
        return (List<X>) strValues;
      case DECIMAL:
        List<Double> decimalValues = new ArrayList<>();
        for(int i=1; i<array.length; i++){
          decimalValues.add(Double.parseDouble(array[i][columnIndex]));
        }
        return (List<X>) decimalValues;
      case INTEGER:
        List<Integer> integerValues = new ArrayList<>();
        for(int i=1; i<array.length; i++){
          integerValues.add(Integer.parseInt(array[i][columnIndex]));
        }
        return (List<X>) integerValues;
      default:
        throw new ColumnTypeMismatchException();
    }
  }

}
