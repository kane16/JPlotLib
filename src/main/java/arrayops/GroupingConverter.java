package arrayops;

import arrayops.functions.GroupingFunction;
import java.util.List;
import java.util.Optional;
import model.output.PlotData;

public class GroupingConverter {

  public PlotData groupValuesByGroupingFunction(
      PlotData plotData,
      GroupingFunction groupingFunction
  ) {
    List<String> args = plotData.getArgSeries().getValues();
    List<Number> values = plotData.getValuesSeries().getValues();
    return null;
  }

}
