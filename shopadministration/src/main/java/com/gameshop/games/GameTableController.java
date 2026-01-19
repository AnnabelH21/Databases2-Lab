package com.gameshop.games;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GameTableController {

    @FXML private TableView<Game> gameTable;
    @FXML private TableColumn<Game, Integer> colId;
    @FXML private TableColumn<Game, String> colName;
    @FXML private TableColumn<Game, Double> colPrice;
    @FXML private TableColumn<Game, Integer> colStock;
    @FXML private ComboBox<String> priceChangeTypePicker;
    @FXML private TextField fixedPriceField;
    @FXML private TextField salePriceField;
    @FXML private TextField gameIdField;
    @FXML private VBox salePriceBox;
    @FXML private VBox fixedPriceBox;

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
            //load dialog fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gameshop/view/AddGameDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Dialog/ return value decides close or save dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Neues Spiel hinzufügen");

            // get button from fxml
            ButtonType saveButtonType = dialogPane.getButtonTypes()
                    .stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .orElse(null);

            // wait for click
            dialog.showAndWait().ifPresent(response -> {
                if (response == saveButtonType) {
                    // get data from input fields
                    TextField nameField = (TextField) dialogPane.lookup("#nameField");
                    TextField priceField = (TextField) dialogPane.lookup("#priceField");
                    TextField stockField = (TextField) dialogPane.lookup("#stockField");

                    //call to service for db action
                    gameService.saveNewGameToDB(nameField.getText(), priceField.getText(), stockField.getText());

                    refreshTable();
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOpenDeleteGameDialog(){

        try{
            // loading dialog fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gameshop/view/ArchiveGameDialog.fxml"));
            DialogPane pane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Spiel archivieren");

            ButtonType saveButton = pane.getButtonTypes()
                    .stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .orElse(null);

            dialog.showAndWait().ifPresent(response -> {
                if (response == saveButton) {
                    // get data from input fields
                    TextField gameIdField = (TextField) pane.lookup("#gameIdField");

                    //call to service for db action
                    gameService.archiveGame(gameIdField.getText());

                    refreshTable();
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenAddPriceChangeDialog() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gameshop/view/DialogAddPriceChange.fxml"));
        loader.setController(this);
        try {
            DialogPane pane = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Preis ändern");

            priceChangeTypePicker.setItems(FXCollections.observableArrayList("Fester Preis", "Sale-Event"));

            // track combobox
            priceChangeTypePicker.valueProperty().addListener((obs, oldVal, newVal) -> {

                System.out.println("Auswahl geändert auf: " + newVal);

                if ("Fester Preis".equals(newVal)) {
                    fixedPriceBox.setVisible(true);
                    fixedPriceBox.setManaged(true);
                    salePriceBox.setVisible(false);
                    salePriceBox.setManaged(false);
                } else if ("Sale-Event".equals(newVal)) {
                    fixedPriceBox.setVisible(false);
                    fixedPriceBox.setManaged(false);
                    salePriceBox.setVisible(true);
                    salePriceBox.setManaged(true);
                }
                pane.getScene().getWindow().sizeToScene();
            });

            // get button from fxml
            ButtonType saveButton = pane.getButtonTypes()
                    .stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .orElse(null);

            //wait for click
            dialog.showAndWait().ifPresent(response -> {
                if (response == saveButton) {
                    if(("Fester Preis").equals(priceChangeTypePicker.getValue())){
                        gameService.changePrice(gameIdField.getText(), fixedPriceField.getText(), null);
                    }
                    if(("Sale-Event").equals(priceChangeTypePicker.getValue())) {
                        gameService.changePrice(gameIdField.getText(), null, salePriceField.getText());
                    }
                    refreshTable();
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}