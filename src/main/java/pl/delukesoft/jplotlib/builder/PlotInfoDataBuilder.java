package pl.delukesoft.jplotlib.builder;

import pl.delukesoft.jplotlib.model.input.PlotInfo;

/**
 * Partial pl.delukesoft.jplotlib.builder to fill plot info needed for plot data creation.
 */
public class PlotInfoDataBuilder {

  public InputPlotDataBuilder withPlotInfo(PlotInfo plotInfo) {
    return new InputPlotDataBuilder(plotInfo);
  }


}
