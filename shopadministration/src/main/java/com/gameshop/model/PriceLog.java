package com.gameshop.model;

import java.time.LocalDateTime;

public class PriceLog {
    private int logId;
    private Integer gameId;
    private Double oldPrice;
    private Double newPrice;
    private LocalDateTime changeDate;

    public PriceLog(int logId, Integer gameId, Double oldPrice, Double newPrice, LocalDateTime changeDate) {
        this.logId = logId;
        this.gameId = gameId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.changeDate = changeDate;
    }

    public Double getOldPrice() { return oldPrice; }
    public Double getNewPrice() { return newPrice; }
    public LocalDateTime getChangeDate() { return changeDate; }
    public Integer getGameId() { return gameId; }
    public int getLogId() { return logId; }
}