package arrayops;

import arrayops.functions.GroupingFunction;
import java.util.List;
import java.util.Optional;
import model.input.PlotInfo;
import model.output.PlotData;

public class GroupingPlotConverter extends PlotConverter{

  public GroupingPlotConverter(PlotHelper plotHelper) {
    super(plotHelper);
  }

  @Override
  public Optional<PlotData> parseToPlot(String[][] array, PlotInfo plotInfo) {
    return Optional.empty();
  }

  public PlotData groupValuesByGroupingFunction(
      PlotData plotData,
      GroupingFunction groupingFunction
  ) {
    List<String> args = plotData.getArgSeries().getValues();
    List<Number> values = plotData.getValuesSeries().getValues();
    return null;
  }

}
