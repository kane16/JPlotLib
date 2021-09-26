package pl.delukesoft.jplotlib.builderflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import pl.delukesoft.jplotlib.builder.PlotDataBuilder;
import pl.delukesoft.jplotlib.exception.CsvNotExistingException;
import java.io.File;
import java.util.Arrays;
import pl.delukesoft.jplotlib.model.enums.ColumnType;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.input.SeriesInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvPlotDataBuilderTest {

  @Test
  public void testSuccessfulBuildPlotDataFromCsv() {
    String path = new File(getClass().getClassLoader()
                               .getResource("test-comma.csv")
                               .getPath()).toString();
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Age", ColumnType.INTEGER)
    );
    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withFilePath(path)
        .build();
    assertEquals(PlotType.STANDARD, plotData.getPlotType());
    assertFalse(plotData.isEmpty());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Age", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList("Ann", "John"), plotData.getArgSeries().getValues());
    assertIterableEquals(Arrays.asList(22, 13), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Name", "Age", "Height"), plotData.getColumns());
  }

  @Test
  public void testSuccessfulBuildPlotDataFromCsvWithSemicolonDelimiter() {
    String path = new File(getClass().getClassLoader()
                               .getResource("test-semicolon.csv")
                               .getPath()).toString();
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Age", ColumnType.INTEGER)
    );
    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withFilePathAndDelimiter(path, ";")
        .build();
    assertEquals(PlotType.STANDARD, plotData.getPlotType());
    assertFalse(plotData.isEmpty());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Age", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList("Ann", "John"), plotData.getArgSeries().getValues());
    assertIterableEquals(Arrays.asList(22, 13), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Name", "Age", "Height"), plotData.getColumns());
  }

  @Test
  public void testBuildPlotDataFromNonExistingFile() {
    String path = new File(getClass().getClassLoader()
                               .getResource("test-comma.csv")
                               .getPath()).toString().replace("test-comma.csv", "test-fail.csv");
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Age", ColumnType.INTEGER)
    );
    Assertions.assertThrows(CsvNotExistingException.class, () -> PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withFilePathAndDelimiter(path, ";")
        .build());
  }

}
