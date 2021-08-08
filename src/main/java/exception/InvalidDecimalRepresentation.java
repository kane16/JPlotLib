package exception;

public class InvalidDecimalRepresentation extends RuntimeException{

  public InvalidDecimalRepresentation(String value){
    super("Invalid number representation: " + value);
  }

}
