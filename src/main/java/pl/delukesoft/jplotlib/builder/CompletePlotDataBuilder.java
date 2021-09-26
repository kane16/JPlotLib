package pl.delukesoft.jplotlib.builder;

import pl.delukesoft.jplotlib.model.output.PlotData;

/**
 * End partial pl.delukesoft.jplotlib.builder.
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
