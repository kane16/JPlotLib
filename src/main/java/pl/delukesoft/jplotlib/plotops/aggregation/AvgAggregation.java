package pl.delukesoft.jplotlib.plotops.aggregation;

import pl.delukesoft.jplotlib.plotops.functions.GroupingFunction;
import java.util.List;

public class AvgAggregation extends Aggregation{

  @Override
  public GroupingFunction getGroupingFunction() {
    return GroupingFunction.AVG;
  }

  @Override
  public Number performGrouping(List<Number> values) {
    return values.stream()
        .mapToDouble(Number::doubleValue)
        .average().orElse(0.0);
  }

}
