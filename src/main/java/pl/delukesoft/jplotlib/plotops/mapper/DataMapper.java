package pl.delukesoft.jplotlib.plotops.mapper;

import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;

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
