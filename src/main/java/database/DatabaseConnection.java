package database;

import database.interfaces.IDB;
import exception.ErrorHandler;

import java.sql.*;

public class DatabaseConnection implements IDB {
    // Method to establish a connection to the PostgreSQL database.
    @Override
    public Connection getConnection() {
        String connectionUrl = "jdbc:postgresql://localhost:5432/theatre-connect";
        Connection connection = null;
        try {
            // Load the PostgreSQL JDBC driver.
            Class.forName("org.postgresql.Driver");
            // Establish the connection using the connection URL, username, and password.

            connection = DriverManager.getConnection(connectionUrl, "postgres", "0000");

            return connection;

        } catch (Exception e) {
            // Handle any exceptions that occur during the connection process.
            ErrorHandler.handleException(e);

            // Return null if connection fails.
            return null;
        }
    }
}