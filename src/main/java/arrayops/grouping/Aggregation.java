package arrayops.grouping;

import arrayops.functions.GroupingFunction;
import java.util.List;
import model.ColumnType;

public abstract class Aggregation {

  abstract GroupingFunction getGroupingFunction();

  public Number aggregate(List<Number> values, ColumnType columnType) {
    Number groupResult = performGrouping(values);
    if(columnType.equals(ColumnType.INTEGER)) {
      return groupResult.intValue();
    }
    return groupResult.doubleValue();
  }

  protected abstract Number performGrouping(List<Number> values);

}
