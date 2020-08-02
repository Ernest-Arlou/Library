package by.jwd.library.dao.impl;

import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.UserDAO;
import by.jwd.library.dao.connectionpool.ConnectionPool;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.factory.ConnectionPoolFactory;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.dao.util.DAOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO implements UserDAO {

    private final ConnectionPoolFactory connectionPoolFactory = ConnectionPoolFactory.getInstance();
    private final ConnectionPool connectionPool = connectionPoolFactory.getConnectionPool();

    private static final String ADD_USER =
            "insert into users(name,email,login,password,`passport-id`,role, status) values(?,?,?,?,?,?,?)";
    private static final String GET_USER_BY_LOG_AND_PASS =
            "select * from users where login=? and password=?";
    private static final String GET_USER_BY_LOGIN =
            "select * from users where login=? and status !='deleted'";

    private static final String GET_USER_BY_EMAIL =
            "select * from users where email=? and status !='deleted'";
    private static final String GET_USER_BY_PASSPORT_ID =
            "select * from users where `passport-id`=? and status !='deleted'";
    private static final String GET_UNVERIFIED_USERS = "select * from users where status = 'Unverified'";
    private static final String UPDATE_USER = "update users set login = ?,password = ?,role = ?,status = ?," +
            "name = ?, email = ?,`passport-id` = ? where `user-id` = ?;";
    private static final String SEARCH_UNVERIFIED_USERS = "select * from users where status = 'Unverified' and login like ? or status = 'Unverified' and email like ? or status = 'Unverified' and `passport-id` like ?;";


    private static final Logger logger = LoggerFactory.getLogger(MySQLUserDAO.class);

    @Override
    public User getUserById(int userId) throws DAOException {
        return DAOFactory.getInstance().getDaoUtil().getUserById(userId);
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

        DAOUtil daoUtil = DAOFactory.getInstance().getDaoUtil();

        try {
            logger.debug("searchUnverifiedUsers searchStr = {}", searchStr);
            connection = connectionPool.takeConnection();

            searchPrepStatement = connection.prepareStatement(SEARCH_UNVERIFIED_USERS);
            searchPrepStatement.setString(1, "%" + searchStr + "%");
            searchPrepStatement.setString(2, "%" + searchStr + "%");
            searchPrepStatement.setString(3, "%" + searchStr + "%");

            usersSet = searchPrepStatement.executeQuery();

            List<User> userList = new ArrayList<>();
            while (usersSet.next()) {
                userList.add(daoUtil.buildUser(usersSet));
            }

            logger.debug("searchUnverifiedUsers userList = {}", userList);
            return userList;

        } catch (SQLException e) {
            logger.error("SQLException in UserDAOImpl method searchUnverifiedUsers()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in UserDAOImpl method searchUnverifiedUsers()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, searchPrepStatement, usersSet);
        }

    }

    @Override
    public List<User> getUnverifiedUsers() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DAOUtil daoUtil = DAOFactory.getInstance().getDaoUtil();
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_UNVERIFIED_USERS);
            resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(daoUtil.buildUser(resultSet));
            }

            logger.debug("getUnverifiedUsers users = {}", users);
            return users;
        } catch (SQLException e) {
            logger.error("SQLException in UserDAOImpl method getUnverifiedUsers()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in UserDAOImpl method getUnverifiedUsers()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            logger.debug("updateUser user = {}", user);
            connection = connectionPool.takeConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getStatus());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassportId());
            preparedStatement.setInt(8, user.getUserId());
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                logger.debug("updateUser trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("updateUser rollback failed");
                throw new DAOException("Impossible to rollback method updateUser", e);
            }
            logger.error("SQLException in UserDAOImpl method updateUser()", e);
            throw new DAOException("SQLException in method updateUser", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in UserDAOImpl method updateUser()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void addUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            logger.debug("addUser user = {}", user);
            connection = connectionPool.takeConnection();

            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(ADD_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPassportId());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setString(7, user.getStatus());
            preparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                logger.debug("addUser trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("addUser rollback failed");
                throw new DAOException("Impossible to rollback method addUser", e);
            }
            logger.error("SQLException in UserDAOImpl method addUser()", e);
            throw new DAOException("SQLException in method addUser", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in UserDAOImpl method addUser()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }

    }

    private User getUserByStringParameter(String paramValue, String query) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        DAOUtil daoUtil = DAOFactory.getInstance().getDaoUtil();
        try {
            logger.debug("getUserByStringParameter paramValue = {}; query = {} ", paramValue, query);

            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, paramValue);
            return daoUtil.getUserFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            logger.error("SQLException in UserDAOImpl method getUserByStringParameter()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in UserDAOImpl method getUserByStringParameter()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

}
