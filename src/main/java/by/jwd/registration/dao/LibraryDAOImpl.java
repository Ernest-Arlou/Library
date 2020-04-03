package by.jwd.registration.dao;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.User;
import by.jwd.registration.dao.connectionpool.ConnectionPoolException;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryDAOImpl implements LibraryDAO {
    private static final String REGISTER_USER =
            "insert into users(name,email,login,password,role) values(?,?,?,?,?)";
    private static final String GET_USER_BY_LOG_AND_PASS =
            "select * from users where login=? and password=?";
    private static final String GET_USER_BY_LOG =
            "select * from users where login=?";
    private static final String GET_USER_BY_EMAIL =
            "select * from users where email=?";

    @Override
    public User getUserByEmail (String email) throws DAOException{

        return getUser(email, GET_USER_BY_EMAIL);
    }

    @Override
    public User getUserByLogin (String login) throws DAOException{

        return getUser(login, GET_USER_BY_LOG);
    }

    private User getUser (String value, String query) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            resultSet = preparedStatement.executeQuery();
            return createUser(resultSet);

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);

        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //log
            }
        }
    }



    private User createUser (ResultSet resultSet) throws SQLException{
        if (resultSet.next()) {
            boolean isAdmin;
            if (resultSet.getString("role").equals("admin")) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }
            return new User(resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    isAdmin);
        } else {
            return null;
        }
    }


    @Override
    public void registerUser (User user) throws DAOException{

        String name = user.getName();
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(REGISTER_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, "user");
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //log
            }
        }

    }

    @Override
    public User loginUser (LoginInfo loginInfo) throws DAOException{

        String login = loginInfo.getUsername();
        String password = loginInfo.getPassword();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_LOG_AND_PASS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            return createUser(resultSet);

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);

        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //log
            }
        }
    }
}
