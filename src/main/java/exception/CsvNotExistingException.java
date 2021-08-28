package exception;

public class CsvNotExistingException extends RuntimeException{

  public CsvNotExistingException() {
    super("CSV couldn't be parsed. File does not exist.");
  }
}
