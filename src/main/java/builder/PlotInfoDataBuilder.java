package builder;

import model.input.PlotInfo;

/**
 * Partial builder to fill plot info needed for plot data creation.
 */
public class PlotInfoDataBuilder {

  public InputPlotDataBuilder withPlotInfo(PlotInfo plotInfo) {
    return new InputPlotDataBuilder(plotInfo);
  }


}
