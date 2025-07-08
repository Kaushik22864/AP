package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GuideController {

    @FXML private TableView<Guide> guideTable;
    @FXML private TableColumn<Guide, String> nameColumn;
    @FXML private TableColumn<Guide, String> languageColumn;
    @FXML private TableColumn<Guide, String> experienceColumn;
    @FXML private TableColumn<Guide, String> contactColumn;

    @FXML private TextField nameField;
    @FXML private TextField languageField; // Comma-separated string input
    @FXML private TextField experienceField;
    @FXML private TextField contactField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    @FXML private Button menuButton;

    private final ObservableList<Guide> guideList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        languageColumn.setCellValueFactory(data -> {
            List<String> langs = data.getValue().getLanguages();
            return new javafx.beans.property.SimpleStringProperty(String.join(", ", langs));
        });

        experienceColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getExperienceYears())));

        contactColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getContact()));

        guideTable.setItems(guideList);

        guideTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> populateFields(newSelection));

        addButton.setOnAction(e -> addGuide());
        updateButton.setOnAction(e -> updateGuide());
        deleteButton.setOnAction(e -> deleteGuide());
        clearButton.setOnAction(e -> clearFields());
    }

    private void addGuide() {
        try {
            String name = nameField.getText();
            String langInput = languageField.getText();
            int experience = Integer.parseInt(experienceField.getText());
            String contact = contactField.getText();

            List<String> languages = Arrays.stream(langInput.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            Guide guide = new Guide(name, languages, experience, contact);
            guideList.add(guide);
            clearFields();

        } catch (EmptyFieldException | NullObjectException | NegativeValueException e) {
            showAlert("Validation Error", e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Experience must be a valid number.");
        }
    }

    private void updateGuide() {
        Guide selected = guideTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setName(nameField.getText());

                List<String> langs = Arrays.stream(languageField.getText().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                selected.setLanguages(langs);

                selected.setExperienceYears(Integer.parseInt(experienceField.getText()));
                selected.setContact(contactField.getText());

                guideTable.refresh();
                clearFields();
            } catch (EmptyFieldException | NullObjectException | NegativeValueException e) {
                showAlert("Validation Error", e.getMessage());
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Experience must be a valid number.");
            }
        } else {
            showAlert("No Selection", "Please select a guide to update.");
        }
    }

    private void deleteGuide() {
        Guide selected = guideTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            guideList.remove(selected);
            clearFields();
        } else {
            showAlert("No Selection", "Please select a guide to delete.");
        }
    }

    private void clearFields() {
        nameField.clear();
        languageField.clear();
        experienceField.clear();
        contactField.clear();
        guideTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Guide guide) {
        if (guide != null) {
            nameField.setText(guide.getName());
            languageField.setText(String.join(", ", guide.getLanguages()));
            experienceField.setText(String.valueOf(guide.getExperienceYears()));
            contactField.setText(guide.getContact());
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
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }
}
