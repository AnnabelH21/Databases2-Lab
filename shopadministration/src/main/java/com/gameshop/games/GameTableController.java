package com.gameshop.games;

import com.gameshop.model.Game;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class GameTableController {

    @FXML private TableView<Game> gameTable;
    @FXML private TableColumn<Game, Integer> colId;
    @FXML private TableColumn<Game, String> colName;
    @FXML private TableColumn<Game, Double> colPrice;
    @FXML private TableColumn<Game, Integer> colStock;

    private GameRepository gameRepo = new GameRepository();
    private GamesService gameService = new GamesService();

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

    @FXML
    private void handleOpenAddDialog() {
        try {
            //load dialog fxmln

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gameshop/view/AddGameDialog.fxml"));
            DialogPane dialogPane = loader.load();

            //  Dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Neues Spiel hinzufügen");

            ButtonType saveButtonType = new ButtonType("Speichern", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // 4. wait for click
            dialog.showAndWait().ifPresent(response -> {
                if (response == saveButtonType) {
                    // DATEN EINSAMMELN
                    TextField nameField = (TextField) dialogPane.lookup("#nameField");
                    TextField priceField = (TextField) dialogPane.lookup("#priceField");
                    TextField stockField = (TextField) dialogPane.lookup("stockField");

                    //call to service for db actions
                    gameService.saveNewGameToDB(nameField.getText(), priceField.getText(), stockField.getText());
                    
                    refreshTable();
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}