package com.mycompany.b.l.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Load the MySQL driver (optional but recommended)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Read database credentials from a configuration file (recommended)
            // ... (Implementation for reading from config file) ...

            String url = "jdbc:mysql://localhost:3306/megacitycab?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "Admin";

            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error connecting to the database", e);
            // Handle the exception appropriately (e.g., throw a custom exception)
        }
    }

    // Singleton implementation (remains the same)
    private static class SingletonHelper {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public Connection getConnection() {
        return connection; // Note: No try-with-resources here because the connection is managed by the constructor.
    }
}