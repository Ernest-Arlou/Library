package by.jwd.registration.dao;

import by.jwd.registration.bean.RegisterBean;
import by.jwd.registration.dao.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterDao {


    public String authorizeRegister(RegisterBean registerBean) {

        String firstname = registerBean.getFirstname();
        String lastname = registerBean.getLastname();
        String username = registerBean.getUsername();
        String password = registerBean.getPassword();

        try {

            ConnectionPool connectionPool = new ConnectionPool();
            connectionPool.initPoolData();
            Connection con = connectionPool.takeConnection();
            PreparedStatement pstmt = null;

            pstmt = con.prepareStatement("insert into users(firstname,lastname,username,password) values(?,?,?,?)");
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            connectionPool.dispose();

            return "You are registered";
        } catch (Exception e) {
            //
        }
        return "Registration Failed";
    }
}
