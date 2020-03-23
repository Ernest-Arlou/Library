package by.jwd.registration.dao;

import by.jwd.registration.bean.Register;
import by.jwd.registration.dao.connectionpool.ConnectionPool;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterDao {


    public String authorizeRegister(Register register) {

        String firstname = register.getFirstname();
        String lastname = register.getLastname();
        String username = register.getUsername();
        String password = register.getPassword();

        try {

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
        } catch (Exception e) {
            //
        }
        return "Registration Failed";
    }
}
