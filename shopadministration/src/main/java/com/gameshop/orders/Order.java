package com.gameshop.orders;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private Integer gameId;
    private String gameNameAtOrder;
    private String status;
    private LocalDateTime orderDate;
    private int amount;
    private LocalDateTime deliveryDate;

    public Order(int orderId, int gameId, String gameNameAtOrder, String status, LocalDateTime orderDate,  int amount, LocalDateTime deliveryDate) {
        this.orderId = orderId;
        this.gameId = gameId;
        this.gameNameAtOrder = gameNameAtOrder;
        this.status = status;
        this.orderDate = orderDate;
        this.amount = amount;
        this.deliveryDate = deliveryDate;
    }

    public int getOrderId() { return orderId; }
    public int getGameId() { return gameId; }
    public String getGameNameAtOrder() { return gameNameAtOrder; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public int getAmount() { return amount; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }

    public void setStatus(String status) { this.status = status; }
}