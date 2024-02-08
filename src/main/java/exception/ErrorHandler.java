package exception;

// Class to handle different types of exceptions
public class ErrorHandler extends Throwable {
    // Method to handle SQLException
    public static void handleSQLException(java.sql.SQLException e) {
        // Print error message for SQLException
        System.err.println("SQLException: " + e.getMessage());
    }

    // Method to handle generic exceptions
    public static void handleException(Exception e) {
        // Print error message for any other exception
        System.err.println("Exception: " + e.getMessage());
    }
}
