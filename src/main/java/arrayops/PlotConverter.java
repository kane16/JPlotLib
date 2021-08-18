package arrayops;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;
import model.output.Series;

@Slf4j
@AllArgsConstructor
abstract class PlotConverter {

  private final PlotHelper plotHelper;

  public abstract Optional<PlotData> parseToPlot(
      String[][] array,
      PlotInfo plotInfo
  );

  Optional<PlotData> convertArrayToPlotData(
      String[][] array,
      PlotInfo plotInfo
  ) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    if (plotHelper.plotHeadersFound(array, plotInfo)) {
      List<String> args = plotHelper.extractArgsFromColumn(array, plotInfo.getArgsInfo().getColumnName());
      List<Number> values = plotHelper.extractValuesFromColumn(
          array,
          plotHelper.getIndex(array, plotInfo.getValuesInfo().getColumnName()),
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


}
