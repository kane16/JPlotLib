package arrayops;

import arrayops.functions.GroupingFunction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.PlotData;

public class GroupingConverter {

  public Optional<PlotData> groupValuesByGroupingFunction(
      Optional<PlotData> plotDataOpt,
      GroupingFunction groupingFunction
      ) {
    return plotDataOpt.map(plotData -> groupValuesByGroupingFunction(plotData, groupingFunction));
  }

  private PlotData groupValuesByGroupingFunction(
      PlotData plotData,
      GroupingFunction groupingFunction
  ) {
    List<String> args = plotData.getArgSeries().getValues();
    List<Number> values = plotData.getValuesSeries().getValues();
    return null;
  }

}
