package arrayops;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;
import model.output.Series;

@Slf4j
@AllArgsConstructor
abstract class PlotConverter {

  private final PlotHelper plotHelper;

  public abstract Optional<PlotData> parseToPlot(
      String[][] array,
      PlotInfo plotInfo
  );


}
