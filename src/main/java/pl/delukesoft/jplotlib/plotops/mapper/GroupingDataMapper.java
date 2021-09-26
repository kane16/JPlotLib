package pl.delukesoft.jplotlib.plotops.mapper;

import pl.delukesoft.jplotlib.exception.NoGroupingFunctionProvidedException;
import pl.delukesoft.jplotlib.plotops.aggregation.AggregationService;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;

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
  public PlotData mapPlotData(PlotData plotData, PlotInfo plotInfo) {
    if(plotInfo.getGroupingFunction().isPresent()) {
      return aggregationService.performAggregation(plotData, plotInfo);
    } else throw new NoGroupingFunctionProvidedException();
  }
}
