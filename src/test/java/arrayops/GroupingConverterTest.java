package arrayops;

import exception.InvalidPlotRepresentation;
import model.enums.PlotType;
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

  private String[][] invalidPlot2 = {
      {}
  };

  @BeforeEach
  public void setUp(){
    plotHelper = new PlotHelper();
    plotConverter = new StandardPlotConverter(plotHelper);
  }

  @Test
  public void shouldInvalidPlotCheckThrowError(){
    Assertions.assertThrows(InvalidPlotRepresentation.class, () -> plotHelper.resolvePlotType(invalidPlot1));
    Assertions.assertThrows(InvalidPlotRepresentation.class, () -> plotHelper.resolvePlotType(invalidPlot2));
  }

  @Test
  public void shouldValidCheckReturnStandard(){
    Assertions.assertEquals(PlotType.STANDARD, plotHelper.resolvePlotType(standardPlot));
  }

  @Test
  public void shouldValidCheckReturnAggregation(){
    Assertions.assertEquals(PlotType.AGGREGATION, plotHelper.resolvePlotType(aggregationPlot));
  }

}
