package exception;

import java.text.ParseException;
import java.util.InputMismatchException;

// Class to handle different types of exceptions
public class ErrorHandler extends Throwable {
    public static void handleSQLException(java.sql.SQLException e) { // Method to handle SQLException
        System.err.println("SQLException: " + e.getMessage());
    }

    public static void handleException(Exception e) { // Method to handle generic exceptions
        System.err.println("Exception: " + e.getMessage());
    }

    public static void handleParseException(ParseException e) {  /* Method to handle exceptions,
                                                                 when parsing string to date or time */
        System.err.println("ParseException: " + e.getMessage());
    }

    public static void handleInputMismatchException(InputMismatchException e) { //Method to handle incorrect input in menu.
        System.err.println("Incorrect input: " + e.getMessage());
    }
}
