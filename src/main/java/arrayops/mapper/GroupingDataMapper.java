package arrayops.mapper;

import arrayops.transformation.ArrayTransformationHelper;
import arrayops.functions.GroupingFunction;
import java.util.List;
import java.util.Optional;
import model.input.PlotInfo;
import model.output.PlotData;

public class GroupingDataMapper extends DataMapper {

  public GroupingDataMapper(ArrayTransformationHelper arrayTransformationHelper) {
    super(arrayTransformationHelper);
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
