package plotops.transformation;

import exception.ColumnNotFoundException;
import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import java.util.Arrays;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayTransformationHelperTest {

  public static final String[][] VALUES = new String[][]{
      {"Name", "Age", "Height", "Grade", "Reward"},
      {"Ann", "22", "165", "4,5", "1000.0"},
      {"John", "13", "144", "3,5", "100.0"}
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
    Assertions.assertThrows(
        ColumnNotFoundException.class,
        () -> arrayTransformationHelper.convertArrayToPlotData(
            VALUES,
            plotInfo
        )
    );
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenValuesHeaderNotExisting() {

    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("LowHeight", ColumnType.INTEGER)
    );
    Assertions.assertThrows(
        ColumnNotFoundException.class,
        () -> arrayTransformationHelper.convertArrayToPlotData(
            VALUES,
            plotInfo
        )
    );
  }

  @Test
  public void shouldArrayBeConvertedToPlotDataCorrectlyWithStringArgs() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.INTEGER)
    );
    PlotData plotData = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
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
    PlotData plotData = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
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
    PlotData plotData = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
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
    PlotData plotData = arrayTransformationHelper.convertArrayToPlotData(
        VALUES,
        plotInfo
    );
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

}
