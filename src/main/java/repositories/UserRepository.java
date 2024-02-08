package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.User;
import repositories.interfaces.IRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserRepository implements IRepository<User> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value of connection.

    public UserRepository(IDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void createRecord(User user) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO users(login, password, name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());

            statement.executeUpdate();

            System.out.println("User " + user.getName() + " created successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
    }

    @Override
    public User getById(int id) {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE id = ?";
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
    public List<User> getAll() {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<User> users = new LinkedList<>();

            while (resultSet.next()) {
                User user = mapResultSet(resultSet);

                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    @Override
    public User mapResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));

        return user;
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
