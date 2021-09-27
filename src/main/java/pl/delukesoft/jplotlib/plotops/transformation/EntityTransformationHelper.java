package pl.delukesoft.jplotlib.plotops.transformation;

import static java.util.stream.Collectors.toList;

import pl.delukesoft.jplotlib.exception.FieldNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;

@Slf4j
public class EntityTransformationHelper extends TransformationHelper {

  public <T> PlotData convertEntityListToPlotData(
      List<T> entityList,
      Class<T> entityClass,
      PlotInfo plotInfo
  ) {
    String argsColumnName = plotInfo.getArgsInfo().getColumnName();
    List<String> args;
    try {
      args = extractValuesFromFieldName(argsColumnName, entityList, entityClass);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new FieldNotFoundException("Args", argsColumnName);
    }
    String valuesColumnName = plotInfo.getValuesInfo().getColumnName();
    List<Number> values;
    try {
      values = extractValuesFromFieldName(valuesColumnName, entityList, entityClass).stream()
          .map(value -> parseValueToColumnType(
              plotInfo.getValuesInfo().getColumnType(),
              value
          ))
          .collect(toList());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new FieldNotFoundException("Values", valuesColumnName);
    }
    List<String> columns = readEntityColumns(entityClass);
    return new PlotData(
        args,
        values,
        argsColumnName,
        valuesColumnName,
        plotInfo.getPlotType(),
        columns
    );
  }

  public <T> List<String> readEntityColumns(Class<T> entityClass) {
    Field[] fields = entityClass.getDeclaredFields();
    List<String> entityColumns = new ArrayList<>();
    for(Field field: fields) {
      entityColumns.add(field.getName());
    }
    return entityColumns;
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
