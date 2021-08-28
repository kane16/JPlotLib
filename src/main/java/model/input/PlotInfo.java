package model.input;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import model.enums.PlotType;
import plotops.functions.GroupingFunction;

@Getter
@Setter
public class PlotInfo {

  private PlotType plotType;
  private SeriesInfo argsInfo;
  private SeriesInfo valuesInfo;
  private Optional<GroupingFunction> groupingFunction;

  public PlotInfo(PlotType plotType, SeriesInfo argsInfo, SeriesInfo valuesInfo) {
    this.plotType = plotType;
    this.argsInfo = argsInfo;
    this.valuesInfo = valuesInfo;
    groupingFunction = Optional.empty();
  }

  public PlotInfo(
      PlotType plotType,
      SeriesInfo argsInfo,
      SeriesInfo valuesInfo,
      GroupingFunction groupingFunction
  ) {
    this.plotType = plotType;
    this.argsInfo = argsInfo;
    this.valuesInfo = valuesInfo;
    this.groupingFunction = Optional.of(groupingFunction);
  }
}
