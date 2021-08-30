package builderflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import builder.PlotDataBuilder;
import exception.InvalidPlotTypeProvided;
import exception.NoGroupingFunctionProvidedException;
import java.util.Arrays;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
import org.junit.jupiter.api.Test;
import plotops.functions.GroupingFunction;

public class ArrayPlotDataBuilderTest {

  private final String[][] STANDARD_PLOT_VALUES = {
      {"Name", "Hour"},
      {"First", "1"},
      {"Second", "2"}
  };


  private final String[][] AGGREGATION_PLOT_VALUES = {
      {"Name", "Score"},
      {"Ann", "20"},
      {"Adam", "10"},
      {"Ann", "30"},
      {"Adam", "5"}
  };

  @Test
  public void shouldThrowInvalidPlotTypeStandardCheckThrowError() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Score", ColumnType.INTEGER)
    );

    assertThrows(
        InvalidPlotTypeProvided.class,
        () -> PlotDataBuilder.builder()
            .withPlotInfo(plotInfo)
            .withArray(AGGREGATION_PLOT_VALUES)
            .build()
    );
  }

  @Test
  public void shouldThrowInvalidPlotTypeAggregationCheckThrowError() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Hour", ColumnType.INTEGER)
    );

    assertThrows(
        InvalidPlotTypeProvided.class,
        () -> PlotDataBuilder.builder()
            .withPlotInfo(plotInfo)
            .withArray(STANDARD_PLOT_VALUES)
            .build()
    );
  }

  @Test
  public void shouldBeCorrectStandardPlotData() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Hour", ColumnType.INTEGER)
    );

    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withArray(STANDARD_PLOT_VALUES)
        .build();
    assertFalse(plotData.isEmpty());
    assertEquals(PlotType.STANDARD, plotData.getPlotType());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Hour", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList("First", "Second"), plotData.getArgSeries().getValues());
    assertIterableEquals(Arrays.asList(1, 2), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Name", "Hour"), plotData.getColumns());
  }

  @Test
  public void shouldThrowNoGroupingFunctionProvidedException() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Score", ColumnType.INTEGER)
    );

    assertThrows(
        NoGroupingFunctionProvidedException.class,
        () -> PlotDataBuilder.builder()
            .withPlotInfo(plotInfo)
            .withArray(AGGREGATION_PLOT_VALUES)
            .build()
    );
  }

  @Test
  public void shouldProperlyGroupAvgToPlotData() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Score", ColumnType.INTEGER),
        GroupingFunction.AVG
    );

    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withArray(AGGREGATION_PLOT_VALUES)
        .build();

    assertFalse(plotData.isEmpty());
    assertEquals(PlotType.AGGREGATION, plotData.getPlotType());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Score", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList(7.5, 25.0), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Adam", "Ann"), plotData.getArgSeries().getValues());
  }

  @Test
  public void shouldProperlyGroupMaxToPlotData() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Score", ColumnType.INTEGER),
        GroupingFunction.MAX
    );

    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withArray(AGGREGATION_PLOT_VALUES)
        .build();

    assertFalse(plotData.isEmpty());
    assertEquals(PlotType.AGGREGATION, plotData.getPlotType());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Score", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList(10.0, 30.0), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Adam", "Ann"), plotData.getArgSeries().getValues());
  }

  @Test
  public void shouldProperlyGroupMinToPlotData() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Score", ColumnType.INTEGER),
        GroupingFunction.MIN
    );

    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withArray(AGGREGATION_PLOT_VALUES)
        .build();

    assertFalse(plotData.isEmpty());
    assertEquals(PlotType.AGGREGATION, plotData.getPlotType());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Score", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList(5.0, 20.0), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Adam", "Ann"), plotData.getArgSeries().getValues());
  }

  @Test
  public void shouldProperlyGroupSumToPlotData() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Score", ColumnType.INTEGER),
        GroupingFunction.SUM
    );

    PlotData plotData = PlotDataBuilder.builder()
        .withPlotInfo(plotInfo)
        .withArray(AGGREGATION_PLOT_VALUES)
        .build();

    assertFalse(plotData.isEmpty());
    assertEquals(PlotType.AGGREGATION, plotData.getPlotType());
    assertEquals("Name", plotData.getArgSeries().getName());
    assertEquals("Score", plotData.getValuesSeries().getName());
    assertIterableEquals(Arrays.asList(15.0, 50.0), plotData.getValuesSeries().getValues());
    assertIterableEquals(Arrays.asList("Adam", "Ann"), plotData.getArgSeries().getValues());
  }

}
