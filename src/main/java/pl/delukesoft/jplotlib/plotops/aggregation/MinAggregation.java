package pl.delukesoft.jplotlib.plotops.aggregation;

import pl.delukesoft.jplotlib.plotops.functions.GroupingFunction;
import java.util.List;

public class MinAggregation extends Aggregation {

  @Override
  public GroupingFunction getGroupingFunction() {
    return GroupingFunction.MIN;
  }

  @Override
  public Number performGrouping(List<Number> values) {
    return values.stream()
        .mapToDouble(Number::doubleValue)
        .min().orElse(0.0);
  }
}
