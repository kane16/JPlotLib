package model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.PlotType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlotInfo {

  private PlotType plotType;
  private SeriesInfo argsInfo;
  private SeriesInfo valuesInfo;

}
