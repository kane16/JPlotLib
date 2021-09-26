module jplotlib {
  requires lombok;
  requires org.slf4j;

  exports pl.delukesoft.jplotlib.exception;
  exports pl.delukesoft.jplotlib.builder;
  exports pl.delukesoft.jplotlib.model.enums;
  exports pl.delukesoft.jplotlib.model.input;
  exports pl.delukesoft.jplotlib.model.output;
  exports pl.delukesoft.jplotlib.plotops.aggregation;
  exports pl.delukesoft.jplotlib.plotops.functions;
}