package com.gameshop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

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
        loadViews("GameTableView");
    }
    private void loadViews(String fxmlName) {
        try {
            Parent node = FXMLLoader.load(getClass().getResource("/com/gameshop/view/" + fxmlName + ".fxml"));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showOrders() {
        loadViews("OrderView");
    }

    @FXML
    private void showSales() {
        loadViews("SalesView");
    }

    @FXML private void showPriceLog() { loadViews("PriceLogView");}

    @FXML private void showGameLog(){ loadViews("GameLogView");}
}