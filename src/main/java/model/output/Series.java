package model.output;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The generic Series.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Series<T> {

  private String name;
  private List<T> values;

}
