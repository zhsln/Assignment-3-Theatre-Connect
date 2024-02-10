package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.Performance;
import repositories.interfaces.IRepository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// PerformanceRepository class handles operations related to Performance objects in the database.
public class PerformanceRepository implements IRepository<Performance> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value of connection.

    public PerformanceRepository(IDB databaseConnection) { this.databaseConnection = databaseConnection; }

    // Creates a new record for a performance in the database.
    @Override
    public boolean createRecord(Performance performance) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO performances(title, date, time, duration, venue) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            // mapResultSet() wasn't used, because there we don't need a performance_id.
            statement.setString(1, performance.getTitle());
            statement.setDate(2, Date.valueOf(performance.getDate()));
            statement.setTime(3,performance.getTime());
            statement.setInt(4, performance.getDuration());
            statement.setString(5, performance.getVenue());

            statement.executeUpdate();

            System.out.println("Performance " + performance.getTitle() +
                    " (" + performance.getDate() + " " + performance.getTime() + ") created successfully.");
            /* Example,
                Performance Swan Lake (10-02-2024 00:27:48) created successfully.
            */

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
        return false;
    }

    // Updates a record for a performance in the database.
    @Override
    public boolean updateRecord(int id, String columnName, Object value) {

        try {
            connection = databaseConnection.getConnection();
            String query = "UPDATE performances SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Depending on the type of value, we use the appropriate set() method.
            if (value instanceof String) {
                preparedStatement.setString(1, (String) value);
            } else if (value instanceof Integer) {
                preparedStatement.setInt(1, (Integer) value);
            } else if (value instanceof Date) {
                preparedStatement.setDate(1, (Date) value);
            } else if (value instanceof Time) {
                preparedStatement.setTime(1, (Time) value);
            }

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("Information about performance with id " + id + " in " + columnName + " updated successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
        return false;
    }

    // Deletes records for performances from the database.
    @Override
    public boolean deleteRecord(int... ids) {
        try {
            connection = databaseConnection.getConnection();
            String query = "DELETE FROM performances WHERE id IN ("; // Start of the query.
            for (int i = 0; i < ids.length; i++) {
                query += i == 0 ? "?" : ", ?"; // amount of id gives us the same amount of '?'.
            }
            query += ")"; // End of the query.

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setInt(i + 1, ids[i]); // setting one or many id to prepared statement.
            }
            preparedStatement.executeUpdate();

            if (ids.length > 1) System.out.println("Records deleted successfully"); // records... if many id
            else System.out.println("Record deleted successfully."); // record... if one id.

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
        return false;
    }

    // Retrieves a performance by its ID from the database.
    @Override
    public Performance getById(int id) {
        Performance performance = null;

        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM performances WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

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

    // Retrieves all performances from the database.
    @Override
    public List<Performance> getAll() {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM performances";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<Performance> performances = new LinkedList<>();

            while (resultSet.next()) {
                Performance performance = mapResultSet(resultSet);

                performances.add(performance);
            }

            return performances;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    // Maps a ResultSet object to a Performance object.
    @Override
    public Performance mapResultSet(ResultSet resultSet) throws SQLException {
        Performance performance = new Performance();
        performance.setId(resultSet.getInt("id"));
        performance.setTitle(resultSet.getString("title"));
        performance.setDate(resultSet.getDate("date").toLocalDate());
        performance.setTime(resultSet.getTime("time"));
        performance.setDuration(resultSet.getInt("duration"));
        performance.setVenue(resultSet.getString("venue"));

        return performance;
    }

    // Closes the database connection.
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
