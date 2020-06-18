package by.jwd.library.service;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;

public interface UserService {
    User getUser (String login) throws ServiceException;

    User login (LoginInfo loginInfo) throws ServiceException;

    boolean loginExists(User user) throws DAOException;

    boolean emailExists(User user) throws DAOException;

    boolean passportIdExists(User user) throws DAOException;

    void register (User user) throws ServiceException;
}
