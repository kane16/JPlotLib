package arrayops.transformation;

import exception.InvalidPlotRepresentation;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.enums.PlotType;
import model.output.PlotData;

public abstract class TransformationHelper {

  public PlotType resolvePlotType(PlotData plotData){
    if (!plotData.isEmpty()) {
      if (isAggregation(plotData)) {
        return PlotType.AGGREGATION;
      }
      return PlotType.STANDARD;
    } else {
      throw new InvalidPlotRepresentation();
    }
  }

  private boolean isAggregation(PlotData plotData) {
    return plotData.getArgSeries().getValues().stream()
        .collect(
            Collectors.groupingBy(
                Function.identity(),
                Collectors.reducing(0, x -> 1, Integer::sum)
            )
        )
        .entrySet()
        .stream()
        .anyMatch(entry -> entry.getValue() > 1);
  }

}
