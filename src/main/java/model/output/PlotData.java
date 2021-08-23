package model.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.PlotType;

@Getter
@NoArgsConstructor
public class PlotData {

  private Series<String> argSeries;
  private Series<Number> valuesSeries;
  private Map<String, Number> argsWithValuesMap;
  private PlotType plotType;

  public PlotData(
      List<String> args,
      List<Number> values,
      String argsHeaderName,
      String valuesHeaderName,
      PlotType plotType
  ) {
    this.argsWithValuesMap = getEntriesMap(args, values);
    this.argSeries = new Series<>(argsHeaderName, args);
    this.valuesSeries = new Series<>(valuesHeaderName, values);
    this.plotType = plotType;
  }

  private Map<String, Number> getEntriesMap(List<String> args, List<Number> values) {
    Map<String, Number> entriesMap = new HashMap<>();
    for(int i=0; i<args.size(); i++){
      entriesMap.put(args.get(i), values.get(i));
    }
    return entriesMap;
  }

  public boolean isEmpty() {
    return valuesSeries.getValues().isEmpty();
  }

}
