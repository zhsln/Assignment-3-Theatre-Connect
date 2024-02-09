package exception;

// Class to handle different types of exceptions
public class ErrorHandler extends Throwable {
    public static void handleSQLException(java.sql.SQLException e) { // Method to handle SQLException
        System.err.println("SQLException: " + e.getMessage());
    }

    public static void handleException(Exception e) { // Method to handle generic exceptions
        System.err.println("Exception: " + e.getMessage());
    }
}
