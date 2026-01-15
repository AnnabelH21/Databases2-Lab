package com.gameshop.orders;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gameshop.model.Order;
import com.gameshop.DatabaseConnector;


public class OrderRepository {

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * From Heberle_orders";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                orders.add(new Order(
                    rs.getInt("orderId"),
                    rs.getInt("gameId"),
                    rs.getString("gameNameAtOrder"),
                    rs.getString("status"),
                    rs.getTimestamp("orderDate").toLocalDateTime(),
                    rs.getInt("amount"),
                    rs.getTimestamp("deliveryDate") != null ? rs.getTimestamp("deliveryDate").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading orders: " + e.getMessage());
        }
        return orders;
    }
    
    public void updateOrderStatus(int orderId, String newStatus) {
        String query = "{call Heberle_sp_UpdateOrderStatus(?, ?)}";

        try (Connection conn = DatabaseConnector.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setInt(1, orderId);
            stmt.setString(2, newStatus);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating order status: " + e.getMessage());
        }
    }
}
