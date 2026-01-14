package com.gameshop.controller;

import com.gameshop.model.Sale;
import com.gameshop.repository.SalesRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class SalesController {

    private SalesRepository salesRepo= new SalesRepository();

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

    @FXML
    public void initialize(){
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
        LocalDate selectedDate = salesDatePicker.getValue();

//        // 1. Validierung: Hat der User ein Datum gewählt?
//        if (selectedDate == null) {
//            revenueLabel.setText("--- €");
//            periodLabel.setText("BITTE DATUM WÄHLEN");
//            return;
//        }

        int year = selectedDate.getYear();
        double result = 0;

        // 2. Weiche: Je nach Auswahl die passende Funktion aufrufen
        if (rbYear.isSelected()) {
            // Ruft deine existierende Funktion für das Jahr auf
            result = salesRepo.getYearlyRevenue(year);

            infoLabel.setText("GESAMTUMSATZ FÜR DAS JAHR " + year);

        }
        else {
            // Ruft deine existierende Funktion für Jahr + Monat auf
            int month = selectedDate.getMonthValue();
            result = salesRepo.getMonthlyRevenue(year, month);

            String monthName = selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);
            infoLabel.setText("UMSATZ FÜR " + monthName.toUpperCase() + " " + year);

            // Optional: Auch die Tabelle auf den Monat filtern
//            updateTable(year, month);
        }

        // 3. Anzeige formatieren
        revenueLabel.setText(String.format("%,.2f €", result));
    }
}
