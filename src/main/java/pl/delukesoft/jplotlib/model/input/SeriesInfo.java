package pl.delukesoft.jplotlib.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.delukesoft.jplotlib.model.enums.ColumnType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeriesInfo {
  String columnName;
  ColumnType columnType;
}
