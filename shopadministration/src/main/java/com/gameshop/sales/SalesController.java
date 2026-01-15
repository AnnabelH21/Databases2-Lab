package com.gameshop.sales;

import com.gameshop.model.Sale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

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
        if (selectedYear == null) return; // Sicherheitsscheck

        // 2. Den gewählten Index des Monats bestimmen
        int monthIndex = monthPicker.getSelectionModel().getSelectedIndex();


        SalesResult salesResult = salesService.getRevenueForSelection(selectedYear,monthIndex, monthPicker.getValue());
        infoLabel.setText(salesResult.infoText);

//        if (monthIndex == 0) {
//            // FALL A: "Gesamtes Jahr" ist ausgewählt (Index 0)
//             = salesRepo.getYearlyRevenue(selectedYear);
//            infoLabel.setText("Gesamtumsatz Jahr " + selectedYear);
//        }
//        else {
//            // FALL B: Ein spezifischer Monat ist ausgewählt (Index 1-12)
//            // Der monthIndex entspricht hier direkt der SQL-Zahl (Jan=1, Feb=2...)
//             = salesRepo.getMonthlyRevenue(selectedYear, monthIndex);
//
//            String selectedMonthName = monthPicker.getValue();
//            infoLabel.setText("Umsatz " + selectedMonthName + " " + selectedYear);
//        }

        // 3. Ergebnis anzeigen (Null-Check falls DB leer)
//        if ( != null) {
//            revenueLabel.setText(String.format("%.2f €", ));
//        } else {
//            revenueLabel.setText("0,00 €");
//        }

    }
}
