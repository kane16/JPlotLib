package pl.delukesoft.jplotlib.plotops.aggregation;

import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;
import pl.delukesoft.jplotlib.plotops.functions.GroupingFunction;

/**
 * The Aggregation service.
 */
@Slf4j
public class AggregationService {

  /**
   * The Aggregation type map.
   */
  Map<GroupingFunction, Aggregation> aggregationMap = Map.ofEntries(
      Map.entry(GroupingFunction.AVG, new AvgAggregation()),
      Map.entry(GroupingFunction.MAX, new MaxAggregation()),
      Map.entry(GroupingFunction.MIN, new MinAggregation()),
      Map.entry(GroupingFunction.SUM, new SumAggregation())
  );

  /**
   * Perform aggregation on plot data.
   *
   * @param plotData raw plot data
   * @param plotInfo the plot info
   * @return the plot data
   */
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
