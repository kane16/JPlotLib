package plotops.aggregation;

import plotops.functions.GroupingFunction;
import java.util.List;
import model.enums.ColumnType;

public abstract class Aggregation {

  abstract GroupingFunction getGroupingFunction();

  public Number aggregate(List<Number> values, ColumnType columnType) {
    Number groupResult = performGrouping(values);
    return groupResult.doubleValue();
  }

  protected abstract Number performGrouping(List<Number> values);

}
