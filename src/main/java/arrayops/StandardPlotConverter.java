package arrayops;

import java.util.Optional;
import model.input.PlotInfo;
import model.output.PlotData;

public class StandardPlotConverter extends PlotConverter{

  public StandardPlotConverter(PlotHelper plotHelper) {
    super(plotHelper);
  }

  @Override
  public Optional<PlotData> parseToPlot(String[][] array, PlotInfo plotInfo) {
    return Optional.empty();
  }
}
