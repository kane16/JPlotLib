package plotops.mapper;

import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

/**
 * The interface for mapping Plot Data.
 */
public interface DataMapper {

  /**
   * Gets plot type.
   *
   * @return the plot type
   */
  public PlotType getPlotType();

  /**
   * Performs mapping operation - specification of transformation is in deriving classes.
   *
   * @param plotData the plot data
   * @param plotInfo the plot info
   * @return the plot data
   */
  public PlotData mapPlotData(PlotData plotData, PlotInfo plotInfo);


}
