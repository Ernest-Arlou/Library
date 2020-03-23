package by.jwd.registration.service.impl;

import by.jwd.registration.bean.Login;
import by.jwd.registration.dao.LoginDao;
import by.jwd.registration.service.LibraryService;

public class LibraryServiceImpl implements LibraryService {
    @Override
   public String login (String username, String password){
        LoginDao loginDao = new LoginDao();
        Login login = new Login(username, password);

        return loginDao.authorizeLogin(login);
    }
}
