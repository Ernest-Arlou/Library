package by.jwd.registration.dao;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.User;

public interface LibraryDAO {
    User getUserByEmail (String email) throws DAOException;

    User getUserByLogin (String login) throws DAOException;

    void registerUser (User user) throws DAOException;

    User loginUser (LoginInfo login) throws DAOException;
}
