package model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.ColumnType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeriesInfo {
  String columnName;
  ColumnType columnType;
}
