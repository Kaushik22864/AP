package com.example.nepaltourismsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class CheckUIMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nepaltourismsystem/AttractionView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
             scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

            primaryStage.setTitle("Tourist Management Test View");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true); // Optional for fullscreen
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Failed to load TouristView.fxml:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}