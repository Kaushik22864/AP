package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.EmptyFieldException;
import com.example.nepaltourismsystem.Tourist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TouristController {

    @FXML private TableView<Tourist> touristTable;
    @FXML private TableColumn<Tourist, String> nameColumn;
    @FXML private TableColumn<Tourist, String> nationalityColumn;
    @FXML private TableColumn<Tourist, String> contactColumn;
    @FXML private TableColumn<Tourist, String> emergencyContactColumn;

    @FXML private TextField nameField;
    @FXML private TextField nationalityField;
    @FXML private TextField contactField;
    @FXML private TextField emergencyContactField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private final ObservableList<Tourist> touristList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        nationalityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNationality()));
        contactColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContact()));
        emergencyContactColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmergencyContact()));

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
                    emergencyContactField.getText()
            );
            touristList.add(tourist);
            clearFields();
        } catch (EmptyFieldException e) {
            showAlert("Invalid Input", e.getMessage());
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

                touristTable.refresh();
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
        touristTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Tourist tourist) {
        if (tourist != null) {
            nameField.setText(tourist.getName());
            nationalityField.setText(tourist.getNationality());
            contactField.setText(tourist.getContact());
            emergencyContactField.setText(tourist.getEmergencyContact());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
