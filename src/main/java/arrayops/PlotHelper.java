package arrayops;

import exception.InvalidPlotRepresentation;
import model.enums.PlotType;

public class PlotHelper {

  PlotType resolvePlotType(String[][] array){
    if (!isEmpty(array)) {
      return PlotType.STANDARD;
    } else {
      throw new InvalidPlotRepresentation();
    }
  }

  private boolean isEmpty(String[][] array) {
    return array == null || array.length <= 1;
  }

}
