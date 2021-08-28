package plotops.mapper;

import model.enums.PlotType;
import model.input.PlotInfo;
import model.output.PlotData;

public interface DataMapper {

  public PlotType getPlotType();

  public PlotData mapPlotData(PlotData plotData, PlotInfo plotInfo);


}
