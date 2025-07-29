package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.Tourist;
import com.example.nepaltourismsystem.TouristFileHandler;
import com.example.nepaltourismsystem.utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmergencyContactController {

    @FXML private TableView<Tourist> contactTable;
    @FXML private TableColumn<Tourist, String> nameColumn;
    @FXML private TableColumn<Tourist, String> emergencyColumn;
    @FXML private Button menuButton;

    @FXML
    private ComboBox<String> langBox;
    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        changeLanguage("en");

        langBox.setOnAction(event -> {
            String selectedLanguage = langBox.getSelectionModel().getSelectedItem();
            if (selectedLanguage.equals("English")) {
                changeLanguage("en");
            } else if (selectedLanguage.equals("Nepali")) {
                changeLanguage("ne");
            }
        });

        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        emergencyColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmergencyContact()));

        ObservableList<Tourist> touristList = FXCollections.observableArrayList(TouristFileHandler.loadTourists());
        contactTable.setItems(touristList);
    }

    private void changeLanguage(String language) {
        bundle = ResourceBundle.getBundle("lang.messages", new Locale(language));
        nameColumn.setText(bundle.getString("name"));
        emergencyColumn.setText(bundle.getString("emergencyContact"));
        menuButton.setText(bundle.getString("menu"));
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }
}
