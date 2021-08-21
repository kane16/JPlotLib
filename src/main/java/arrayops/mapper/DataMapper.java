package arrayops.mapper;

import arrayops.transformation.ArrayTransformationHelper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;

@Slf4j
@AllArgsConstructor
abstract class DataMapper {

  private final ArrayTransformationHelper arrayTransformationHelper;

  public abstract Optional<PlotData> parseToPlot(
      String[][] array,
      PlotInfo plotInfo
  );


}
