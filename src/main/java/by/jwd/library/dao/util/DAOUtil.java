package by.jwd.library.dao.util;

import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAOUtil {

    User getUserById(Connection connection, int userId) throws DAOException;

    User getUserById(int userId) throws DAOException;

    User getUserFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException;

    User buildUser(ResultSet resultSet) throws SQLException;

//    void closeConnection(Connection connection);
//
//    void closePreparedStatement(PreparedStatement preparedStatement);
//
//    void closeResultSet(ResultSet resultSet);
}
