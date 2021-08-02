package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlotData<X, Y extends Number> {

  private Series<X> argSeries;
  private Series<Y> valuesSeries;

}
