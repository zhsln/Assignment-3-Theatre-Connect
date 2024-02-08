package database.interfaces;

import java.sql.Connection;

// Interface defining methods for obtaining a database connection
public interface IDB {
    // Method signature for obtaining a connection to the database
    Connection getConnection();
}