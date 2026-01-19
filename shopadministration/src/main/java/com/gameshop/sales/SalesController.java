package com.gameshop.sales;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SalesController {

    private SalesRepository salesRepo= new SalesRepository();
    private SalesService salesService = new SalesService();

    @FXML private DatePicker salesDatePicker;
    @FXML private RadioButton rbYear;
    @FXML private RadioButton rbMonth;
    @FXML private Label infoLabel;
    @FXML private Label revenueLabel;


    @FXML private TableView<Sale> salesTable;
    @FXML private TableColumn<Sale, Integer> colId;
    @FXML private TableColumn<Sale, Integer> colGameId;
    @FXML private TableColumn<Sale, String>  colGameName;
    @FXML private TableColumn<Sale, Integer> colAmount;
    @FXML private TableColumn<Sale, Double> colPrice;
    @FXML private TableColumn<Sale, LocalDateTime> colSalesDate;
    @FXML private ComboBox<String> monthPicker;
    @FXML private ComboBox<Integer> yearPicker;
    private Integer currentYear = LocalDate.now().getYear();
    private ArrayList<Integer> years = new ArrayList<Integer>();

    @FXML
    public void initialize(){

        monthPicker.setItems(FXCollections.observableArrayList("Kein Monat (Ganzes Jahr)","Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"));

        for (Integer year = 1990; year<= currentYear; year++) {
            years.add(year);
        }

        yearPicker.setItems(FXCollections.observableArrayList(years));

        colId.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        colGameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        colGameName.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSalesDate.setCellValueFactory(new PropertyValueFactory<>("salesDate"));

        refreshTable();
    }

    public void refreshTable() {

        salesTable.setItems(FXCollections.observableArrayList(salesRepo.getAllSales()));
    }

    @FXML
    private void handleCalculateSales() {

        Double result;
        Integer selectedYear = yearPicker.getValue();
        if (selectedYear == null) return;

        // Define month through index position
        int monthIndex = monthPicker.getSelectionModel().getSelectedIndex();

        SalesResult salesResult = salesService.getRevenueForSelection(selectedYear,monthIndex, monthPicker.getValue());
        infoLabel.setText(salesResult.infoText);
    }

    @FXML
    private void handleAddSale(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gameshop/view/DialogAddtoSales.fxml"));

        try{
            DialogPane dialogPane = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Verkauf eintragen");

            // get button from fxml
            ButtonType saveButton = dialogPane.getButtonTypes()
                    .stream()
                    .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                    .findFirst()
                    .orElse(null);

            //wait for click
            dialog.showAndWait().ifPresent(response -> {
                if (response == saveButton) {
                    // get data from input fields
                    TextField gameIdField = (TextField) dialogPane.lookup("#gameIdField");
                    TextField gameNameField = (TextField) dialogPane.lookup("#gameNameField");
                    TextField amountField = (TextField) dialogPane.lookup("#amountField");
                    salesService.addSaleEntry(gameIdField.getText(),gameNameField.getText(), amountField.getText());

                    refreshTable();
                }
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
