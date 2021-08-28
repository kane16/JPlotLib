package plotops.transformation;

import exception.InvalidPlotTypeProvided;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransformationHelperTest {

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

  @BeforeEach
  public void setUp() {
    arrayTransformationHelper = new ArrayTransformationHelper();
  }

//  @Test
//  public void shouldInvalidPlotCheckThrowError() {
//    PlotData invalidPlotData = arrayTransformationHelper.convertArrayToPlotData(
//        AGGREGATION_PLOT_VALUES,
//        new PlotInfo(
//            PlotType.STANDARD,
//            new SeriesInfo("Name", ColumnType.STRING),
//            new SeriesInfo("Score", ColumnType.INTEGER)
//        )
//    );
//    Assertions.assertThrows(
//        InvalidPlotTypeProvided.class,
//        () -> TransformationHelper.resolvePlotType(invalidPlotData)
//    );
//  }

  @Test
  public void shouldValidCheckReturnStandard() {
    PlotData standardPlotData = arrayTransformationHelper.convertArrayToPlotData(
        STANDARD_PLOT_VALUES,
        new PlotInfo(
            PlotType.STANDARD,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Hour", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(
        PlotType.STANDARD,
        TransformationHelper.resolvePlotType(standardPlotData)
    );
  }

  @Test
  public void shouldValidCheckReturnAggregation() {
    PlotData aggregationPlotData = arrayTransformationHelper.convertArrayToPlotData(
        AGGREGATION_PLOT_VALUES,
        new PlotInfo(
            PlotType.AGGREGATION,
            new SeriesInfo("Name", ColumnType.STRING),
            new SeriesInfo("Score", ColumnType.INTEGER)
        )
    );
    Assertions.assertEquals(
        PlotType.AGGREGATION,
        TransformationHelper.resolvePlotType(aggregationPlotData)
    );
  }

}
