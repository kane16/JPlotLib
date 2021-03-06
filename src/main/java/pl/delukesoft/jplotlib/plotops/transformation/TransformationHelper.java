package pl.delukesoft.jplotlib.plotops.transformation;

import pl.delukesoft.jplotlib.exception.ColumnTypeMismatchException;
import pl.delukesoft.jplotlib.exception.InvalidDecimalRepresentation;
import pl.delukesoft.jplotlib.exception.InvalidPlotTypeProvided;
import java.util.function.Function;
import java.util.stream.Collectors;
import pl.delukesoft.jplotlib.model.enums.ColumnType;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.output.PlotData;

public abstract class TransformationHelper {

  public static PlotType resolvePlotType(PlotData plotData){
    if (!plotData.isEmpty()) {
      if (isAggregation(plotData)) {
        return PlotType.AGGREGATION;
      }
      return PlotType.STANDARD;
    } else {
      throw new InvalidPlotTypeProvided();
    }
  }

  private static boolean isAggregation(PlotData plotData) {
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
