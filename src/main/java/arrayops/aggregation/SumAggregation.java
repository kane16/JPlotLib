package arrayops.aggregation;

import arrayops.functions.GroupingFunction;
import java.util.List;

public class SumAggregation extends Aggregation {

  @Override
  public GroupingFunction getGroupingFunction() {
    return GroupingFunction.SUM;
  }

  @Override
  public Number performGrouping(List<Number> values) {
    return values.stream()
        .mapToDouble(Number::doubleValue)
        .sum();
  }
}
