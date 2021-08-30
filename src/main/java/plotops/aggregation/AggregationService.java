package plotops.aggregation;

import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;
import plotops.functions.GroupingFunction;

@Slf4j
public class AggregationService {

  Map<GroupingFunction, Aggregation> aggregationMap = Map.ofEntries(
      Map.entry(GroupingFunction.AVG, new AvgAggregation()),
      Map.entry(GroupingFunction.MAX, new MaxAggregation()),
      Map.entry(GroupingFunction.MIN, new MinAggregation()),
      Map.entry(GroupingFunction.SUM, new SumAggregation())
  );

  public PlotData performAggregation(PlotData plotData, PlotInfo plotInfo) {
    Map<String, List<Number>> aggregatedValuesMap = plotData.getArgsWithValuesMap().entrySet()
        .stream()
        .map(entry -> Map.entry(
            entry.getKey(),
            Collections.singletonList(groupValues(entry.getValue(), plotInfo))
        ))
        .collect(toMap(Entry::getKey, Entry::getValue));
    return new PlotData(
        aggregatedValuesMap,
        plotInfo.getArgsInfo().getColumnName(),
        plotInfo.getValuesInfo().getColumnName(),
        plotInfo.getPlotType(),
        plotData.getColumns()
    );
  }

  private Number groupValues(List<Number> value, PlotInfo plotInfo) {
    return aggregationMap.get(plotInfo.getGroupingFunction().get())
        .aggregate(value, plotInfo.getValuesInfo().getColumnType());
  }

}
