package exception;

public class ColumnTypeMismatchException extends RuntimeException {

  public ColumnTypeMismatchException() {
    super("Type mismatch, couldn't extract column values");
  }
}
