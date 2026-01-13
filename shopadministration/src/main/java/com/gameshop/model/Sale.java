package com.gameshop.model;

import java.time.LocalDateTime;

public class Sale {
    private int saleId;
    private Integer gameId; // Nullable Foreign Key
    private int amount;
    private Double price;
    private LocalDateTime salesDate;
    private String gameName;

    public Sale(int saleId, Integer gameId, int amount, Double price, String gameName, LocalDateTime salesDate) {
        this.saleId = saleId;
        this.gameId = gameId;
        this.amount = amount;
        this.price = price;
        this.salesDate = salesDate;
        this.gameName = gameName;
    }

    // Getter
    public int getSaleId() { return saleId; }
    public Integer getGameId() { return gameId; }
    public int getAmount() { return amount; }
    public Double getPrice() { return price; }
    public LocalDateTime getSalesDate() { return salesDate; }
    public String getGameName() { return gameName; }
}