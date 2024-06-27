module com.tugalsan.api.log {
    requires javax.servlet.api;
    requires org.fusesource.jansi;
    requires com.tugalsan.api.tuple;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.unsafe;
    requires com.tugalsan.api.function;
    
    exports com.tugalsan.api.log.client;
    exports com.tugalsan.api.log.server;
}
