package com.gameshop.model;

import java.time.LocalDateTime;

public class GameLog {
    private int logId;
    private Integer gameId;
    private String name;
    private Double lastPrice;
    private int inventoryaAtDeletion;
    private LocalDateTime deleteDate;

    public GameLog(int logId, Integer gameId, String name, Double lastPrice, int inventoryaAtDeletion, LocalDateTime deleteDate) {
        this.logId = logId;
        this.gameId = gameId;
        this.name = name;
        this.lastPrice = lastPrice;
        this.inventoryaAtDeletion = inventoryaAtDeletion;
        this.deleteDate = deleteDate;
    }

    public int getLogId() { return logId; }
    public Integer getGameId() { return gameId; }
    public String getName() { return name; }
    public Double getLastPrice() { return lastPrice; }
    public int getInventoryaAtDeletion() { return inventoryaAtDeletion; }
    public LocalDateTime getDeleteDate() { return deleteDate; }

}
