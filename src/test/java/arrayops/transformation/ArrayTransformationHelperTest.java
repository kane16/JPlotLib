package arrayops.transformation;

import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import exception.InvalidPlotRepresentation;
import java.util.Arrays;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;
import model.input.SeriesInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayTransformationHelperTest {

  public static final String[][] VALUES = new String[][]{
      {"Name", "Age", "Height", "Grade", "Reward"},
      {"Ann", "22", "165", "4,5", "1000.0"},
      {"John", "13", "144", "3,5", "100.0"}
  };

  private final String[][] STANDARD_PLOT_VALUES = {
      {"Name", "Hour"},
      {"First", "1"},
      {"Second", "2"}
  };


  private final String[][] AGGREGATION_PLOT_VALUES = {
      {"Name", "Score"},
      {"Ann", "9"},
      {"Adam", "10"},
      {"Ann", "11"},
      {"Adam", "8"}
  };

  private final String[][] INVALID_PLOT = {
      {"Name", "Surname"}
  };

  private ArrayTransformationHelper arrayTransformationHelper;

  @BeforeEach
  public void setUp() {
    arrayTransformationHelper = new ArrayTransformationHelper();
  }

  @Test
  public void shouldArrayConversionThrowConversionMismatch() {

    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.STRING)
    );
    Assertions.assertThrows(
        ColumnTypeMismatchException.class,
        () -> arrayTransformationHelper.convertArrayToPlotData(
            VALUES,
            plotInfo
        )
    );
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenArgsHeaderNotExisting() {

    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Surname", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.INTEGER)
    );
    Optional<PlotData> plotDataOpt = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
        );
    Assertions.assertTrue(plotDataOpt.isEmpty());
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenValuesHeaderNotExisting() {

    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("LowHeight", ColumnType.INTEGER)
    );
    Optional<PlotData> plotDataOpt = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
    Assertions.assertTrue(plotDataOpt.isEmpty());
  }

  @Test
  public void shouldArrayBeConvertedToPlotDataCorrectlyWithStringArgs() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.INTEGER)
    );
    Optional<PlotData> plotDataOpt = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
    Assertions.assertTrue(plotDataOpt.isPresent());
    PlotData plotData = plotDataOpt.get();
    Assertions.assertEquals("Name", plotData.getArgSeries().getName());
    Assertions.assertEquals(Arrays.asList("Ann", "John"), plotData.getArgSeries().getValues());
    Assertions.assertEquals("Height", plotData.getValuesSeries().getName());
    Assertions.assertEquals(Arrays.asList(165, 144), plotData.getValuesSeries().getValues());
  }

  @Test
  public void shouldArrayBeConvertedToPlotDataCorrectlyWithIntArgs() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Age", ColumnType.INTEGER),
        new SeriesInfo("Height", ColumnType.INTEGER)
    );
    Optional<PlotData> plotDataOpt = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
    Assertions.assertTrue(plotDataOpt.isPresent());
    PlotData plotData = plotDataOpt.get();
    Assertions.assertEquals("Age", plotData.getArgSeries().getName());
    Assertions.assertEquals(Arrays.asList("22", "13"), plotData.getArgSeries().getValues());
    Assertions.assertEquals("Height", plotData.getValuesSeries().getName());
    Assertions.assertEquals(Arrays.asList(165, 144), plotData.getValuesSeries().getValues());
  }


  @Test
  public void shouldArrayBeConvertedToPlotDataCorrectlyWithDecimalValuesCommaSeparator() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Grade", ColumnType.DECIMAL)
    );
    Optional<PlotData> plotDataOpt = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
    Assertions.assertTrue(plotDataOpt.isPresent());
    PlotData plotData = plotDataOpt.get();
    Assertions.assertEquals("Name", plotData.getArgSeries().getName());
    Assertions.assertEquals(Arrays.asList("Ann", "John"), plotData.getArgSeries().getValues());
    Assertions.assertEquals("Grade", plotData.getValuesSeries().getName());
    Assertions.assertEquals(Arrays.asList(4.5, 3.5), plotData.getValuesSeries().getValues());
  }

  @Test
  public void shouldArrayBeConvertedToPlotDataCorrectlyWithDecimalValuesDotSeparator() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Reward", ColumnType.DECIMAL)
    );
    Optional<PlotData> plotDataOpt = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
    Assertions.assertTrue(plotDataOpt.isPresent());
    PlotData plotData = plotDataOpt.get();
    Assertions.assertEquals("Name", plotData.getArgSeries().getName());
    Assertions.assertEquals(Arrays.asList("Ann", "John"), plotData.getArgSeries().getValues());
    Assertions.assertEquals("Reward", plotData.getValuesSeries().getName());
    Assertions.assertEquals(Arrays.asList(1000.0, 100.0), plotData.getValuesSeries().getValues());
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenInvalidDecimalRepresentation() {
    Assertions.assertThrows(
        InvalidDecimalRepresentation.class,
        () -> arrayTransformationHelper.parseDecimalWithDefaultFormat("22.220.22")
    );
    Assertions.assertThrows(
        InvalidDecimalRepresentation.class,
        () -> arrayTransformationHelper.parseDecimalWithDefaultFormat("22,220.22")
    );
    Assertions.assertThrows(
        InvalidDecimalRepresentation.class,
        () -> arrayTransformationHelper.parseDecimalWithDefaultFormat("22,220,22")
    );
  }

  @Test
  public void shouldInvalidPlotCheckThrowError() {
    Optional<PlotData> invalidPlotData = arrayTransformationHelper.convertArrayToPlotData(
        INVALID_PLOT,
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
        STANDARD_PLOT_VALUES,
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
        AGGREGATION_PLOT_VALUES,
        new PlotInfo(
            PlotType.AGGREGATION,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Score", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(PlotType.AGGREGATION, arrayTransformationHelper.resolvePlotType(aggregationPlotData.get()));
  }

}
