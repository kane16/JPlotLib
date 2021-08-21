package arrayops.transformation;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;

@Slf4j
public class EntityTransformationHelper extends TransformationHelper {

  public <T> Optional<PlotData> convertEntityListToPlotData(
      List<T> entityList,
      Class<T> entityClass,
      PlotInfo plotInfo
  ) {
    return Optional.empty();
  }

}
