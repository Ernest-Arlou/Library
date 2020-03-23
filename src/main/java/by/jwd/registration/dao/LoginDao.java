package by.jwd.registration.dao;

import by.jwd.registration.bean.Login;
import by.jwd.registration.dao.connectionpool.ConnectionPool;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {


    public String authorizeLogin(Login login) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "WRONG USERNAME OR PASSWORD";
    }
}
