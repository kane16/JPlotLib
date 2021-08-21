package arrayops.transformation;

import arrayops.model.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityTransformationHelperTest {

  EntityTransformationHelper entityTransformationHelper;

  @BeforeEach
  public void setUp(){
    entityTransformationHelper = new EntityTransformationHelper();
  }

  @Test
  public void shouldTransformationFail() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Value", ColumnType.INTEGER)
    );
    Optional<PlotData> plotDataOpt = entityTransformationHelper
        .convertEntityListToPlotData(Collections.emptyList(), Person.class, plotInfo);
  }

}
