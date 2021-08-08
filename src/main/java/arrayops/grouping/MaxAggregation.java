package arrayops.grouping;

import arrayops.functions.GroupingFunction;
import java.util.List;

public class MaxAggregation extends Aggregation{

  @Override
  public GroupingFunction getGroupingFunction() {
    return GroupingFunction.MAX;
  }

  @Override
  public Number performGrouping(List<Number> values) {
    return values.stream()
        .mapToDouble(Number::doubleValue)
        .max().orElse(0.0);
  }
}
