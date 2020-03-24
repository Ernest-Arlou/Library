package by.jwd.registration.dao;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.RegistrationInfo;

public interface LibraryDAO {
    String registerUser (RegistrationInfo register) throws DAOException;

    String loginUser(LoginInfo login) throws DAOException;
}
