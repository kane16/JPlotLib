package plotops.mapper;

import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

public class StandardDataMapper implements DataMapper {


  @Override
  public PlotType getPlotType() {
    return PlotType.STANDARD;
  }

  @Override
  public PlotData mapPlotData(
      PlotData plotData, PlotInfo plotInfo
  ) {
    return plotData;
  }
}
