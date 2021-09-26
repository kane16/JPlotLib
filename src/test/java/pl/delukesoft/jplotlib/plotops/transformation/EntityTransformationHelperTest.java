package pl.delukesoft.jplotlib.plotops.transformation;

import pl.delukesoft.jplotlib.exception.FieldNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import pl.delukesoft.jplotlib.model.Person;
import pl.delukesoft.jplotlib.model.enums.ColumnType;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.input.SeriesInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;
import org.junit.jupiter.api.Assertions;
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
    Assertions.assertThrows(FieldNotFoundException.class, () -> entityTransformationHelper
        .convertEntityListToPlotData(Collections.emptyList(), Person.class, plotInfo));
  }

  @Test
  public void shouldPerformTransformationSuccessfully(){
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("name", ColumnType.STRING),
        new SeriesInfo("age", ColumnType.INTEGER)
    );
    List<Person> personList = new ArrayList<>();
    personList.add(new Person("Adam", 22));
    personList.add(new Person("Natalie", 33));
    PlotData plotData = entityTransformationHelper
        .convertEntityListToPlotData(personList, Person.class, plotInfo);
    Assertions.assertIterableEquals(plotData.getArgSeries().getValues(), Arrays.asList("Adam", "Natalie"));
    Assertions.assertIterableEquals(plotData.getValuesSeries().getValues(), Arrays.asList(22, 33));
    Assertions.assertEquals(plotData.getValuesSeries().getName(), "age");
    Assertions.assertEquals(plotData.getArgSeries().getName(), "name");
    Assertions.assertEquals(plotData.getPlotType(), PlotType.STANDARD);
  }

}
