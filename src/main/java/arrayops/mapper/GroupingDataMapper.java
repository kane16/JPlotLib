package arrayops.mapper;

import arrayops.aggregation.AggregationService;
import java.util.Optional;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

public class GroupingDataMapper implements DataMapper {

  private final AggregationService aggregationService;

  public GroupingDataMapper(AggregationService aggregationService) {
    this.aggregationService = aggregationService;
  }

  @Override
  public PlotType getPlotType() {
    return PlotType.AGGREGATION;
  }

  @Override
  public Optional<PlotData> mapPlotData(PlotData plotData, PlotInfo plotInfo) {
    if(plotInfo.getGroupingFunction().isPresent()) {
      return aggregationService.performAggregation(plotData, plotInfo);
    }
    return null;
  }
}
