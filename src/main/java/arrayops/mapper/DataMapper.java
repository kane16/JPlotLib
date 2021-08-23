package arrayops.mapper;

import arrayops.transformation.ArrayTransformationHelper;
import arrayops.transformation.TransformationHelper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

public interface DataMapper {

  public PlotType getPlotType();

  public Optional<PlotData> mapPlotData(PlotData plotData, PlotInfo plotInfo);


}
