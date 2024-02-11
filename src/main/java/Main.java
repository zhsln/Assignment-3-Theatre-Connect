import consoleManager.ConsoleMainManager;
import controllers.AdminController;
import controllers.BookingController;
import controllers.PerformanceController;
import controllers.UserController;
import database.DatabaseConnection;
import database.interfaces.IDB;
import repositories.AdminRepository;
import repositories.BookingRepository;
import repositories.PerformanceRepository;
import repositories.UserRepository;

// Main class for running the application
public class Main {
    public static void main(String[] args)  {
        // Declaring database connection interface to connect with database.
        IDB db = new DatabaseConnection();

        // Declaring each repositories to work with each of them by controllers.
        UserRepository userRepository = new UserRepository(db);
        AdminRepository adminRepository = new AdminRepository(db);
        PerformanceRepository performanceRepository = new PerformanceRepository(db);
        BookingRepository bookingRepository = new BookingRepository(db);

        // Declaring controllers to work with repositories.
        UserController userController = new UserController(userRepository);
        AdminController adminController = new AdminController(adminRepository);
        PerformanceController performanceController = new PerformanceController(performanceRepository);
        BookingController bookingController = new BookingController(bookingRepository);

        // Declaring main console manager.
        ConsoleMainManager consoleMainManager = new ConsoleMainManager(userController, adminController,
                                                                        performanceController, bookingController);

        consoleMainManager.startMainMenu();
    }
}