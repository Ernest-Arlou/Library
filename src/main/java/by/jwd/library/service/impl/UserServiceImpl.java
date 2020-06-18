package by.jwd.library.service.impl;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.userdao.UserDAO;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.UserService;
import by.jwd.library.service.ServiceException;


public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String login) throws ServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().getUserByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    @Override
    public User login(LoginInfo loginInfo) throws ServiceException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        User user = null;
        try {
            user = userDAO.getUserByLogin(loginInfo);
        } catch (DAOException e) {
            throw new ServiceException("Error in login", e);
        }
        return user;
    }

    @Override
    public boolean loginExists(User user) throws DAOException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        return userDAO.getUserByLogin(user.getLogin()) != null;
    }

    @Override
    public boolean emailExists(User user) throws DAOException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        return userDAO.getUserByEmail(user.getEmail()) != null;
    }

    @Override
    public boolean passportIdExists(User user) throws DAOException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        return userDAO.getUserByPassportId(user.getPassportId()) != null;
    }

    @Override
    public void register(User user) throws ServiceException {

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            synchronized (this) {
                if (loginExists(user) || emailExists(user)) {
                    throw new ServiceException("User exists");
                }
                userDAO.addUser(user);
            }
        } catch (DAOException e) {
            throw new ServiceException("Error in registration", e);
        }

    }
}
