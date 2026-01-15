package com.gameshop.games;

import com.gameshop.model.Game;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GameTableController {

    @FXML private TableView<Game> gameTable;
    @FXML private TableColumn<Game, Integer> colId;
    @FXML private TableColumn<Game, String> colName;
    @FXML private TableColumn<Game, Double> colPrice;
    @FXML private TableColumn<Game, Integer> colStock;

    private GameRepository gameRepo = new GameRepository();

    @FXML
    public void initialize() {
        // Define which attributes from model belong in which column
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("inventory"));

        // Load Data
        refreshTable();
    }

    public void refreshTable() {

        gameTable.setItems(FXCollections.observableArrayList(gameRepo.findAll()));
    }
}