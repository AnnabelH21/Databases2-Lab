// package com.gameshop;

// import java.sql.Connection;
// import java.sql.SQLException;

// public class App {
//     public static void main(String[] args) {
//         System.out.println("------- SQL Server Verbindungstest -------");
        
//         // Wir versuchen die Verbindung aufzubauen
//         try (Connection conn = DatabaseConnector.getConnection()) {
            
//             if (conn != null) {
//                 System.out.println("ERFOLG: Verbindung zum Server itnt0005 steht!");
//                 System.out.println("Datenbank-Info: " + conn.getMetaData().getDatabaseProductVersion());
//             }

//         } catch (SQLException e) {
//             System.err.println("FEHLER: Verbindung fehlgeschlagen!");
//             System.err.println("Meldung: " + e.getMessage());
            
//             // Das hier hilft uns extrem bei der Fehlersuche:
//             if (e.getMessage().contains("network name")) {
//                 System.err.println("TIPP: Prüfe dein VPN! Der Server itnt0005 ist nicht erreichbar.");
//             }
//         }
//     }
// }

package com.gameshop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        System.out.println("------- Game inventory -------");

        // Use Conenctor to get connection
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Test database connection
            String sql = "SELECT gameId, name, price, inventory FROM Heberle_games";
            
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-5s | %-30s | %-10s | %-10s%n", "ID", "Name", "Preis", "Bestand");
            System.out.println("-----------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("gameId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("inventory");

                // Formatted console output
                System.out.printf("%-5d | %-30s | %-10.2f | %-10d%n", id, name, price, stock);
            }

        } catch (SQLException e) {
            System.err.println("Error during database operation!");
            e.printStackTrace();
        }
    }
}