package arrayops.transformation;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.input.PlotInfo;
import model.output.PlotData;
import model.output.Series;

@Slf4j
public class EntityTransformationHelper extends TransformationHelper {

  public <T> Optional<PlotData> convertEntityListToPlotData(
      List<T> entityList,
      Class<T> entityClass,
      PlotInfo plotInfo
  ) {
    Optional<PlotData> plotDataOpt = Optional.empty();
    if (entityList.isEmpty()) {
      log.warn("Empty list of entity provided");
    } else {
      try {
        String argsColumnName = plotInfo.getArgsInfo().getColumnName();
        List<String> args = extractValuesFromFieldName(argsColumnName, entityList, entityClass);
        String valuesColumnName = plotInfo.getValuesInfo().getColumnName();
        List<Number> values =
            extractValuesFromFieldName(valuesColumnName, entityList, entityClass).stream()
                .map(value -> parseValueToColumnType(
                    plotInfo.getValuesInfo().getColumnType(),
                    value
                ))
                .collect(toList());
        plotDataOpt = Optional.of(new PlotData(
            args,
            values,
            argsColumnName,
            valuesColumnName,
            plotInfo.getPlotType()
        ));
      } catch (NoSuchFieldException | IllegalAccessException e) {
        log.error("Field not found for entity", e);
      }
    }

    return plotDataOpt;
  }

  private <T> List<String> extractValuesFromFieldName(
      String argsColumnName,
      List<T> entityList,
      Class<T> entityClass
  ) throws NoSuchFieldException, IllegalAccessException {
    Field field = entityClass.getDeclaredField(argsColumnName);
    field.setAccessible(true);
    List<String> values = new ArrayList<>();
    for (T entity : entityList) {
      values.add(field.get(entity).toString());
    }
    return values;
  }

}
