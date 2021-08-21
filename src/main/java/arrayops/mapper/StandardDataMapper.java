package arrayops.mapper;

import arrayops.transformation.ArrayTransformationHelper;
import java.util.Optional;
import model.input.PlotInfo;
import model.output.PlotData;

public class StandardDataMapper extends DataMapper {

  public StandardDataMapper(ArrayTransformationHelper arrayTransformationHelper) {
    super(arrayTransformationHelper);
  }

  @Override
  public Optional<PlotData> parseToPlot(String[][] array, PlotInfo plotInfo) {
    return Optional.empty();
  }
}
