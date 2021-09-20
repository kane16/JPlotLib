package builder;

import exception.InvalidPlotTypeProvided;
import io.CsvConnector;
import plotops.aggregation.AggregationService;
import plotops.mapper.GroupingDataMapper;
import plotops.mapper.StandardDataMapper;
import plotops.transformation.ArrayTransformationHelper;
import plotops.transformation.EntityTransformationHelper;
import plotops.transformation.TransformationHelper;
import java.util.List;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;


/**
 * Partial builder to fill input data needed for plot data creation.
 */
public class InputPlotDataBuilder {

  PlotInfo plotInfo;

  public InputPlotDataBuilder(PlotInfo plotInfo) {
    this.plotInfo = plotInfo;
  }

  public CompletePlotDataBuilder withArray(String array[][]) {
    ArrayTransformationHelper transformationHelper = new ArrayTransformationHelper();
    PlotData plotData = transformationHelper.convertArrayToPlotData(array, plotInfo);
    PlotData mappedPlotData = mapData(plotData, plotInfo);
    return new CompletePlotDataBuilder(mappedPlotData);
  }

  public <T> CompletePlotDataBuilder withEntityList(List<T> entityList, Class<T> entityClass) {
    EntityTransformationHelper transformationHelper = new EntityTransformationHelper();
    PlotData plotData = transformationHelper.convertEntityListToPlotData(entityList, entityClass, plotInfo);
    PlotData mappedPlotData = mapData(plotData, plotInfo);
    return new CompletePlotDataBuilder(mappedPlotData);
  }

  public CompletePlotDataBuilder withFilePathAndDelimiter(String absolutePath, String delimiter){
    CsvConnector csvConnector = new CsvConnector();
    String[][] array = csvConnector.readFromCsv(absolutePath, delimiter);
    ArrayTransformationHelper transformationHelper = new ArrayTransformationHelper();
    PlotData plotData = transformationHelper.convertArrayToPlotData(array, plotInfo);
    PlotData mappedPlotData = mapData(plotData, plotInfo);
    return new CompletePlotDataBuilder(mappedPlotData);
  }

  public CompletePlotDataBuilder withFilePath(String absolutePath) {
    return withFilePathAndDelimiter(absolutePath, ",");
  }

  private PlotData mapData(PlotData plotData, PlotInfo plotInfo) {
    PlotType plotType = TransformationHelper.resolvePlotType(plotData);
    if(plotInfo.getPlotType() == plotType) {
      if(plotType == PlotType.AGGREGATION) {
        AggregationService aggregationService = new AggregationService();
        GroupingDataMapper groupingDataMapper = new GroupingDataMapper(aggregationService);
        return groupingDataMapper.mapPlotData(plotData, plotInfo);
      }if(plotType == PlotType.STANDARD) {
        StandardDataMapper standardDataMapper = new StandardDataMapper();
        return standardDataMapper.mapPlotData(plotData, plotInfo);
      } else {
        throw new InvalidPlotTypeProvided();
      }
    } else {
      throw new InvalidPlotTypeProvided();
    }
  }

}
