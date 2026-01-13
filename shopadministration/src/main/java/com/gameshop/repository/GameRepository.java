package com.gameshop.repository;

import com.gameshop.DatabaseConnector;
import com.gameshop.model.Game;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    // get all games
    public List<Game> findAll() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT gameId, name, price, inventory FROM Heberle_games";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                games.add(new Game(
                    rs.getInt("gameId"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("inventory")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading games: " + e.getMessage());
        }
        return games;
    }

    // add a new game to the db
    public void addGame(String name, double price, int inventory) {
        String sql = "INSERT INTO Heberle_games (name, price, inventory) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, inventory);
            pstmt.executeUpdate();
            
            System.out.println("game '" + name + "' successfully added.");
        } catch (SQLException e) {
            System.err.println("Error while adding game: " + e.getMessage());
        }
    }

    //delete game
    public void deleteGame(int gameId) {
        String sql = "DELETE FROM Heberle_games WHERE gameId = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gameId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("game with ID " + gameId + " successfully deleted.");
            } else {
                System.out.println("No game found with ID " + gameId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting game: " + e.getMessage());
        }
    }

    //change price of a game
    public void updateGamePriceFixed(int gameId, double newPrice) {
        callUpdatePriceProcedure(gameId, newPrice, null);
    }

    public void updateGamePricePercent(int gameId, double percentChange) {
        callUpdatePriceProcedure(gameId, null, (int) percentChange);
    }

    private void callUpdatePriceProcedure(int gameId, Double fixedPriceChange, Integer percent) {
        String sql = "{call Heberle_sp_UpdateGamePrice(?, ?, ?)}";

        try (Connection conn = DatabaseConnector.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            
            // 1. Parameter: gameId (immer da)
            stmt.setInt(1, gameId);
            stmt.setObject(2, fixedPriceChange, java.sql.Types.DECIMAL);
            stmt.setObject(3, percent, java.sql.Types.INTEGER);
            stmt.execute();
            System.out.println("Successfully updated price for game ID " + gameId);

        } catch (SQLException e) {
            System.err.println("Error while updating game price:" + e.getMessage());
        }
    }

}