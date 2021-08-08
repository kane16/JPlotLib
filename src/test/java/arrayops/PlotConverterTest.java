package arrayops;

import exception.ColumnTypeMismatchException;
import exception.InvalidDecimalRepresentation;
import java.util.Arrays;
import java.util.Optional;
import model.enums.ColumnType;
import model.PlotData;
import model.SeriesInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlotConverterTest {

  public static final String[][] VALUES = new String[][]{
      {"Name", "Age", "Height", "Grade", "Reward"},
      {"Ann", "22", "165", "4,5", "1000.0"},
      {"John", "13", "144", "3,5", "100.0"}
  };

  private PlotConverter plotConverter;

  @BeforeEach
  public void setUp() {
    plotConverter = new PlotConverter();
  }

  @Test
  public void shouldArrayConversionThrowConversionMismatch(){

    Assertions.assertThrows(ColumnTypeMismatchException.class, () -> plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.STRING)
    ));
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenArgsHeaderNotExisting(){

    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Surname", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.INTEGER)
        );
    Assertions.assertTrue(plotDataOpt.isEmpty());
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenValuesHeaderNotExisting(){

    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("LowHeight", ColumnType.INTEGER)
    );
    Assertions.assertTrue(plotDataOpt.isEmpty());
  }

  @Test
  public void shouldArrayBeConvertedToPlotDataCorrectlyWithStringArgs() {
    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.INTEGER)
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
    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Age", ColumnType.INTEGER),
        new SeriesInfo("Height", ColumnType.INTEGER)
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
    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Grade", ColumnType.DECIMAL)
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
    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        VALUES,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Reward", ColumnType.DECIMAL)
    );
    Assertions.assertTrue(plotDataOpt.isPresent());
    PlotData plotData = plotDataOpt.get();
    Assertions.assertEquals("Name", plotData.getArgSeries().getName());
    Assertions.assertEquals(Arrays.asList("Ann", "John"), plotData.getArgSeries().getValues());
    Assertions.assertEquals("Reward", plotData.getValuesSeries().getName());
    Assertions.assertEquals(Arrays.asList(1000.0, 100.0), plotData.getValuesSeries().getValues());
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenInvalidDecimalRepresentation(){
    Assertions.assertThrows(InvalidDecimalRepresentation.class, () -> plotConverter.parseValueToDecimalType("22.220.22"));
    Assertions.assertThrows(InvalidDecimalRepresentation.class, () -> plotConverter.parseValueToDecimalType("22,220.22"));
    Assertions.assertThrows(InvalidDecimalRepresentation.class, () -> plotConverter.parseValueToDecimalType("22,220,22"));
  }

}
