package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.Booking;
import repositories.interfaces.IRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookingRepository implements IRepository<Booking> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value for connection.

    public BookingRepository(IDB databaseConnection) { this.databaseConnection = databaseConnection; }

    @Override
    public void createRecord(Booking booking) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO bookings(user_id, performance_id, seat_number) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, booking.getUser_id());
            statement.setInt(2, booking.getPerformance_id());
            statement.setString(3, booking.getSeat_number());

            statement.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
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
                return mapResultSet(resultSet);
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
            String query = "SELECT * FROM bookings";
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
        booking.setPerformance_id(resultSet.getInt("performance_id"));
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
