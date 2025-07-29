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
import java.util.Locale;
import java.util.ResourceBundle;

public class AttractionController {

    @FXML private TableView<Attraction> attractionTable;
    @FXML private TableColumn<Attraction, String> nameColumn;
    @FXML private TableColumn<Attraction, String> typeColumn;
    @FXML private TableColumn<Attraction, String> locationColumn;
    @FXML private TableColumn<Attraction, String> descriptionColumn;
    @FXML private TableColumn<Attraction, String> altitudeColumn;

    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextField locationField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField altitudeArea;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button menuButton;

    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label locationLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label altitudeLabel;

    @FXML
    private ComboBox<String> langBox;
    private ResourceBundle bundle;

    private final ObservableList<Attraction> attractionList = FXCollections.observableArrayList();

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
        typeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));
        locationColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLocation()));
        descriptionColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));
        altitudeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAltitude().toString()));

        attractionList.addAll(AttractionFileHandler.loadAttractions()); //  Load from file
        attractionTable.setItems(attractionList);

        attractionTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> populateFields(newSelection));

        addButton.setOnAction(e -> addAttraction());
        updateButton.setOnAction(e -> updateAttraction());
        deleteButton.setOnAction(e -> deleteAttraction());
        clearButton.setOnAction(e -> clearFields());
    }

    private void changeLanguage(String language) {
        bundle = ResourceBundle.getBundle("lang.messages", new Locale(language));
        nameColumn.setText(bundle.getString("name"));
        typeColumn.setText(bundle.getString("type"));
        locationColumn.setText(bundle.getString("location"));
        descriptionColumn.setText(bundle.getString("description"));
        altitudeColumn.setText(bundle.getString("altitude"));
        addButton.setText(bundle.getString("add"));
        updateButton.setText(bundle.getString("update"));
        deleteButton.setText(bundle.getString("delete"));
        clearButton.setText(bundle.getString("clear"));
        menuButton.setText(bundle.getString("menu"));
        nameLabel.setText(bundle.getString("name"));
        typeLabel.setText(bundle.getString("type"));
        locationLabel.setText(bundle.getString("location"));
        descriptionLabel.setText(bundle.getString("description"));
        altitudeLabel.setText(bundle.getString("altitude"));
    }

    private void addAttraction() {
        try {
            Attraction attraction = new Attraction(
                    nameField.getText(),
                    typeField.getText(),
                    locationField.getText(),
                    descriptionArea.getText(),
                    Double.parseDouble(altitudeArea.getText())
            );
            attractionList.add(attraction);
            AttractionFileHandler.saveAttractions(attractionList); //  Save
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
                selected.setAltitude(Double.parseDouble(altitudeArea.getText()));

                attractionTable.refresh();
                AttractionFileHandler.saveAttractions(attractionList); //  Save
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
            AttractionFileHandler.saveAttractions(attractionList); //  Save
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
        altitudeArea.clear();
    }

    private void populateFields(Attraction attraction) {
        if (attraction != null) {
            nameField.setText(attraction.getName());
            typeField.setText(attraction.getType());
            locationField.setText(attraction.getLocation());
            descriptionArea.setText(attraction.getDescription());
            altitudeArea.setText(attraction.getAltitude().toString());
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
