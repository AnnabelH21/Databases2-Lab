package com.gameshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Path to FXML (Wichtig: Achte auf das führende /)
        URL fxmlLocation = getClass().getResource("/com/gameshop/view/MainDashboard.fxml");

        if (fxmlLocation == null) {
            System.err.println("Fehler: MainDashboard.fxml nicht gefunden!");
            return;
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        primaryStage.setTitle("GameShop Verwalter-Panel");

        // Wir setzen eine angenehme Startgröße
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

