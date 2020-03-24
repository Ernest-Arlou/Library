package by.jwd.registration.dao;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.RegistrationInfo;
import by.jwd.registration.dao.connectionpool.ConnectionPoolException;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

import java.sql.*;

public class LibraryDAOImpl implements LibraryDAO {

    private boolean checkUserExistence (String login) throws ConnectionPoolException, SQLException{
        Connection con = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
        PreparedStatement pstmt = null;

        pstmt = con.prepareStatement("select * from users where username=?");
        pstmt.setString(1, login);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
            pstmt.close();
            con.close();
            return true;
        }
        else {
            pstmt.close();
            con.close();
            return false;
        }

    }

    @Override
    public String registerUser (RegistrationInfo register) throws DAOException{

        String firstname = register.getFirstname();
        String lastname = register.getLastname();
        String username = register.getUsername();
        String password = register.getPassword();

        try {
            if (checkUserExistence(username)){
                return "User with this login already exists";
            }
            Connection con = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            PreparedStatement pstmt = null;

            pstmt = con.prepareStatement("insert into users(firstname,lastname,username,password) values(?,?,?,?)");
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            return "You are registered";
        } catch (SQLException e) {
            throw new DAOException("SQL error",e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        }

    }

    @Override
    public String loginUser (LoginInfo login) throws DAOException{

        String username = login.getUsername();
        String password = login.getPassword();

        String dbusername = "";
        String dbpassword = "";

        try {

            Connection con = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            PreparedStatement pstmt = null;

            pstmt = con.prepareStatement("select * from users where username=? and password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dbusername = rs.getString("username");
                dbpassword = rs.getString("password");

                if (username.equals(dbusername) && password.equals(dbpassword)) {
                    return "SUCCESS LOGIN";
                }
            }

            pstmt.close();
            con.close();

        } catch (SQLException e) {
            throw new DAOException("SQL error",e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        }

        return "WRONG USERNAME OR PASSWORD";
    }
}
