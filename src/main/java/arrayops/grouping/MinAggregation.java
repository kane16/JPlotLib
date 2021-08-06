package arrayops.grouping;

import arrayops.functions.GroupingFunction;
import java.util.List;
import model.ColumnType;

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
