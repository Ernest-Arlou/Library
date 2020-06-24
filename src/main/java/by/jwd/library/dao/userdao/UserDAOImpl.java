package by.jwd.library.dao.userdao;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.MediaDisplay;
import by.jwd.library.bean.MediaPage;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.util.DAOUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    // TODO: 22.06.2020 refactor
    private static final String ADD_USER =
            "insert into users(name,email,login,password,`passport-id`,role, status) values(?,?,?,?,?,?,?)";
    private static final String GET_USER_BY_LOG_AND_PASS =
            "select * from users where login=? and password=?";
    private static final String GET_USER_BY_LOGIN =
            "select * from users where login=? and status !='deleted'";
    private static final String GET_USER_BY_ID =
            "select * from users where `user-id`=? and status !='deleted'";
    private static final String GET_USER_BY_EMAIL =
            "select * from users where email=? and status !='deleted'";
    private static final String GET_USER_BY_PASSPORT_ID =
            "select * from users where `passport-id`=? and status !='deleted'";
    private static final String GET_UNVERIFIED_USERS = "select * from users where status = 'Unverified'";
    private static final String UPDATE_USER = "update users set login = ?,password = ?,role = ?,status = ?," +
            "name = ?, email = ?,`passport-id` = ? where `user-id` = ?;";
    private static final String SEARCH_UNVERIFIED_USERS = "select * from users where status = 'Unverified' and login like ? or status = 'Unverified' and email like ? or status = 'Unverified' and `passport-id` like ?;";
    private static final String ROLE_USER = "user";
    private static final String ROLE_ADMIN = "admin";
    private static final String USER_ROLE = "role";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PASSPORT_ID = "passport-id";
    private static final String USER_STATUS = "status";
    private static final String USER_USER_ID = "user-id";

    @Override
    public User getUserById(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, userId);
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
    public User getUserByEmail(String email) throws DAOException {

        return getUserByStringParameter(email, GET_USER_BY_EMAIL);
    }

    @Override
    public User getUserByLogin(String login) throws DAOException {

        return getUserByStringParameter(login, GET_USER_BY_LOGIN);
    }

    @Override
    public User getUserByPassportId(String passportId) throws DAOException {

        return getUserByStringParameter(passportId, GET_USER_BY_PASSPORT_ID);
    }

    @Override
    public List<User> searchUnverifiedUsers(String searchStr) throws DAOException {
        Connection connection = null;

        PreparedStatement searchPrepStatement = null;
        ResultSet usersSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            searchPrepStatement = connection.prepareStatement(SEARCH_UNVERIFIED_USERS);
            searchPrepStatement.setString(1, "%" + searchStr + "%");
            searchPrepStatement.setString(2, "%" + searchStr + "%");
            searchPrepStatement.setString(3, "%" + searchStr + "%");

            usersSet = searchPrepStatement.executeQuery();

            List<User> userList = new ArrayList<>();
            while (usersSet.next()) {
                userList.add(buildUser(usersSet));
            }

            return userList;

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(usersSet);
            DAOUtil.closePreparedStatement(searchPrepStatement);
            DAOUtil.closeConnection(connection);
        }

    }

    @Override
    public List<User> getUnverifiedUsers() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(GET_UNVERIFIED_USERS);
            resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(resultSet);
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getStatus());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassportId());
            preparedStatement.setInt(8,user.getUserId());
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
            preparedStatement.setString(5, user.getPassportId());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setString(7, user.getStatus());
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

    private User getUserByStringParameter(String paramValue, String query) throws DAOException {
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
        return new User(resultSet.getInt(USER_USER_ID),
                resultSet.getString(USER_NAME),
                resultSet.getString(USER_EMAIL),
                resultSet.getString(USER_LOGIN),
                resultSet.getString(USER_PASSWORD),
                resultSet.getString(USER_PASSPORT_ID),
                resultSet.getString(USER_ROLE),
                resultSet.getString(USER_STATUS));
    }

}
