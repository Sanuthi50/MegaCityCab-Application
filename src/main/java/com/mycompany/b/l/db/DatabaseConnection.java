package com.mycompany.b.l.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        initializeConnection();
    }

    // Singleton implementation using static inner class
    private static class SingletonHelper {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Initialize the database connection
    private void initializeConnection() {
        try {
            // Load database credentials from a configuration file or environment variables
            String url = "jdbc:mysql://localhost:3306/megacitycab?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "Admin";

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Database connection established successfully.");
        } catch (SQLException e) {
            logger.error("Failed to establish database connection.", e);
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    // Get the database connection
    public Connection getConnection() {
        try {
            // Reinitialize the connection if it is closed or null
            if (connection == null || connection.isClosed()) {
                logger.info("Connection is closed or null. Reinitializing...");
                initializeConnection();
            }
        } catch (SQLException e) {
            logger.error("Failed to validate database connection.", e);
            throw new RuntimeException("Database connection is invalid", e);
        }
        return connection;
    }

    // Close the database connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed successfully.");
            } catch (SQLException e) {
                logger.error("Failed to close database connection.", e);
            }
        }
    }
}