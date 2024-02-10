import controllers.UserController;
import database.interfaces.IDB;
import exception.ErrorHandler;
import models.User;
import repositories.AdminRepository;
import repositories.BookingRepository;
import repositories.PerformanceRepository;
import repositories.UserRepository;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter

public class ConsoleManager {
    private final UserController userController;
    //    private final UserRepository userRepository;
//    private final AdminRepository adminRepository;
//    private final PerformanceRepository performanceRepository;
//    private final BookingRepository bookingRepository;
    private final Scanner scanner;
    private int choice = -1;

    public ConsoleManager(UserController userController) {
        this.userController = userController;
        scanner = new Scanner(System.in);
    }

    // Arguments constructor (without int choice).
//    public ConsoleManager(UserRepository userRepository,
//                          AdminRepository adminRepository,
//                          PerformanceRepository performanceRepository,
//                          BookingRepository bookingRepository)
//    {
//        // setters from lombok doesn't work here...
//        this.userRepository = userRepository;
//        this.adminRepository = adminRepository;
//        this.performanceRepository = performanceRepository;
//        this.bookingRepository = bookingRepository;
//        scanner = new Scanner(System.in);
//    }

    public void startMainMenu() {
        System.out.println();
        System.out.println(
                        "████████╗██╗  ██╗███████╗ █████╗ ████████╗██████╗ ███████╗     ██████╗ ██████╗ ███╗   ██╗███╗   ██╗███████╗ ██████╗████████╗\n" +
                        "╚══██╔══╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔══██╗██╔════╝    ██╔════╝██╔═══██╗████╗  ██║████╗  ██║██╔════╝██╔════╝╚══██╔══╝\n" +
                        "   ██║   ███████║█████╗  ███████║   ██║   ██████╔╝█████╗█████╗██║     ██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██║        ██║   \n" +
                        "   ██║   ██╔══██║██╔══╝  ██╔══██║   ██║   ██╔══██╗██╔══╝╚════╝██║     ██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██║        ██║   \n" +
                        "   ██║   ██║  ██║███████╗██║  ██║   ██║   ██║  ██║███████╗    ╚██████╗╚██████╔╝██║ ╚████║██║ ╚████║███████╗╚██████╗   ██║   \n" +
                        "   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝ ╚═════╝   ╚═╝   \n");
        // We used https://www.fancytextpro.com/BigTextGenerator to create this text.
        do {
            System.out.println();
            System.out.println();
            System.out.println("====================================================================" +
                    "\nMAIN MENU:");
            System.out.println("1. Manage users");
            System.out.println("2. Manage performances");
            System.out.println("3. Manage bookings");
            System.out.println("4. Manage admins");
            System.out.println();
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Select option >>> ");

            Scanner scanner = new Scanner(System.in);
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        manageUsers();
                        break;
                    case 2:
                        //managePerformances();
                        break;
                    case 3:
                        //manageBookings();
                        break;
                    case 4:
                        //manageAdmins();
                    case 0:
                        System.out.println("\nExiting the programme.");
                        break;
                    default:
                        System.err.println("Incorrect choice. Try again."); // err.println() to change font colour to red.
                }
            } catch (InputMismatchException e) {
                ErrorHandler.handleInputMismatchException(e);
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    // manageUsers() and other methods below allows us to operate with userRepository.
    private void manageUsers() {
        int userChoice = -1; // if we use choice variable, then it will cause a problem with exiting whole program in case 0.
        do {
            System.out.println();
            System.out.println("Manage users:");
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
                userChoice = scanner.nextInt();
                scanner.nextLine();

                switch (userChoice) {
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
                        getUser();
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
            }
        } while (userChoice != 0);
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

        String response = userController.createUser(login, password, name, surname);
        System.out.println(response);
    }

    private void updateUser() {
        System.out.println("Update information about user.");
        System.out.print("Enter user's ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter column name (login, password, name, surname): ");
        String columnName = scanner.nextLine(); // Every column in users table are character varying type.
        System.out.print("Enter a new value: ");
        String value = scanner.nextLine(); // Every column in users table are character varying type.

        String response = userController.updateUser(userId, columnName, value);
        System.out.println(response);
    }

    private void deleteUser() {
        System.out.println("Delete users records (or one record).");
        System.out.print("Enter user's ID (or several users ID separated by comma): ");

        String input = scanner.nextLine();
        userController.deleteUser(input);
    }

    private void getUser() {
        System.out.println("Get information about user by ID.");
        System.out.print("Enter user's ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        String response = userController.getUserById(userId);
        System.out.println(response);
    }

    private void getAllUsers() {
        System.out.println("\nAll users in database: ");
        String response = userController.getAllUsers();
        if (response != null)
            System.out.println(response);
        else System.out.println("\nThere is no users in database.");
    }
}
