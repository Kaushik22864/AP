package com.example.nepaltourismsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nepaltourismsystem/Main.fxml"));
            Scene scene = new Scene(loader.load());

//            Load the custom CSS for styling
            scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

            primaryStage.setTitle("Nepal Tourism Management System");
            primaryStage.setScene(scene);
//            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Error loading Main.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}