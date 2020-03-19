package by.jwd.registration.dao;

import by.jwd.registration.bean.LoginBean;
import by.jwd.registration.dao.connectionpool.ConnectionPool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {


    public String authorizeLogin(LoginBean loginBean) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        String dbusername = "";
        String dbpassword = "";

        try {
            ConnectionPool connectionPool = new ConnectionPool();
            connectionPool.initPoolData();
            Connection con = connectionPool.takeConnection();

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
            connectionPool.dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "WRONG USERNAME OR PASSWORD";
    }
}
