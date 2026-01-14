package com.gameshop.controller;

import com.gameshop.model.Game;
import com.gameshop.model.Order;
import com.gameshop.repository.OrderRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

public class OrderController {

    private OrderRepository orderRepo = new OrderRepository();

    @FXML
    private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer > colId;
    @FXML private TableColumn<Order, Integer> colGameId;
    @FXML private TableColumn<Order, String> colName;
    @FXML private TableColumn<Order, String> colStatus;
    @FXML private TableColumn<Order, Integer> colAmount;
    @FXML private TableColumn<Order, LocalDateTime> colOrderDate;
    @FXML private TableColumn<Order, LocalDateTime> colDeliveryDate;

    @FXML private ComboBox<String> statusComboBox;
    @FXML private Label infoLabel;
    @FXML
    private Button btnUpdateStatus;

    @FXML
    public void initialize() {

        //Dropdown
        statusComboBox.setItems(FXCollections.observableArrayList("Ordered", "Arrived"));

        // Define which attributes from model belong in which column
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colGameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("gameNameAtOrder"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));

        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                boolean isArrived = newSelection.getStatus().equalsIgnoreCase("Arrived");
                // Status update not possible for already arrived orders
                statusComboBox.setDisable(isArrived);
                btnUpdateStatus.setDisable(isArrived);

                if(isArrived) {
                    infoLabel.setText("Abgeschlossene Bestellung können nicht bearbeitet werden!");
                } else {
                    infoLabel.setText("");
                }
            }
        });

        // Load Data
        refreshTable();
    }

    public void refreshTable() {

        orderTable.setItems(FXCollections.observableArrayList(orderRepo.getAllOrders()));
    }


    @FXML
    private void handleStatusUpdate() {
        // Order clicked
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        // Status change chosen
        String newStatus = statusComboBox.getValue();

        // Check criteria
        if (selectedOrder == null) {
            infoLabel.setText("Fehler: Bitte wähle erst eine Bestellung in der Tabelle aus!");
            return;
        }
        if (newStatus == null) {
            infoLabel.setText("Fehler: Bitte wähle einen Status (Ordered/Arrived) aus!");
            return;
        }

        try {
            //Call backend function that handles SP
            orderRepo.updateOrderStatus(selectedOrder.getOrderId(), newStatus);
            refreshTable();
            infoLabel.setText("Status für Bestellung #" + selectedOrder.getOrderId() + " auf '" + newStatus + "' aktualisiert!");
            infoLabel.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            infoLabel.setText("Fehler beim Datenbank-Update!");
            infoLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
}
