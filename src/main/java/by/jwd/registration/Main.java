package by.jwd.registration;

import by.jwd.registration.bean.RegisterBean;
import by.jwd.registration.dao.RegisterDao;

public class Main {
    public static void main (String[] args){
        RegisterBean registerBean = new RegisterBean();
        registerBean.setFirstname("0001");
        registerBean.setLastname("0010");
        registerBean.setPassword("0010");
        registerBean.setUsername("0010");

        RegisterDao registerDao = new RegisterDao();
        registerDao.authorizeRegister(registerBean);

        System.out.println("AAAAAAAAAAAAAAA");
    }
}
