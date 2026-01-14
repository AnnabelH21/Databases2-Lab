package com.gameshop.controller;

import com.gameshop.model.Game;
import com.gameshop.model.GameLog;
import com.gameshop.model.PriceLog;
import com.gameshop.repository.GameRepository;
import com.gameshop.repository.LogsRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class GameLogController {

    @FXML private TableView<GameLog> gameLogTable;
    @FXML private TableColumn<GameLog, Integer> colLogId;
    @FXML private TableColumn<GameLog, Integer> colGameId;
    @FXML private TableColumn<GameLog, String> colName;
    @FXML private TableColumn<GameLog, Double> colPrice;
    @FXML private TableColumn<GameLog, Integer> colInventory;
    @FXML private TableColumn<GameLog, LocalDateTime> colDeleteDate;

    private LogsRepository logsRepo = new LogsRepository();

    @FXML
    public void initialize() {
        // Define which attributes from model belong in which column
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        colGameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("lastPrice"));
        colInventory.setCellValueFactory(new PropertyValueFactory<>("inventoryAtDelete"));
        colDeleteDate.setCellValueFactory(new PropertyValueFactory<>("deleteDate"));

        // Load Data
        refreshTable();
    }

    public void refreshTable() {

        gameLogTable.setItems(FXCollections.observableArrayList(logsRepo.getAllGameLogs()));
    }
}
