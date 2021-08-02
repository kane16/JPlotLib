package arrayops;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
    Optional<PlotData> plotData = Optional.empty();
    if(array.length > 0){
      int argsIndex = -1;
      int valuesIndex = -1;
      String[] titleRow = array[0];
      for(int i=0; i<titleRow.length; i++){

        if(titleRow[i].equals(argsInfo.getName())) {

        }
      }
    }
    return Optional.empty();
  }

}
