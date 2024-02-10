package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.User;
import repositories.interfaces.IRepository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// UserRepository class handles database operations related to users.
public class UserRepository implements IRepository<User> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value of connection.

    public UserRepository(IDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    // Create a new user record in the database.
    @Override
    public boolean createRecord(User user) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO users(login, password, name, surname) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false; // if creation went wrong.
    }

    // Update an existing user record in the database.
    @Override
    public boolean updateRecord(int id, String columnName, Object value) {

        try {
            connection = databaseConnection.getConnection();
            String query = "UPDATE users SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Depending on the type of value, we use the appropriate set() method.
            preparedStatement.setString(1, (String) value); /* All columns in users table
                                                                        are character varying type. */

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false; // if updating went wrong.
    }

    // Delete user records from the database.
    @Override
    public boolean deleteRecord(int... ids) {
        try {
            connection = databaseConnection.getConnection();
            String query = "DELETE FROM users WHERE id IN ("; // Start of the query.
            for (int i = 0; i < ids.length; i++) {
                query += i == 0 ? "?" : ", ?"; // amount of id gives us the same amount of '?'.
            }
            query += ")"; // End of the query.

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setInt(i + 1, ids[i]); // setting one or many id to prepared statement.
            }
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false;
    }

    // Retrieve a user record by ID from the database.
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

    // Retrieve all user records from the database.
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

    // Map a ResultSet to a User object.
    @Override
    public User mapResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));

        return user;
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
