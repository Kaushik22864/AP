package com.example.nepaltourismsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Nepal Tourism Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error loading Main.fxml: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}