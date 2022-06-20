module com.tugalsan.api.log {
    requires elemental2.dom;
    requires org.fusesource.jansi;
    requires com.tugalsan.api.pack;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.compiler;
    exports com.tugalsan.api.log.client;
    exports com.tugalsan.api.log.server;
}
