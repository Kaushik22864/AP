package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.Attraction;
import com.example.nepaltourismsystem.AttractionFileHandler;
import com.example.nepaltourismsystem.EmptyFieldException;
import com.example.nepaltourismsystem.utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AttractionController {

    @FXML private TableView<Attraction> attractionTable;
    @FXML private TableColumn<Attraction, String> nameColumn;
    @FXML private TableColumn<Attraction, String> typeColumn;
    @FXML private TableColumn<Attraction, String> locationColumn;
    @FXML private TableColumn<Attraction, String> descriptionColumn;

    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextField locationField;
    @FXML private TextArea descriptionArea;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button menuButton;

    private final ObservableList<Attraction> attractionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        typeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));
        locationColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLocation()));
        descriptionColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));

        attractionList.addAll(AttractionFileHandler.loadAttractions()); // ✅ Load from file
        attractionTable.setItems(attractionList);

        attractionTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> populateFields(newSelection));

        addButton.setOnAction(e -> addAttraction());
        updateButton.setOnAction(e -> updateAttraction());
        deleteButton.setOnAction(e -> deleteAttraction());
        clearButton.setOnAction(e -> clearFields());
    }

    private void addAttraction() {
        try {
            Attraction attraction = new Attraction(
                    nameField.getText(),
                    typeField.getText(),
                    locationField.getText(),
                    descriptionArea.getText()
            );
            attractionList.add(attraction);
            AttractionFileHandler.saveAttractions(attractionList); // ✅ Save
            clearFields();
        } catch (EmptyFieldException e) {
            showAlert("Validation Error", e.getMessage());
        }
    }

    private void updateAttraction() {
        Attraction selected = attractionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setName(nameField.getText());
                selected.setType(typeField.getText());
                selected.setLocation(locationField.getText());
                selected.setDescription(descriptionArea.getText());

                attractionTable.refresh();
                AttractionFileHandler.saveAttractions(attractionList); // ✅ Save
                clearFields();
            } catch (EmptyFieldException e) {
                showAlert("Validation Error", e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select an attraction to update.");
        }
    }

    private void deleteAttraction() {
        Attraction selected = attractionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            attractionList.remove(selected);
            AttractionFileHandler.saveAttractions(attractionList); // ✅ Save
            clearFields();
        } else {
            showAlert("No Selection", "Please select an attraction to delete.");
        }
    }

    private void clearFields() {
        nameField.clear();
        typeField.clear();
        locationField.clear();
        descriptionArea.clear();
        attractionTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Attraction attraction) {
        if (attraction != null) {
            nameField.setText(attraction.getName());
            typeField.setText(attraction.getType());
            locationField.setText(attraction.getLocation());
            descriptionArea.setText(attraction.getDescription());
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
