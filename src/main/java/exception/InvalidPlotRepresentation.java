package exception;

public class InvalidPlotRepresentation extends RuntimeException{

  public InvalidPlotRepresentation() {
    super("Invalid representation of data provided");
  }
}
