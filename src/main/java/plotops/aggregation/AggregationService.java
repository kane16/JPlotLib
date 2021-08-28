package plotops.aggregation;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;

@Slf4j
public class AggregationService {



  public PlotData performAggregation(PlotData plotData, PlotInfo plotInfo) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    if(plotInfo.getGroupingFunction().isEmpty()) {
      log.warn("Grouping function not provided");
    }
    return plotData;
  }

}
