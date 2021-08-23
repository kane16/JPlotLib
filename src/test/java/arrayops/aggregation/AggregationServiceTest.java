package arrayops.aggregation;

import arrayops.functions.GroupingFunction;
import arrayops.transformation.ArrayTransformationHelper;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AggregationServiceTest {

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

  private ArrayTransformationHelper arrayTransformationHelper;
  private AggregationService aggregationService;

  @BeforeEach
  void setUp() {
    this.arrayTransformationHelper = new ArrayTransformationHelper();
    this.aggregationService = new AggregationService();
  }

  @Test
  public void shouldFailedAggregation(){
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Hour", ColumnType.INTEGER)
    );
    PlotData standardPlotData = arrayTransformationHelper.convertArrayToPlotData(
        STANDARD_PLOT_VALUES, plotInfo
    ).get();
    Assertions.assertEquals(Optional.empty(), aggregationService.performAggregation(standardPlotData, plotInfo));
  }

  @Test
  public void shouldReturnSuccessfulAndUnchangedAggregation(){
    PlotInfo plotInfo = new PlotInfo(
        PlotType.AGGREGATION,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Hour", ColumnType.INTEGER),
        GroupingFunction.AVG
    );
    PlotData standardPlotData = arrayTransformationHelper.convertArrayToPlotData(
        STANDARD_PLOT_VALUES, plotInfo
    ).get();
    Assertions.assertEquals(Optional.of(standardPlotData), aggregationService.performAggregation(standardPlotData, plotInfo));
  }

//    @Test
//    public void shouldGrouping() {
//      Optional<PlotData> aggregationPlotData = arrayTransformationHelper.convertArrayToPlotData(
//          AGGREGATION_PLOT_VALUES,
//          new PlotInfo(
//              PlotType.AGGREGATION,
//              new SeriesInfo("Name", ColumnType.STRING),
//              new SeriesInfo("Score", ColumnType.INTEGER)
//          )
//      );
//      Assertions.assertEquals(PlotType.AGGREGATION, arrayTransformationHelper.resolvePlotType(aggregationPlotData.get()));
//    }

}
