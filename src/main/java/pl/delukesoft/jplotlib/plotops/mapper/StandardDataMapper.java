package pl.delukesoft.jplotlib.plotops.mapper;

import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;

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
