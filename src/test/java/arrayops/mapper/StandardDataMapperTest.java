package arrayops.mapper;

import arrayops.mapper.DataMapper;
import arrayops.mapper.StandardDataMapper;
import arrayops.transformation.ArrayTransformationHelper;
import exception.InvalidPlotRepresentation;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StandardDataMapperTest {

  ArrayTransformationHelper arrayTransformationHelper;
  DataMapper dataMapper;

  private String[][] standardPlot = {
      {"Name", "Hour"},
      {"First", "1"},
      {"Second", "2"}
  };


  private String[][] aggregationPlot = {
      {"Name", "Score"},
      {"Ann", "9"},
      {"Adam", "10"},
      {"Ann", "11"},
      {"Adam", "8"}
  };

  private String[][] invalidPlot1 = {
      {"Name", "Surname"}
  };

  @BeforeEach
  public void setUp() {
    arrayTransformationHelper = new ArrayTransformationHelper();
    dataMapper = new StandardDataMapper();
  }

  @Test
  public void shouldInvalidPlotCheckThrowError() {
    Optional<PlotData> invalidPlotData = arrayTransformationHelper.convertArrayToPlotData(
        invalidPlot1,
        new PlotInfo(
            PlotType.STANDARD,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Surname", ColumnType.STRING)
        )
    );
    Assertions.assertThrows(
        InvalidPlotRepresentation.class,
        () -> arrayTransformationHelper.resolvePlotType(invalidPlotData.get())
    );
  }

  @Test
  public void shouldValidCheckReturnStandard() {
    Optional<PlotData> standardPlotData = arrayTransformationHelper.convertArrayToPlotData(
        standardPlot,
        new PlotInfo(
            PlotType.STANDARD,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Hour", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(PlotType.STANDARD, arrayTransformationHelper.resolvePlotType(standardPlotData.get()));
  }

  @Test
  public void shouldValidCheckReturnAggregation() {
    Optional<PlotData> aggregationPlotData = arrayTransformationHelper.convertArrayToPlotData(
        aggregationPlot,
        new PlotInfo(
            PlotType.AGGREGATION,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Score", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(PlotType.AGGREGATION, arrayTransformationHelper.resolvePlotType(aggregationPlotData.get()));
  }

}
