package com.example.c195_software2.controller;

import com.example.c195_software2.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {
    public void openNewView(ActionEvent event, String view, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/" + view));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set title for new view
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
