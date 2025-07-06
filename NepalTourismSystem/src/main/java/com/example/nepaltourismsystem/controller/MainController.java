package com.example.nepaltourismsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML private Button touristButton;
    @FXML private Button guideButton;
    @FXML private Button attractionButton;
    @FXML private Button bookingButton;
    @FXML private Button reportButton;

    @FXML
    public void initialize() {
        touristButton.setOnAction(e -> System.out.println("Tourist module clicked"));
        guideButton.setOnAction(e -> System.out.println("Guide module clicked"));
        attractionButton.setOnAction(e -> System.out.println("Attraction module clicked"));
        bookingButton.setOnAction(e -> System.out.println("Booking module clicked"));
        reportButton.setOnAction(e -> System.out.println("Reports module clicked"));
    }
}
