package by.jwd.registration;

import by.jwd.registration.bean.User;
import by.jwd.registration.dao.DAOException;
import by.jwd.registration.dao.connectionpool.ConnectionPoolException;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Main {
    private static final String REGISTER_USER =
            "insert into users(name,email,login,password) values(?,?,?,?)";
    private static final String GET_USER_BY_LOG_AND_PASS =
            "select * from users join `users-have-roles` on users.`user-id` = `Users-have-Roles`.`user-id` where login=? and password=?";
    private static final String GET_USER_BY_LOG =
            "select * from users join `users-have-roles` on users.`user-id` = `Users-have-Roles`.`user-id` where login=?";
    private static final String GET_USER_BY_EMAIL =
            "select * from users join `users-have-roles` on users.`user-id` = `Users-have-Roles`.`user-id` where email=?";
    public static void main (String[] args) throws DAOException{

        ConnectionPoolManager.getInstance().initConnectionPool();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = GET_USER_BY_EMAIL;
        String value = "vvvvvv@gmail.com";
//        String value = "email0@gmail.com";
//        String value = "Login1";


        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            resultSet = preparedStatement.executeQuery();
            User user =  createUser(resultSet);
            System.out.println(user);


        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);


        }





        ConnectionPoolManager.getInstance().disposeConnectionPull();

    }

    private static User createUser (ResultSet resultSet) throws SQLException{
        if (resultSet.next()) {
            boolean isAdmin;
            if (resultSet.getString("role-id").equals("1")) {
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
}
