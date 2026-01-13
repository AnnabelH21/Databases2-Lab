package com.gameshop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    
    private static final Properties props = new Properties();

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        File file = new File("config.properties");
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            
        } catch (IOException e) {
            System.err.println("config.properties not found" + System.getProperty("user.dir"));
            e.printStackTrace();
        }
    }
 
    public static Connection getConnection() throws SQLException {
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL driver couldnt load", e);
        }
    }
}
