package by.jwd.library.dao.util;

import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUtilImpl implements DAOUtil {

    private static final String GET_USER_BY_ID =
            "select * from users where `user-id`=? and status !='deleted'";

    private static final String USER_ROLE = "role";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PASSPORT_ID = "passport-id";
    private static final String USER_STATUS = "status";
    private static final String USER_USER_ID = "user-id";


    private User getUserByIdwCon(Connection connection, int userId) throws DAOException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, userId);
            return getUserFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public User getUserById(Connection connection, int userId) throws DAOException {
        return getUserByIdwCon(connection, userId);
    }

    @Override
    public User getUserById(int userId) throws DAOException {
        Connection connection = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            return getUserById(connection, userId);

        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public User getUserFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = null;
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return buildUser(resultSet);
        } else {
            return null;
        }
    }

    @Override
    public User buildUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt(USER_USER_ID),
                resultSet.getString(USER_NAME),
                resultSet.getString(USER_EMAIL),
                resultSet.getString(USER_LOGIN),
                resultSet.getString(USER_PASSWORD),
                resultSet.getString(USER_PASSPORT_ID),
                resultSet.getString(USER_ROLE),
                resultSet.getString(USER_STATUS));
    }


    @Override
    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    @Override
    public void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    @Override
    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            //log
        }
    }
}
