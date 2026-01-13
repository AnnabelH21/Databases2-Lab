package com.gameshop.repository;

import com.gameshop.model.Sale;
import com.gameshop.DatabaseConnector;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;



public class SalesRepository {
    
    public List<Sale> getAllSales() {
        
        String query = "SELECT * FROM Heberle_sales";
        List<Sale> sales = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                sales.add(new Sale(
                    rs.getInt("saleId"),
                    rs.getInt("gameId"),
                    rs.getInt("amount"),
                    rs.getDouble("price"),
                    rs.getString("gameNameAtSale"),
                    rs.getTimestamp("saleDate").toLocalDateTime()    
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading sales: " + e.getMessage());
        }
        return sales;
    }

    public Double getYearlyRevenue(int year){

        String query = "SELECT db_owner.Heberle_fn_YearlyRevenue(?) AS yearlyRevenue;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("yearlyRevenue");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while calculating yearly revenue: " + e.getMessage());
        }
        return null;
    }

    public Double getMonthlyRevenue(int year, int month){

        String query = "SELECT db_owner.Heberle_fn_MonthlyRevenue(?, ?) AS monthlyRevenue;";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("monthlyRevenue");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while calculating monthly revenue: " + e.getMessage());
        }
        return null;
    }
}