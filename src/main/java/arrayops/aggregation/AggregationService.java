package arrayops.aggregation;

import static java.util.stream.Collectors.toList;

import arrayops.functions.GroupingFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnType;
import model.input.PlotInfo;
import model.output.PlotData;

@Slf4j
public class AggregationService {



  public Optional<PlotData> performAggregation(PlotData plotData, PlotInfo plotInfo) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    if(plotInfo.getGroupingFunction().isEmpty()) {
      log.warn("Grouping function not provided");
    } else {
      plotDataOpt = Optional.of(plotData);
    }
    return plotDataOpt;
  }

}
