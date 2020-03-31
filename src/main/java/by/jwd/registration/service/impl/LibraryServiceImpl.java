package by.jwd.registration.service.impl;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.bean.User;
import by.jwd.registration.dao.DAOException;
import by.jwd.registration.dao.LibraryDAO;
import by.jwd.registration.dao.factory.DAOFactory;
import by.jwd.registration.service.LibraryService;
import by.jwd.registration.service.ServiceException;


public class LibraryServiceImpl implements LibraryService {

    @Override
    public User getUser (String login) throws ServiceException{
        try {
            return DAOFactory.getInstance().getLibraryDAO().getUserByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    @Override
    public String login (LoginInfo loginInfo) throws ServiceException{
        LibraryDAO libraryDAO = DAOFactory.getInstance().getLibraryDAO();
        User user = null;
        try {
            user = libraryDAO.loginUser(loginInfo);
        } catch (DAOException e) {
            throw new ServiceException("Error in login", e);
        }
        if (user == null) {
            return "Wrong login or password";
        } else {
            return "SUCCESS LOGIN";
        }
    }

    @Override
    public String register (User user) throws ServiceException{
        LibraryDAO libraryDAO = DAOFactory.getInstance().getLibraryDAO();

        try {
            if (libraryDAO.getUserByLogin(user.getLogin()) != null) {
                return "User with this login already exists";
            }
            if (libraryDAO.getUserByEmail(user.getEmail()) != null) {
                return "User with this email already exists";
            }

            libraryDAO.registerUser(user);
            return "You are registered";

        } catch (DAOException e) {
            throw new ServiceException("Error in registration", e);
        }
    }
}
