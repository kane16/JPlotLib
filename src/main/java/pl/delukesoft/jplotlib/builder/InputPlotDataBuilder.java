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

  public PlotInfoDataBuilder<String> withArray(String array[][]) {
    return new PlotInfoDataBuilder<String>(array);
  }

  public <T> PlotInfoDataBuilder<T> withEntityList(List<T> entityList, Class<T> entityClass) {
    return new PlotInfoDataBuilder<T>(entityList, entityClass);
  }

  public <T> PlotInfoDataBuilder<T> withFilePathAndDelimiter(String absolutePath, String delimiter){
    CsvConnector csvConnector = new CsvConnector();
    String[][] array = csvConnector.readFromCsv(absolutePath, delimiter);
    return new PlotInfoDataBuilder<T>(array);
  }

  public <T> PlotInfoDataBuilder<T> withFilePath(String absolutePath) {
    CsvConnector csvConnector = new CsvConnector();
    String[][] array = csvConnector.readFromCsv(absolutePath, ",");
    return new PlotInfoDataBuilder<T>(array);
  }


}
