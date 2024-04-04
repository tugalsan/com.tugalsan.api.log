module com.tugalsan.api.log {
    requires javax.servlet.api;
    requires org.fusesource.jansi;
    requires com.tugalsan.api.tuple;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.callable;
    requires com.tugalsan.api.union;
    requires com.tugalsan.api.runnable;
    exports com.tugalsan.api.log.client;
    exports com.tugalsan.api.log.server;
}
