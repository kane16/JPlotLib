package builder;

import model.input.PlotInfo;

public class PlotInfoDataBuilder {

  public InputPlotDataBuilder withPlotInfo(PlotInfo plotInfo) {
    return new InputPlotDataBuilder(plotInfo);
  }


}
