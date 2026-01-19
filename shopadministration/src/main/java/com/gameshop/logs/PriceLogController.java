package com.gameshop.logs;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;

public class PriceLogController {

    @FXML
    private TableView<PriceLog> priceLogTable;
    @FXML private TableColumn<PriceLog, Integer> colLogId;
    @FXML private TableColumn<PriceLog, String> colGameId;
    @FXML private TableColumn<PriceLog, Double> colOldPrice;
    @FXML private TableColumn<PriceLog, Integer> colNewPrice;
    @FXML private TableColumn<PriceLog, LocalDateTime> colChangeDate;

    private LogsRepository priceLogRepo = new LogsRepository();

    @FXML
    public void initialize() {
        // Define which attributes from model belong in which column
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        colGameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        colOldPrice.setCellValueFactory(new PropertyValueFactory<>("oldPrice"));
        colNewPrice.setCellValueFactory(new PropertyValueFactory<>("newPrice"));
        colChangeDate.setCellValueFactory(new PropertyValueFactory<>("changeDate"));

        // Load Data
        refreshTable();
    }

    public void refreshTable() {

        priceLogTable.setItems(FXCollections.observableArrayList(priceLogRepo.getAllPriceLogs()));
    }
}
