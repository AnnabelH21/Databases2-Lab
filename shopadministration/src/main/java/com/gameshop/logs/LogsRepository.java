package com.gameshop.logs;

import com.gameshop.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogsRepository {
    
    public List<PriceLog> getAllPriceLogs() {
        List<PriceLog> priceLogs = new ArrayList<>();
        String query = "SELECT * FROM Heberle_log_prices";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                priceLogs.add(new PriceLog(
                    rs.getInt("logId"),
                    rs.getInt("gameId"),
                    rs.getDouble("oldPrice"),
                    rs.getDouble("newPrice"),
                    rs.getTimestamp("change_Date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading price logs: " + e.getMessage());
        }
        return priceLogs;
    }

    public List<GameLog> getAllGameLogs() {
        List<GameLog> gameLogs = new ArrayList<>();
        String query = "SELECT * FROM Heberle_log_games";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                gameLogs.add(new GameLog(
                    rs.getInt("logId"),
                    rs.getInt("gameId"),
                    rs.getString("name"),
                    rs.getDouble("lastPrice"),
                    rs.getInt("inventoryAtDelete"),
                    rs.getTimestamp("deleteDate").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading game logs: " + e.getMessage());
        }
        return gameLogs;
    }

}
