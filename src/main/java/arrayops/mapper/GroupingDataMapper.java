package arrayops.mapper;

import arrayops.aggregation.AggregationService;
import arrayops.functions.GroupingFunction;
import arrayops.transformation.ArrayTransformationHelper;
import arrayops.transformation.TransformationHelper;
import java.util.Optional;
import model.input.PlotInfo;
import model.output.PlotData;

public class GroupingDataMapper extends DataMapper {

  private final AggregationService aggregationService;

  public GroupingDataMapper(AggregationService aggregationService) {
    this.aggregationService = aggregationService;
  }

  public Optional<PlotData> groupValuesByGroupingFunction(
      Optional<PlotData> plotData,
      GroupingFunction groupingFunction
  ) {
    return Optional.empty();
  }

}
