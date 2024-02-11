package consoleManager;

import controllers.AdminController;
import controllers.BookingController;
import controllers.PerformanceController;
import controllers.UserController;
import exception.ErrorHandler;

import java.util.InputMismatchException;
import java.util.Scanner;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter

public class ConsoleMainManager {
    private final UserController userController;
    private final ConsoleUserManager consoleUserManager;

    private final AdminController adminController;
    private final ConsoleAdminManager consoleAdminManager;

    private final PerformanceController performanceController;
    private final ConsolePerformanceManager consolePerformanceManager;

    private final BookingController bookingController;
    private final ConsoleBookingManager consoleBookingManager;

    public ConsoleMainManager(UserController userController, AdminController adminController,
                              PerformanceController performanceController, BookingController bookingController) {
        this.userController = userController;
        this.consoleUserManager = new ConsoleUserManager(userController);

        this.adminController = adminController;
        this.consoleAdminManager = new ConsoleAdminManager(adminController);

        this.performanceController = performanceController;
        this.consolePerformanceManager = new ConsolePerformanceManager(performanceController);

        this.bookingController = bookingController;
        this.consoleBookingManager = new ConsoleBookingManager(bookingController);
    }

    public void startMainMenu() {
        int choice = -1;
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
                        ConsoleUserManager userManager = new ConsoleUserManager(userController);
                        userManager.manageUsers();
                        break;
                    case 2:
                        ConsolePerformanceManager performanceManager = new ConsolePerformanceManager(performanceController);
                        performanceManager.managePerformances();
                        break;
                    case 3:
                        ConsoleBookingManager bookingManager = new ConsoleBookingManager(bookingController);
                        bookingManager.manageBookings();
                        break;
                    case 4:
                        ConsoleAdminManager adminManager = new ConsoleAdminManager(adminController);
                        adminManager.manageAdmins();
                        break;
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
}

