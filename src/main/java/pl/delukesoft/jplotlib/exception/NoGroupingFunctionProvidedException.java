package pl.delukesoft.jplotlib.exception;

public class NoGroupingFunctionProvidedException extends RuntimeException{

  public NoGroupingFunctionProvidedException() {
    super("No grouping function for Aggregation plot type provided");
  }
}
