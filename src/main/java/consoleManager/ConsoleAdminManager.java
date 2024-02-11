package consoleManager;

import controllers.AdminController;
import exception.ErrorHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleAdminManager {
    private final Scanner scanner;
    private final AdminController adminController;

    public ConsoleAdminManager(AdminController adminController) {
        this.adminController = adminController;
        this.scanner = new Scanner(System.in);
    }

    // manageAdmins() and other methods below allows us to operate with adminRepository.
    public void manageAdmins() {
        int adminChoice = -1; // if we use choice variable, then it will cause a problem with exiting whole program in case 0.
        do {
            System.out.println();
            System.out.println("====================================================================" +
                    "\nManage admins:");
            System.out.println("1. Create admin record;");
            System.out.println("2. Update admin record;");
            System.out.println("3. Delete admins records (or one record);");
            System.out.println("4. Get admin info by ID;");
            System.out.println("5. Get all admins list;");
            System.out.println();
            System.out.println("0. Back");
            System.out.println();

            try {
                System.out.print("Select option >>> ");
                adminChoice = scanner.nextInt();
                scanner.nextLine();

                switch (adminChoice) {
                    case 1:
                        createAdmin();
                        break;
                    case 2:
                        updateAdmin();
                        break;
                    case 3:
                        deleteAdmin();
                        break;
                    case 4:
                        getAdminById();
                        break;
                    case 5:
                        getAllAdmins();
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
        } while (adminChoice != 0);
    }

    private void createAdmin() { // method title speaks for itself...
        try {
            System.out.println("Enter information about new admin.");
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Surname: ");
            String surname = scanner.nextLine();
            System.out.print("Admin level (1 or 2): ");
            int adminLevel = scanner.nextInt();
            scanner.nextLine();

            String response = adminController.createAdmin(login, password, name, surname, adminLevel);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private <T> void updateAdmin() {
        try {
            System.out.println("Update information about admin.");
            System.out.print("Enter admin's ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter column name (login, password, name, surname, admin_level): ");
            String columnName = scanner.nextLine();
            System.out.print("Enter a new value: ");
            String valueStr = scanner.nextLine();

            T value;

            columnName.toLowerCase(); // to make it case non-sensitive.
            if (columnName.equals("admin_level")) {
                value = (T) Integer.valueOf(valueStr);
            }
            else {
                value = (T) valueStr;
            }

            String response = adminController.updateAdmin(id, columnName, value);
            System.out.println(response);

        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } catch (Exception e) {
            ErrorHandler.handleException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void deleteAdmin() {
        System.out.println("Delete admins records (or one record).");
        System.out.print("Enter admins ID (or several admins ID separated by comma): ");

        String input = scanner.nextLine();
        adminController.deleteAdmin(input);
    }

    private void getAdminById() {
        try {
            System.out.println("Get information about admin by ID.");
            System.out.print("Enter admin's ID: ");
            int adminId = scanner.nextInt();

            String response = adminController.getAdminById(adminId);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void getAllAdmins() {
        System.out.println("\nAll admins in database: ");
        String response = adminController.getAllAdmins();
        if (response != null)
            System.out.println(response);
        else System.out.println("\nThere is no admins in database.");
    }
}
