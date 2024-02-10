package models;
import database.DatabaseConnection;
import database.interfaces.IDB;
import exception.ErrorHandler;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import repositories.PerformanceRepository;
import repositories.UserRepository;

import java.sql.Time;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter

public class Booking {
    // name of fields are the same as in the table in Database.
    private int booking_id;
    private int user_id;
    private String user_name;
    private String user_surname;
    private int performance_id;
    private String performance_title;
    private String performance_venue;
    private LocalDate performance_date;
    private Time performance_time;
    private String seat_number; /*Seat number is String,
                                because in theatre your seat can be in the balcony, for example,
                                and it should be noted by words in your ticket. And why it is called a number?
                                Because seat can have information about rows and your seat number in a row.
                                Example: Parterre: Row 13, Seat 10 */

    // Arguments constructor.
    public Booking(int booking_id, int user_id, int performance_id, String seat_number) {
        setBooking_id(booking_id);
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

            UserRepository userRepository = new UserRepository(db);
            User user = userRepository.getById(user_id);

            if (performance != null && user != null)
                return "\n==============================" +
                        "\n| Booking with ID: " + booking_id +
                        "\n| User's full name: " + user.getName() + " " + user.getSurname() +
                        "\n| Performance ID: " + performance_id +
                        "\n| Performance title: " + performance.getTitle() +
                        "\n| Venue: " + performance.getVenue() +
                        "\n| Date and Time: " + performance.getDate() + " " + performance.getTime() +
                        "\n| Seat: " + seat_number +
                        "\n==============================";

        } catch (Exception e) {
            ErrorHandler.handleException(e);
        }

        return null;
    }
}
