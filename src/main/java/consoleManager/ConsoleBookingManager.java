package consoleManager;

import controllers.BookingController;
import exception.ErrorHandler;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleBookingManager {
    private final Scanner scanner;
    private final BookingController bookingController;

    public ConsoleBookingManager(BookingController bookingController) {
        this.bookingController = bookingController;
        scanner = new Scanner(System.in);
    }

    // manageBookings() and other methods allows us to operate with performanceRepository.
    public void manageBookings() {
        int choice = -1;
        do {
            System.out.println();
            System.out.println("====================================================================" +
                    "\nManage bookings:");
            System.out.println("1. Create booking record;");
            System.out.println("2. Update booking record;");
            System.out.println("3. Delete booking records (or one record);");
            System.out.println("4. Get booking info by ID;");
            System.out.println("5. Get all bookings list;");
            System.out.println();
            System.out.println("0. Back");
            System.out.println();

            try {
                System.out.print("Select option >>> ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createBooking();
                        break;
                    case 2:
                        updateBooking();
                        break;
                    case 3:
                        deleteBooking();
                        break;
                    case 4:
                        getBookingById();
                        break;
                    case 5:
                        getAllBookings();
                        break;
                    case 0:
                        break;
                    default:
                        System.err.println("\nIncorrect choice. Try again.");
                }
            } catch (InputMismatchException e) {
                ErrorHandler.handleInputMismatchException(e);
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    private void createBooking() {
        try {
            System.out.println("Enter information about new booking.");
            System.out.print("User's ID: ");
            int user_id = scanner.nextInt();
            System.out.print("Performance's ID: ");
            int performance_id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Information about seat number: ");
            String seat_number = scanner.nextLine();

            String response = bookingController.createBooking(user_id, performance_id, seat_number);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private <T> void updateBooking() {
        try {
            System.out.println("Update information about booking.");
            System.out.print("Enter booking's ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter column name (user_id, performance_id, seat_number). " +
                    "P. S. seat_number have character varying data type: ");
            String columnName = scanner.nextLine();
            System.out.print("Enter a new value: ");
            String valueStr = scanner.nextLine();

            T value;

            columnName.toLowerCase(); // to make it case non-sensitive.
            if (columnName.equals("seat_number"))
                value = (T) valueStr;
            else
                value = (T) Integer.valueOf(valueStr);

            String response = bookingController.updateBooking(id, columnName, value);
            System.out.println(response);

        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } catch (Exception e) {
            ErrorHandler.handleException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void deleteBooking() {
        System.out.println("Delete booking records (or one record).");
        System.out.print("Enter bookings ID (or several admins ID separated by comma): ");

        String input = scanner.nextLine();
        bookingController.deleteBooking(input);
    }

    private void getBookingById() {
        try {
            System.out.println("Get information about booking by ID.");
            System.out.print("Enter booking's ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            String response = bookingController.getBookingById(id);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void getAllBookings() {
        System.out.println("\nAll performances in database: ");
        String response = bookingController.getAllBookings();
        if (response != null)
            System.out.println(response);
        else System.out.println("\nThere is no performance in database.");
    }
}