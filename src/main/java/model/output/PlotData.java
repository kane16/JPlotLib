package model.output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.enums.PlotType;

@Getter
@NoArgsConstructor
public class PlotData {

  private Series<String> argSeries;
  private Series<Number> valuesSeries;
  private Map<String, List<Number>> argsWithValuesMap;
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

  public PlotData(
      Map<String, List<Number>> valuesMap,
      String argsHeaderName,
      String valuesHeaderName,
      PlotType plotType
  ) {
    this.argsWithValuesMap = valuesMap;
    this.argSeries = new Series<>(argsHeaderName, getArgsFromMap(valuesMap));
    this.valuesSeries = new Series<>(valuesHeaderName, getValuesFromMap(valuesMap));
    this.plotType = plotType;
  }

  private List<String> getArgsFromMap(Map<String, List<Number>> valuesMap) {
    return new ArrayList<>(valuesMap.keySet());
  }

  private List<Number> getValuesFromMap(Map<String, List<Number>> valuesMap) {
    return valuesMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  private Map<String, List<Number>> getEntriesMap(List<String> args, List<Number> values) {
    List<Entry<String, Number>> entries = new ArrayList<>();
    for (int i = 0; i < args.size(); i++) {
      entries.add(Map.entry(args.get(i), values.get(i)));
    }
    return entries.stream().collect(Collectors.groupingBy(Entry::getKey)).entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue().stream().map(Entry::getValue).collect(Collectors.toList())
        ));
  }

  public boolean isEmpty() {
    return valuesSeries.getValues().isEmpty() || argSeries.getValues().isEmpty();
  }

}
