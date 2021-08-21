package arrayops.transformation;

import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import exception.InvalidPlotRepresentation;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.enums.ColumnType;
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

  protected Number parseValueToColumnType(ColumnType columnType, String value) {
    switch (columnType) {
      case INTEGER:
        return Integer.parseInt(value);
      case DECIMAL:
        return parseDecimalWithDefaultFormat(value);
      default:
        throw new ColumnTypeMismatchException();
    }
  }

  double parseDecimalWithDefaultFormat(String value) {
    try {
      String processedValue = value.replace(",", ".");
      return Double.parseDouble(processedValue);
    } catch (NumberFormatException exc) {
      throw new InvalidDecimalRepresentation(value);
    }
  }

}
