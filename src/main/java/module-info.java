module com.github.thelittlestone.tfc_forge_solver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.databind;

    opens com.github.thelittlestone to javafx.fxml;
    exports com.github.thelittlestone;
    exports com.github.thelittlestone.logic.json;
    exports com.github.thelittlestone.logic.components;
    exports com.github.thelittlestone.config;
    exports com.github.thelittlestone.exception;
    opens com.github.thelittlestone.logic.json to javafx.fxml;
}