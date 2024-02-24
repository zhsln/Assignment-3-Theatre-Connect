package consoleManager;

import controllers.UserController;
import exception.ErrorHandler;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUserManager {
    private final Scanner scanner;
    private final UserController userController;

    public ConsoleUserManager(UserController userController) {
        this.userController = userController;
        scanner = new Scanner(System.in);
    }

    // manageUsers() and other methods below allows us to operate with AdminRepository.
    public void manageUsers() {
        int choice = -1; // if we use choice variable, then it will cause a problem with exiting whole program in case 0.
        do {
            System.out.println();
            System.out.println("====================================================================" +
                    "\nManage users:");
            System.out.println("1. Create user record;");
            System.out.println("2. Update user record;");
            System.out.println("3. Delete users records (or one record);");
            System.out.println("4. Get user info by ID;");
            System.out.println("5. Get all users list;");
            System.out.println();
            System.out.println("0. Back");
            System.out.println();

            try {
                System.out.print("Select option >>> ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        updateUser();
                        break;
                    case 3:
                        deleteUser();
                        break;
                    case 4:
                        getUserById();
                        break;
                    case 5:
                        getAllUsers();
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

    private void createUser() { // method title speaks for itself...
        System.out.println("Enter information about new user.");

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Surname: ");
        String surname = scanner.nextLine();

        // Entering is user editor or not.
        System.out.print("Is editor? (Yes or No ?): ");
        String isEditor = scanner.nextLine();
        Boolean editor = false; // Default value.
        if (isEditor.toLowerCase() == "yes" || isEditor == "1")
            editor = true;

        // Entering is user manager or not.
        System.out.print("Is manager? (Yes or No ?): ");
        String isManager = scanner.nextLine();
        Boolean manager = false; // Default value.
        if (isManager.toLowerCase() == "yes" || isManager == "1") {
            manager = true;
        }

        String response = userController.createUser(login, password, name, surname, editor, manager);
        System.out.println(response);
    }

    private void updateUser() {
        try {
            System.out.println("Update information about user.");
            System.out.print("Enter user's ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter column name (login, password, name, surname, editor, manager): ");
            String columnName = scanner.nextLine();

            System.out.print("Enter a new value: ");
            Object value;
            String valueStr = scanner.nextLine();
            if (columnName.equals("editor") || columnName.equals("manager")) {
                if (valueStr.toLowerCase().equals("true") || valueStr.equals("1")) {
                    value = true;
                } else if (valueStr.toLowerCase().equals("false") || valueStr.toLowerCase().equals("0")) {
                    value = false;
                } else
                    throw new InputMismatchException();
            } else
                value = valueStr;

            String response = userController.updateUser(userId, columnName, value);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } catch (Exception e) {
            ErrorHandler.handleException(e);
        } finally {
            System.out.println("Press Enter to continue.");
            scanner.nextLine();
        }
    }

    private void deleteUser() {
        System.out.println("Delete users records (or one record).");
        System.out.print("Enter user's ID (or several users ID separated by comma): ");
        String input = scanner.nextLine();
        userController.deleteUser(input);
    }

    private void getUserById() {
        try {
            System.out.println("Get information about user by ID.");
            System.out.print("Enter user's ID: ");
            int userId = scanner.nextInt();

            String response = userController.getUserById(userId);
            System.out.println(response);
        } catch (InputMismatchException e) {
            ErrorHandler.handleInputMismatchException(e);
        } finally {
            scanner.nextLine();
        }
    }

    private void getAllUsers() {
        System.out.println("\nAll users in database: ");
        String response = userController.getAllUsers();
        if (response != null)
            System.out.println(response);
        else System.out.println("\nThere is no users in database.");
    }
}
