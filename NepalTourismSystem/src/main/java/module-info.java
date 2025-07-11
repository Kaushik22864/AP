module com.example.nepaltourismsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.pdfbox;
    requires javafx.swing;

    opens com.example.nepaltourismsystem to javafx.fxml;
    exports com.example.nepaltourismsystem;
    exports com.example.nepaltourismsystem.controller;
    opens com.example.nepaltourismsystem.controller to javafx.fxml;
}