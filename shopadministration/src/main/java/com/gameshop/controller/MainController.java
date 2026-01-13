package com.gameshop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    // Runs automatically at start
    @FXML
    public void initialize() {
        showGames(); // Load game list directly
    }

    @FXML
    private void showGames() {
        loadView("GameTableView");
    }
    private void loadView(String fxmlName) {
        try {
            Parent node = FXMLLoader.load(getClass().getResource("/com/gameshop/view/" + fxmlName + ".fxml"));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showOrders() {
        contentArea.getChildren().setAll(new Label("--- Hier kommen die Bestellungen hin ---"));
    }

    @FXML
    private void showSales() {
        contentArea.getChildren().setAll(new Label("--- Hier kommen die Statistiken hin ---"));
    }
}