package com.example.nepaltourismsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
// for changing scenes
public class utility {

    public static Stage getCurrentWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        return (Stage) node.getScene().getWindow();
    }

    public static void changeScene(ActionEvent actionEvent, String sceneName, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load()); // removed fixed width and height
        scene.getStylesheets().add(utility.class.getResource("/style/style.css").toExternalForm());

        Stage stage = getCurrentWindow(actionEvent);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setMaximized(true); // put this after setting the scene
    }
}

//FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(sceneName)); -> May come error due to this line

