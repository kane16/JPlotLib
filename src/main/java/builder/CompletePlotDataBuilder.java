package builder;

import model.output.PlotData;

/**
 * End partial builder.
 */
public class CompletePlotDataBuilder {

  PlotData plotData;

  public CompletePlotDataBuilder(PlotData plotData) {
    this.plotData = plotData;
  }

  public PlotData build(){
    return plotData;
  }

}
