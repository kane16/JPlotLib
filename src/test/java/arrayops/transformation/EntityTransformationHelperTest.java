package arrayops.transformation;

import arrayops.model.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.enums.ColumnType;
import model.enums.PlotType;
import model.input.PlotInfo;
import model.input.SeriesInfo;
import model.output.PlotData;
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
    Optional<PlotData> plotDataOpt = entityTransformationHelper
        .convertEntityListToPlotData(Collections.emptyList(), Person.class, plotInfo);
    Assertions.assertEquals(Optional.empty(), plotDataOpt);
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
    Optional<PlotData> plotDataOpt = entityTransformationHelper
        .convertEntityListToPlotData(personList, Person.class, plotInfo);
    Assertions.assertTrue(plotDataOpt.isPresent());
    Assertions.assertIterableEquals(plotDataOpt.get().getArgSeries().getValues(), Arrays.asList("Adam", "Natalie"));
    Assertions.assertIterableEquals(plotDataOpt.get().getValuesSeries().getValues(), Arrays.asList(22, 33));
    Assertions.assertEquals(plotDataOpt.get().getValuesSeries().getName(), "age");
    Assertions.assertEquals(plotDataOpt.get().getArgSeries().getName(), "name");
    Assertions.assertEquals(plotDataOpt.get().getPlotType(), PlotType.STANDARD);
  }

}
