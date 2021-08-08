package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.PlotType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlotData {

  private Series<String> argSeries;
  private Series<Number> valuesSeries;
  private PlotType plotType;

}
