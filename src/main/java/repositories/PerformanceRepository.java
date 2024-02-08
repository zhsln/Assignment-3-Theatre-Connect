package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.Performance;
import repositories.interfaces.IRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PerformanceRepository implements IRepository<Performance> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value of connection.

    public PerformanceRepository(IDB databaseConnection) { this.databaseConnection = databaseConnection; }
    @Override
    public void createRecord(Performance performance) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO performances(title, timestamp, duration, venue) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            // mapResultSet() wasn't used, because there we don't need a performance_id.
            statement.setString(1, performance.getTitle());
            statement.setTimestamp(2, Timestamp.valueOf(performance.getTimestamp()));
            statement.setInt(3, performance.getDuration());
            statement.setString(4, performance.getVenue());

            statement.executeUpdate();

            System.out.println("Performance " + performance.getTitle() + " created successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
    }

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

    @Override
    public Performance mapResultSet(ResultSet resultSet) throws SQLException {
        Performance performance = new Performance();
        performance.setId(resultSet.getInt("id"));
        performance.setTitle(resultSet.getString("title"));
        performance.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
        performance.setDuration(resultSet.getInt("duration"));
        performance.setVenue(resultSet.getString("venue"));

        return performance;
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
