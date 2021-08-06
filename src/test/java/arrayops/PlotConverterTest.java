package arrayops;

import exception.ColumnTypeMismatchException;
import java.util.Arrays;
import java.util.Optional;
import model.ColumnType;
import model.PlotData;
import model.SeriesInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlotConverterTest {

  private PlotConverter plotConverter;

  @BeforeEach
  public void setUp() {
    plotConverter = new PlotConverter();
  }

  @Test
  public void shouldArrayConversionThrowConversionMismatch(){
    String[][] values = {
        {"Name", "Age", "Height"},
        {"Ann", "22", "165"},
        {"John", "13", "144"}
    };

    Assertions.assertThrows(ColumnTypeMismatchException.class, () -> plotConverter.convertArrayToPlotData(
        values,
        new SeriesInfo("Name", ColumnType.INTEGER),
        new SeriesInfo("Height", ColumnType.INTEGER)
    ));
  }

  @Test
  public void shouldArrayConversionThrowErrorWhenArgsHeaderNotExisting(){
    String[][] values = {
        {"Name", "Age", "Height"},
        {"Ann", "22", "165"},
        {"John", "13", "144"}
    };

    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        values,
        new SeriesInfo("Surname", ColumnType.STRING),
        new SeriesInfo("Height", ColumnType.INTEGER)
        );
    Assertions.assertTrue(plotDataOpt.isEmpty());
  }

  @Test
  public void shouldArrayBeConvertedToPlotData() {
    String[][] values = {
        {"Name", "Age", "Height"},
        {"Ann", "22", "165"},
        {"John", "13", "144"}
    };
    Optional<PlotData> plotDataOpt = plotConverter.convertArrayToPlotData(
        values,
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

}
