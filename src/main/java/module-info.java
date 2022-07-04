module com.tugalsan.api.log {
    requires elemental2.dom;
    requires org.fusesource.jansi;
    requires com.tugalsan.api.pack;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.unsafe;
    requires com.tugalsan.api.compiler;
    requires com.tugalsan.api.executable;
    exports com.tugalsan.api.log.client;
    exports com.tugalsan.api.log.server;
}
