package pl.delukesoft.jplotlib.builderflow;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.delukesoft.jplotlib.builder.PlotDataBuilder;
import pl.delukesoft.jplotlib.builder.PlotInfoDataBuilder;
import pl.delukesoft.jplotlib.exception.FieldNotFoundException;
import pl.delukesoft.jplotlib.model.Person;
import pl.delukesoft.jplotlib.model.enums.ColumnType;
import pl.delukesoft.jplotlib.model.enums.PlotType;
import pl.delukesoft.jplotlib.model.input.PlotInfo;
import pl.delukesoft.jplotlib.model.input.SeriesInfo;
import pl.delukesoft.jplotlib.model.output.PlotData;

public class EntityPlotDataBuilderTest {

  @Test
  public void shouldEntityPlotBuilderFail() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("Name", ColumnType.STRING),
        new SeriesInfo("Value", ColumnType.INTEGER)
    );
    Assertions.assertThrows(FieldNotFoundException.class, () -> PlotDataBuilder.builder()
        .withEntityList(Collections.emptyList(), Person.class)
        .withPlotInfo(plotInfo)
        .build());
  }

  @Test
  public void shouldPerformEntityPlotBuildingSuccessfully() {
    PlotInfo plotInfo = new PlotInfo(
        PlotType.STANDARD,
        new SeriesInfo("name", ColumnType.STRING),
        new SeriesInfo("age", ColumnType.INTEGER)
    );
    List<Person> personList = new ArrayList<>();
    personList.add(new Person("Adam", 22));
    personList.add(new Person("Natalie", 33));
    PlotData plotData = PlotDataBuilder.builder()
        .withEntityList(personList, Person.class)
        .withPlotInfo(plotInfo)
        .build();
    Assertions.assertIterableEquals(
        plotData.getArgSeries().getValues(),
        Arrays.asList("Adam", "Natalie")
    );
    Assertions.assertIterableEquals(Arrays.asList(22, 33), plotData.getValuesSeries().getValues());
    Assertions.assertEquals("age", plotData.getValuesSeries().getName());
    Assertions.assertEquals("name", plotData.getArgSeries().getName());
    Assertions.assertEquals(PlotType.STANDARD, plotData.getPlotType());
    Assertions.assertIterableEquals(Arrays.asList("name", "age"), plotData.getColumns());
  }

  @Test
  public void shouldExtractCorrectlyEntityFields() {
    List<Person> personList = new ArrayList<>();
    personList.add(new Person("Adam", 22));
    personList.add(new Person("Natalie", 33));
    PlotInfoDataBuilder<Person> builder = PlotDataBuilder.builder()
        .withEntityList(personList, Person.class);
    assertIterableEquals(Arrays.asList("name", "age"), builder.extractColumns());
  }

}
