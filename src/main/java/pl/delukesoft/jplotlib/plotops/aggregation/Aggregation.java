package pl.delukesoft.jplotlib.plotops.aggregation;

import pl.delukesoft.jplotlib.plotops.functions.GroupingFunction;
import java.util.List;
import pl.delukesoft.jplotlib.model.enums.ColumnType;

/**
 * The type Aggregation.
 */
public abstract class Aggregation {

  /**
   * Gets grouping function.
   *
   * @return the grouping function
   */
  abstract GroupingFunction getGroupingFunction();

  /**
   * Aggregate values by grouping function.
   *
   * @param values     the values
   * @param columnType the column type
   * @return the number
   */
  public Number aggregate(List<Number> values, ColumnType columnType) {
    Number groupResult = performGrouping(values);
    return groupResult.doubleValue();
  }

  /**
   * Perform grouping.
   *
   * @param values the values
   * @return the number
   */
  protected abstract Number performGrouping(List<Number> values);

}
