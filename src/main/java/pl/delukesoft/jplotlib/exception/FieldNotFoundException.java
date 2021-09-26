package pl.delukesoft.jplotlib.exception;

public class FieldNotFoundException extends RuntimeException{

  public FieldNotFoundException(String series, String fieldName) {
    super(String.format("%s Field %s does not exist", series, fieldName));
  }

}
