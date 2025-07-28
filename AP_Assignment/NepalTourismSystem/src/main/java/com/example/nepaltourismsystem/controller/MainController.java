package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private Button touristButton;
    @FXML private Button guideButton;
    @FXML private Button attractionButton;
    @FXML private Button bookingButton;
    @FXML private Button reportButton;

//    @FXML
//    public void initialize() {
//        touristButton.setOnAction(e -> System.out.println("Tourist module clicked"));
//        guideButton.setOnAction(e -> System.out.println("Guide module clicked"));
//        attractionButton.setOnAction(e -> System.out.println("Attraction module clicked"));
//        bookingButton.setOnAction(e -> System.out.println("Booking module clicked"));
//        reportButton.setOnAction(e -> System.out.println("Reports module clicked"));
//    }

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
