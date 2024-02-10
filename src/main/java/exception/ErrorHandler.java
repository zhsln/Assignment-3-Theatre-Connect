package exception;

import java.text.ParseException;
import java.util.InputMismatchException;

// Class to handle different types of exceptions
public class ErrorHandler extends Throwable {
    public static void handleSQLException(java.sql.SQLException e) { // Method to handle SQLException
        System.err.println("\nSQLException: " + e.getMessage());
    }

    public static void handleException(Exception e) { // Method to handle generic exceptions
        System.err.println("\nException: " + e.getMessage());
    }

    public static void handleParseException(ParseException e) {  /* Method to handle exceptions,
                                                                 when parsing string to date or time */
        System.err.println("\nParseException: " + e.getMessage());
    }

    public static void handleInputMismatchException(InputMismatchException e) { //Method to handle incorrect input in menu.
        System.err.println("\nIncorrect input. Try again.");
    }
}
