package arrayops.grouping;

import arrayops.functions.GroupingFunction;
import java.util.List;
import model.ColumnType;

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
