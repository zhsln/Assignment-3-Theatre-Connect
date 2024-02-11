package consoleManager;

import controllers.PerformanceController;
import exception.ErrorHandler;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsolePerformanceManager {
    private final Scanner scanner;
    private final PerformanceController performanceController;

    public ConsolePerformanceManager(PerformanceController performanceController) {
        this.performanceController = performanceController;
        scanner = new Scanner(System.in);
    }

    // managePerformances() and other methods allows us to operate with performanceRepository.
    public void managePerformances() {
        int choice = -1;
        do {
            System.out.println();
            System.out.println("====================================================================" +
                    "\nManage performances:");
            System.out.println("1. Create performance record;");
            System.out.println("2. Update performance record;");
            System.out.println("3. Delete performance records (or one record);");
            System.out.println("4. Get performance info by ID;");
            System.out.println("5. Get all performances list;");
            System.out.println();
            System.out.println("0. Back");
            System.out.println();

            try {
                System.out.print("Select option >>> ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createPerformance();
                        break;
                    case 2:
                        updatePerformance();
                        break;
                    case 3:
                        deletePerformance();
                        break;
                    case 4:
                        getPerformanceById();
                        break;
                    case 5:
                        getAllPerformances();
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

    private void createPerformance() {
        try {
            System.out.println("Enter information about new performance.");
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Venue: ");
            String venue = scanner.nextLine();
            System.out.print("Date: ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString);
            System.out.print("Time (in format HH:MM:SS): ");
            String timeString = scanner.nextLine();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.util.Date parsedTime = timeFormat.parse(timeString);
            Time time = new Time(parsedTime.getTime());
            System.out.print("Duration (in minutes): ");
            int duration = scanner.nextInt();

            String response = performanceController.createPerformance(title, date, time, duration, venue);
            System.out.println(response);
        } catch (ParseException e) {
            ErrorHandler.handleParseException(e);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } catch (Exception e) {
            ErrorHandler.handleException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private <T> void updatePerformance() {
        try {
            System.out.println("Update information about performance.");
            System.out.print("Enter performance's ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter column name (title, date, time, duration, venue): ");
            String columnName = scanner.nextLine();
            System.out.print("Enter a new value: ");
            String valueStr = scanner.nextLine();

            T value;

            columnName.toLowerCase(); // to make it case non-sensitive.
            if (columnName.equals("date")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                value = (T) dateFormat.parse(valueStr);
            }
            else if (columnName.equals("time")) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                value = (T) timeFormat.parse(valueStr);
            }
            else if (columnName.equals("duration")) {
                value = (T) Integer.valueOf(valueStr);
            }
            else {
                value = (T) valueStr;
            }

            String response = performanceController.updatePerformance(id, columnName, value);
            System.out.println(response);

        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } catch (ParseException e) {
            ErrorHandler.handleParseException(e);
        } catch (Exception e) {
            ErrorHandler.handleException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void deletePerformance() {
        System.out.println("Delete admins records (or one record).");
        System.out.print("Enter admins ID (or several admins ID separated by comma): ");

        String input = scanner.nextLine();
        performanceController.deletePerformance(input);
    }

    private void getPerformanceById() {
        try {
            System.out.println("Get information about performance by ID.");
            System.out.print("Enter performance ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            String response = performanceController.getPerformanceById(id);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void getAllPerformances() {
        System.out.println("\nAll performances in database: ");
        String response = performanceController.getAllPerformances();
        if (response != null)
            System.out.println(response);
        else System.out.println("\nThere is no performance in database.");
    }
}