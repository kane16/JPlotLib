package pl.delukesoft.jplotlib.builder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pl.delukesoft.jplotlib.exception.InvalidPlotTypeProvided;
import pl.delukesoft.jplotlib.exception.UnknownInputTypeProvided;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;
import pl.delukesoft.jplotlib.plotops.aggregation.AggregationService;
import pl.delukesoft.jplotlib.plotops.mapper.GroupingDataMapper;
import pl.delukesoft.jplotlib.plotops.mapper.StandardDataMapper;
import pl.delukesoft.jplotlib.plotops.transformation.ArrayTransformationHelper;
import pl.delukesoft.jplotlib.plotops.transformation.EntityTransformationHelper;
import pl.delukesoft.jplotlib.plotops.transformation.TransformationHelper;

/**
 * Partial pl.delukesoft.jplotlib.builder to fill plot info needed for plot data creation.
 */
public class PlotInfoDataBuilder<T> {

  InputType inputType;
  List<T> entityList;
  Class<T> entityClass;
  String[][] array;

  public PlotInfoDataBuilder(List<T> entityList, Class<T> entityClass) {
    this.entityList = entityList;
    this.entityClass = entityClass;
    this.inputType = InputType.ENTITY;
  }

  public PlotInfoDataBuilder(String[][] array) {
    this.array = array;
    this.inputType = InputType.ARRAY;
  }

  public List<String> extractColumns() {
    if (inputType.equals(InputType.ARRAY)) {
      ArrayTransformationHelper transformationHelper = new ArrayTransformationHelper();
      return transformationHelper.extractColumnNames(array);
    } else if (inputType.equals(InputType.ENTITY)) {
      EntityTransformationHelper transformationHelper = new EntityTransformationHelper();
      return transformationHelper.readEntityColumns(entityClass);
    } else {
      throw new UnknownInputTypeProvided();
    }
  }

  public CompletePlotDataBuilder withPlotInfo(PlotInfo plotInfo) {
    if (inputType.equals(InputType.ARRAY)) {
      ArrayTransformationHelper transformationHelper = new ArrayTransformationHelper();
      PlotData plotData = transformationHelper.convertArrayToPlotData(array, plotInfo);
      PlotData mappedPlotData = mapData(plotData, plotInfo);
      return new CompletePlotDataBuilder(mappedPlotData);
    } else if (inputType.equals(InputType.ENTITY)) {
      EntityTransformationHelper transformationHelper = new EntityTransformationHelper();
      PlotData plotData = transformationHelper.convertEntityListToPlotData(
          entityList,
          entityClass,
          plotInfo
      );
      PlotData mappedPlotData = mapData(plotData, plotInfo);
      return new CompletePlotDataBuilder(mappedPlotData);
    } else {
      throw new InvalidPlotTypeProvided();
    }
  }

  private PlotData mapData(PlotData plotData, PlotInfo plotInfo) {
    PlotType plotType = TransformationHelper.resolvePlotType(plotData);
    if (plotInfo.getPlotType() == plotType) {
      if (plotType == PlotType.AGGREGATION) {
        AggregationService aggregationService = new AggregationService();
        GroupingDataMapper groupingDataMapper = new GroupingDataMapper(aggregationService);
        return groupingDataMapper.mapPlotData(plotData, plotInfo);
      }
      if (plotType == PlotType.STANDARD) {
        StandardDataMapper standardDataMapper = new StandardDataMapper();
        return standardDataMapper.mapPlotData(plotData, plotInfo);
      } else {
        throw new InvalidPlotTypeProvided();
      }
    } else {
      throw new InvalidPlotTypeProvided();
    }
  }

  private enum InputType {
    ENTITY,
    ARRAY
  }


}
