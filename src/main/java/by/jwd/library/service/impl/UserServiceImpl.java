package by.jwd.library.service.impl;

import by.jwd.library.bean.LoginInfo;
import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.dao.UserDAO;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;

import java.util.List;


public class UserServiceImpl implements UserService {
    private static final String USER_STATUS_ACTIVE = "Active";

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().getUserByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    @Override
    public User getUserById(int userId) throws ServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().getUserById(userId);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().getUserByEmail(email);
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
    public boolean loginExists(String login) throws ServiceException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.getUserByLogin(login) != null;
        } catch (DAOException e) {
            throw new ServiceException("Error in login check", e);
        }
    }

    @Override
    public boolean emailExists(String email) throws ServiceException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.getUserByEmail(email) != null;
        } catch (DAOException e) {
            throw new ServiceException("Error in email check", e);
        }
    }

    @Override
    public boolean passportIdExists(String passportId) throws ServiceException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.getUserByPassportId(passportId) != null;
        } catch (DAOException e) {
            throw new ServiceException("Error in passport id check", e);
        }
    }

    @Override
    public List<User> getUnverifiedUsers() throws ServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().getUnverifiedUsers();
        } catch (DAOException e) {
            throw new ServiceException("Error in unverified users obtainment", e);
        }
    }

    @Override
    public void editUser(User user) throws ServiceException {
        try {
            DAOFactory.getInstance().getUserDAO().updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Error in user update", e);
        }
    }

    @Override
    public void register(User user) throws ServiceException {

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            synchronized (this) {
                if (loginExists(user.getLogin()) || emailExists(user.getEmail()) || passportIdExists(user.getPassportId())) {
                    throw new ServiceException("User exists");
                }
                userDAO.addUser(user);
            }
        } catch (DAOException e) {
            throw new ServiceException("Error in registration", e);
        }

    }

    //    public void editUserPassportId (int userId, String passportId){
//        try {
//            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
//            user
//
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void changePassportId(int userId, String passportId) throws ServiceException {
        try {
            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
            user.setPassportId(passportId);
            DAOFactory.getInstance().getUserDAO().updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Error in user update", e);
        }
    }

    @Override
    public List<User> searchUnverifiedUsers(String searchStr) throws ServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().searchUnverifiedUsers(searchStr);
        } catch (DAOException e) {
            throw new ServiceException("Error in search", e);
        }
    }

    @Override
    public void verifyUser(int userId) throws ServiceException {
        try {
            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
            user.setStatus(USER_STATUS_ACTIVE);
            DAOFactory.getInstance().getUserDAO().updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Error in user verification", e);
        }
    }
}
