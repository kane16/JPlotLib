package arrayops;

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

public class GroupingConverterTest {

  PlotHelper plotHelper;
  PlotConverter plotConverter;

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
    plotHelper = new PlotHelper();
    plotConverter = new StandardPlotConverter(plotHelper);
  }

  @Test
  public void shouldInvalidPlotCheckThrowError() {
    Optional<PlotData> invalidPlotData = plotConverter.convertArrayToPlotData(
        invalidPlot1,
        new PlotInfo(
            PlotType.STANDARD,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Surname", ColumnType.STRING)
        )
    );
    Assertions.assertThrows(
        InvalidPlotRepresentation.class,
        () -> plotHelper.resolvePlotType(invalidPlotData.get())
    );
  }

  @Test
  public void shouldValidCheckReturnStandard() {
    Optional<PlotData> standardPlotData = plotConverter.convertArrayToPlotData(
        standardPlot,
        new PlotInfo(
            PlotType.STANDARD,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Hour", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(PlotType.STANDARD, plotHelper.resolvePlotType(standardPlotData.get()));
  }

  @Test
  public void shouldValidCheckReturnAggregation() {
    Optional<PlotData> aggregationPlotData = plotConverter.convertArrayToPlotData(
        aggregationPlot,
        new PlotInfo(
            PlotType.AGGREGATION,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Score", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(PlotType.AGGREGATION, plotHelper.resolvePlotType(aggregationPlotData.get()));
  }

}
