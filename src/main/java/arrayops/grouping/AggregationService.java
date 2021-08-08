package arrayops.grouping;

import arrayops.functions.GroupingFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.enums.ColumnType;

public class AggregationService {

  private List<Aggregation> aggregationList;

  public AggregationService(){
    aggregationList = new ArrayList<>();
    aggregationList.add(new AvgAggregation());
    aggregationList.add(new MaxAggregation());
    aggregationList.add(new MinAggregation());
    aggregationList.add(new SumAggregation());
  }
  
  public Optional<Number> performAggregation(List<Number> values, ColumnType columnType, GroupingFunction groupingFunction) {
    return aggregationList.stream()
        .filter(aggregation -> aggregation.getGroupingFunction().equals(groupingFunction))
        .map(aggregation -> aggregation.aggregate(values, columnType))
        .findFirst();
  }

}
