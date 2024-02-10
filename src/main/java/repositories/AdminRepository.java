package repositories;

import database.interfaces.IDB;
import exception.ErrorHandler;
import models.Admin;
import repositories.interfaces.IRepository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// Repository class for handling Admin objects in the database.
public class AdminRepository implements IRepository<Admin> {
    private final IDB databaseConnection;
    private Connection connection = null; // Default value of connection.

    public AdminRepository(IDB databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    // Create a new record for an admin in the database.
    @Override
    public boolean createRecord(Admin admin) {
        try {
            connection = databaseConnection.getConnection();
            String query = "INSERT INTO admins(login, password, name, surname, admin_level) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,admin.getLogin());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getName());
            statement.setString(4, admin.getSurname());
            statement.setInt(5, admin.getAdminLevel());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false;
    }

    // Update a record for an admin in the database.
    @Override
    public boolean updateRecord(int id, String columnName, Object value) {

        try {
            connection = databaseConnection.getConnection();
            String query = "UPDATE admins SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Depending on the type of value, we use the appropriate set() method.
            if (value instanceof String) {
                preparedStatement.setString(1, (String) value);
            } else if (value instanceof Integer) {
                preparedStatement.setInt(1, (Integer) value);
            }

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return false;
    }

    // Delete records for admins from the database.
    @Override
    public boolean deleteRecord(int... ids) {
        try {
            connection = databaseConnection.getConnection();
            String query = "DELETE FROM admins WHERE id IN ("; // Start of the query.
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

    // Get an admin by ID from the database.
    @Override
    public Admin getById(int id) {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM admins WHERE id = ?";
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

    // Get all admins from the database.
    @Override
    public List<Admin> getAll() {
        try {
            connection = databaseConnection.getConnection();
            String query = "SELECT * FROM admins";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            List<Admin> admins = new LinkedList<>();

            while (resultSet.next()) {
                Admin admin = mapResultSet(resultSet);

                admins.add(admin);
            }

            return admins;

        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } finally {
            getFinallyBlock(connection);
        }

        return null;
    }

    // Map a result set to an admin object.
    @Override
    public Admin mapResultSet(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getInt("id"));
        admin.setLogin(resultSet.getString("login"));
        admin.setPassword(resultSet.getString("password"));
        admin.setName(resultSet.getString("name"));
        admin.setSurname(resultSet.getString("surname"));

        return admin;
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
