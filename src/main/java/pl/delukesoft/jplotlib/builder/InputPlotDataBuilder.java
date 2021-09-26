package pl.delukesoft.jplotlib.builder;

import pl.delukesoft.jplotlib.exception.InvalidPlotTypeProvided;
import pl.delukesoft.jplotlib.io.CsvConnector;
import pl.delukesoft.jplotlib.plotops.aggregation.AggregationService;
import pl.delukesoft.jplotlib.plotops.mapper.GroupingDataMapper;
import pl.delukesoft.jplotlib.plotops.mapper.StandardDataMapper;
import pl.delukesoft.jplotlib.plotops.transformation.ArrayTransformationHelper;
import pl.delukesoft.jplotlib.plotops.transformation.EntityTransformationHelper;
import pl.delukesoft.jplotlib.plotops.transformation.TransformationHelper;
import java.util.List;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;


/**
 * Partial pl.delukesoft.jplotlib.builder to fill input data needed for plot data creation.
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
