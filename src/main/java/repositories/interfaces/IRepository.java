package repositories.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IRepository <T> {
    void createRecord(T entity);
    T getById(int id);
    List<T> getAll();
    T mapResultSet(ResultSet resultSet) throws SQLException;
    void getFinallyBlock(Connection connection);
}
