package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {

    @FXML private Button touristButton;
    @FXML private Button guideButton;
    @FXML private Button attractionButton;
    @FXML private Button bookingButton;
    @FXML private Button reportButton;
    @FXML private Button emergencyButton;
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
    }

    private void changeLanguage(String language) {
        bundle = ResourceBundle.getBundle("lang.messages", new Locale(language));
        touristButton.setText(bundle.getString("tourist_button"));
        guideButton.setText(bundle.getString("guide_button"));
        attractionButton.setText(bundle.getString("attraction_button"));
        bookingButton.setText(bundle.getString("booking_button"));
        reportButton.setText(bundle.getString("report_button"));
        emergencyButton.setText(bundle.getString("emergency_button"));
    }

    public void touristPage(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/TouristView.fxml", "Tourist Page");
    }

    public void guidePage(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/GuideView.fxml", "Guide Page");
    }

    public void attractionPage(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/AttractionView.fxml", "Attraction Page");
    }

    public void bookingPage(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/BookingView.fxml", "Booking Page");
    }

    public void reportPage(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/ReportsView.fxml", "Report Page");
    }

    public void emergencyPage(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent, "/com/example/nepaltourismsystem/EmergencyContactView.fxml", "Emergency Contacts");
    }
}
