package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.Booking;
import repositories.interfaces.IRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class BookingRepository implements IRepository<Booking> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value for connection.

    public BookingRepository(IDB databaseConnection) { this.databaseConnection = databaseConnection; }

    @Override
    public boolean createRecord(Booking booking) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO bookings(user_id, performance_id, seat_number) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, booking.getUser_id());
            statement.setInt(2, booking.getPerformance_id());
            statement.setString(3, booking.getSeat_number());

            statement.executeUpdate();

            System.out.println("Booking with id " + booking.getUser_id() + " created successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
        return false;
    }

    @Override
    public boolean updateRecord(int id, String columnName, Object value) {

        try {
            connection = databaseConnection.getConnection();
            String query = "UPDATE bookings SET " + columnName + " = ? WHERE booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Depending on the type of value, we use the appropriate set() method.
            if (value instanceof String) {
                preparedStatement.setString(1, (String) value);
            } else if (value instanceof Integer) {
                preparedStatement.setInt(1, (Integer) value);
            } else if (value instanceof LocalDate) {
                preparedStatement.setDate(1, Date.valueOf((LocalDate) value));
            }

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("Booking information with id " + id + " in " + columnName + " updated successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
        return false;
    }

    @Override
    public boolean deleteRecord(int... bookingIds) {
        try {
            connection = databaseConnection.getConnection();
            String query = "DELETE FROM bookings WHERE booking_id IN ("; // Start of the query.
            for (int i = 0; i < bookingIds.length; i++) {
                query += i == 0 ? "?" : ", ?"; // amount of id gives us the same amount of '?'.
            }
            query += ")"; // End of the query.

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < bookingIds.length; i++) {
                preparedStatement.setInt(i + 1, bookingIds[i]); // setting one or many id to prepared statement.
            }
            preparedStatement.executeUpdate();

            if (bookingIds.length > 1) System.out.println("Records deleted successfully"); // records... if many id.
            else System.out.println("Record deleted successfully."); // record... if one id.

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
        return false;
    }


    @Override
    public Booking getById(int booking_id) {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM bookings WHERE booking_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, booking_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("booking_id");
                int userId = resultSet.getInt("user_id");
                int performanceId = resultSet.getInt("performance_id");
                String seatNumber = resultSet.getString("seat_number");
                Booking booking = new Booking(id, userId, performanceId, seatNumber);

                return booking;
            }

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    @Override
    public List<Booking> getAll() {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT " +
                    "b.booking_id, " +
                    "u.id AS user_id, " +
                    "u.name AS user_name, " +
                    "u.surname AS user_surname, " +
                    "p.id AS performance_id, " +
                    "p.title AS performance_title, " +
                    "p.venue AS performance_venue, " +
                    "p.date AS performance_date, " +
                    "p.time AS performance_time, " +
                    "b.seat_number " +
                    "FROM " +
                    "bookings b " +
                    "JOIN " +
                    "users u ON b.user_id = u.id " +
                    "JOIN " +
                    "performances p ON b.performance_id = p.id;";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<Booking> bookings = new LinkedList<>();

            while (resultSet.next()) {
                Booking booking = mapResultSet(resultSet);

                bookings.add(booking);
            }

            return bookings;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    @Override
    public Booking mapResultSet(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setBooking_id(resultSet.getInt("booking_id"));
        booking.setUser_id(resultSet.getInt("user_id"));
        booking.setUser_name(resultSet.getString("user_name"));
        booking.setUser_surname(resultSet.getString("user_surname"));
        booking.setPerformance_id(resultSet.getInt("performance_id"));
        booking.setPerformance_title(resultSet.getString("performance_title"));
        booking.setPerformance_venue(resultSet.getString("performance_venue"));
        booking.setPerformance_date(resultSet.getDate("performance_date").toLocalDate());
        booking.setPerformance_time(resultSet.getTime("performance_time"));
        booking.setSeat_number(resultSet.getString("seat_number"));

        return booking;
    }

    @Override
    public void getFinallyBlock(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        }
    }
}