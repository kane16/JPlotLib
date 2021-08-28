package exception;

public class ColumnNotFoundException extends RuntimeException{

  public ColumnNotFoundException(String series, String columnName) {
    super(String.format("%s Column %s does not exist", series, columnName));
  }
}
