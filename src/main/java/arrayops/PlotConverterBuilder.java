package arrayops;

import arrayops.PlotConverter;
import java.util.Optional;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

public class PlotConverterBuilder {

  PlotHelper plotHelper;

  public PlotConverterBuilder(PlotHelper plotHelper) {
    this.plotHelper = plotHelper;
  }

  public Optional<PlotConverter> build(String[][] data, PlotInfo plotInfo) {
    Optional<PlotData> plotDataOpt = plotHelper.convertArrayToPlotData(data, plotInfo);
    if(plotDataOpt.isPresent()) {
      PlotData plotData = plotDataOpt.get();
      PlotType detectedPlotType = plotHelper.resolvePlotType(plotData);
      if(detectedPlotType.equals(plotInfo.getPlotType())) {

      }else {

      }
    }
    return Optional.empty();
  }

}
