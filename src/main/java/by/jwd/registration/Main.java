package by.jwd.registration;

import by.jwd.registration.bean.Register;
import by.jwd.registration.dao.RegisterDao;

public class Main {
    public static void main (String[] args){
        Register register = new Register();
        register.setFirstname("0001");
        register.setLastname("0010");
        register.setPassword("0010");
        register.setUsername("0010");

        RegisterDao registerDao = new RegisterDao();
        registerDao.authorizeRegister(register);

        System.out.println("AAAAAAAAAAAAAAA");
    }
}
