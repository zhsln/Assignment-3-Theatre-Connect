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
            String query = "INSERT INTO users(login, password, name, surname) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());

            statement.executeUpdate();

            System.out.println("User " + user.getName() + " " + user.getSurname() + " created successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
    }

    @Override
    public void updateRecord(int id, String columnName, Object value) {

        try {
            connection = databaseConnection.getConnection();
            String query = "UPDATE users SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Depending on the type of value, we use the appropriate set() method.
            if (value instanceof String) {
                preparedStatement.setString(1, (String) value);
            } else if (value instanceof Integer) {
                preparedStatement.setInt(1, (Integer) value);
            }

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("User's information with id " + id + " in " + columnName + " updated successfully.");

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }
    }

    @Override
    public void deleteRecord(int... ids) {
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

            if (ids.length > 1) System.out.println("Records deleted successfully"); // records... if many id.
            else System.out.println("Record deleted successfully."); // record... if one id.

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
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));

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
