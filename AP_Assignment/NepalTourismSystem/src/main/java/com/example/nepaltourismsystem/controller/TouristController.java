package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.EmptyFieldException;
import com.example.nepaltourismsystem.Tourist;
import com.example.nepaltourismsystem.TouristFileHandler;
import com.example.nepaltourismsystem.utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class TouristController {

    @FXML private TableView<Tourist> touristTable;
    @FXML private TableColumn<Tourist, String> nameColumn;
    @FXML private TableColumn<Tourist, String> nationalityColumn;
    @FXML private TableColumn<Tourist, String> contactColumn;
    @FXML private TableColumn<Tourist, String> emergencyContactColumn;
    @FXML private TableColumn<Tourist, String> passportColumn;

    @FXML private TextField nameField;
    @FXML private TextField nationalityField;
    @FXML private TextField contactField;
    @FXML private TextField emergencyContactField;
    @FXML private TextField passportField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    @FXML private Button menuButton;

    private final ObservableList<Tourist> touristList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        nationalityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNationality()));
        contactColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContact()));
        emergencyContactColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmergencyContact()));
        passportColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPassportNumber()));

        // Load saved tourists into table
        touristList.addAll(TouristFileHandler.loadTourists());
        touristTable.setItems(touristList);

        touristTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> populateFields(newSelection));

        addButton.setOnAction(e -> addTourist());
        updateButton.setOnAction(e -> updateTourist());
        deleteButton.setOnAction(e -> deleteTourist());
        clearButton.setOnAction(e -> clearFields());
    }

    private void addTourist() {
        try {
            Tourist tourist = new Tourist(
                    nameField.getText(),
                    nationalityField.getText(),
                    contactField.getText(),
                    emergencyContactField.getText(),
                    passportField.getText()
            );
            touristList.add(tourist);
            TouristFileHandler.saveTourists(touristList); // ✅ Save to file
            clearFields();
        } catch (EmptyFieldException e) {
            showAlert("Invalid Input", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTourist() {
        Tourist selected = touristTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setName(nameField.getText());
                selected.setNationality(nationalityField.getText());
                selected.setContact(contactField.getText());
                selected.setEmergencyContact(emergencyContactField.getText());
                selected.setPassportNumber(passportField.getText());

                touristTable.refresh();
                TouristFileHandler.saveTourists(touristList); // ✅ Save after update
                clearFields();
            } catch (EmptyFieldException e) {
                showAlert("Invalid Input", e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a tourist to update.");
        }
    }

    private void deleteTourist() {
        Tourist selected = touristTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            touristList.remove(selected);
            TouristFileHandler.saveTourists(touristList); // ✅ Save after delete
            clearFields();
        } else {
            showAlert("No Selection", "Please select a tourist to delete.");
        }
    }

    private void clearFields() {
        nameField.clear();
        nationalityField.clear();
        contactField.clear();
        emergencyContactField.clear();
        passportField.clear();
        touristTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Tourist tourist) {
        if (tourist != null) {
            nameField.setText(tourist.getName());
            nationalityField.setText(tourist.getNationality());
            contactField.setText(tourist.getContact());
            emergencyContactField.setText(tourist.getEmergencyContact());
            passportField.setText(tourist.getPassportNumber());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent, "/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }
}
