package by.jwd.library.dao.userdao;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;

public interface UserDAO {
    User getUserByEmail (String email) throws DAOException;

    User getUserByLogin (String login) throws DAOException;

    User getUserByPassportId(String passportId) throws DAOException;

    void addUser(User user) throws DAOException;

    User getUserByLogin(LoginInfo login) throws DAOException;
}
