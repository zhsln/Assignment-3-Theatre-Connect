package models;
import database.DatabaseConnection;
import database.interfaces.IDB;
import exception.ErrorHandler;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import repositories.PerformanceRepository;
import repositories.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor
@Getter
@Setter

public class Booking {
    private int booking_id;
    private int user_id;
    private int performance_id;
    private String seat_number; /*Seat number is String,
                                because it theatre your seat can be in the balcony, for example,
                                and it should be noted by words in your ticket.*/
    private IDB databaseConnection;

    public Booking(IDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    // Arguments constructor without booking_id.
    public Booking(int user_id, int performance_id, String seat_number) {
        setUser_id(user_id);
        setPerformance_id(performance_id);
        setSeat_number(seat_number);
    }

    @Override
    public String toString() {
        try {
            IDB db = new DatabaseConnection();
            PerformanceRepository performanceRepository = new PerformanceRepository(db);
            Performance performance = performanceRepository.getById(performance_id);

            // Загрузка информации о пользователе
            UserRepository userRepository = new UserRepository(db);
            User user = userRepository.getById(user_id);

            if (performance != null && user != null) {
                return "Booking: " +
                        "\nID >>> " + booking_id +
                        ", \nUser >>> " + user.getName() +
                        ", \nPerformance >>> " + performance.getTitle() +
                        ", \nVenue >>> " + performance.getVenue() +
                        ", \nDate and Time >>> " + performance.getTimestamp() +
                        ", \nSeat Number >>> " + seat_number;
            } else {
                return "Booking: " +
                        "\nID >>> " + booking_id +
                        ", \nUser ID >>> " + user_id +
                        ", \nPerformance ID >>> " + performance_id +
                        ", \nSeat Number >>> " + seat_number;
            }
        } catch (Exception e) {
            ErrorHandler.handleException(e);
        }

        return null;
    }
}
