package by.jwd.library.dao.userdao;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.util.DAOUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private static final String ADD_USER =
            "insert into users(name,email,login,password,role) values(?,?,?,?,?)";
    private static final String GET_USER_BY_LOG_AND_PASS =
            "select * from users where login=? and password=?";
    private static final String GET_USER_BY_LOGIN =
            "select * from users where login=?";
    private static final String GET_USER_BY_EMAIL =
            "select * from users where email=?";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ADMIN = "admin";
    private static final String USER_ROLE = "role";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";

    @Override
    public User getUserByEmail(String email) throws DAOException {

        return getUserByParameter(email, GET_USER_BY_EMAIL);
    }

    @Override
    public User getUserByLogin(String login) throws DAOException {

        return getUserByParameter(login, GET_USER_BY_LOGIN);
    }


    @Override
    public void addUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, ROLE_USER);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }

    }

    private User getUserByParameter(String paramValue, String query) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, paramValue);
            return getUserFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    @Override
    public User getUserByLogin(LoginInfo loginInfo) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_LOG_AND_PASS);
            preparedStatement.setString(1, loginInfo.getUsername());
            preparedStatement.setString(2, loginInfo.getPassword());
            return getUserFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    private User getUserFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildUser(resultSet);
            } else {
                return null;
            }
        } finally {
            DAOUtil.closeResultSet(resultSet);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        boolean isAdmin;
        if (resultSet.getString(USER_ROLE).equals(ROLE_ADMIN)) {
            isAdmin = true;
        } else {
            isAdmin = false;
        }
        return new User(resultSet.getString(USER_NAME),
                resultSet.getString(USER_EMAIL),
                resultSet.getString(USER_LOGIN),
                resultSet.getString(USER_PASSWORD),
                isAdmin);
    }

}
