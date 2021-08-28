package exception;

public class InvalidPlotTypeProvided extends RuntimeException{

  public InvalidPlotTypeProvided() {
    super("Invalid plot type provided");
  }
}
