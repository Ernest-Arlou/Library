package by.jwd.library.service.impl;

import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.UserDAO;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;
import by.jwd.library.service.util.BCrypt;
import by.jwd.library.service.validation.UserValidator;
import by.jwd.library.service.validation.factory.ValidationFactory;
import by.jwd.library.util.UserRole;
import by.jwd.library.util.UserStatus;

import java.util.List;
import java.util.Set;


public class UserServiceImpl implements UserService {

    @Override
    public synchronized boolean changePassword(int userId, String oldPass, String newPass) throws ServiceException {

        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateUserId(userId) ||
                !userValidator.validatePassword(oldPass) ||
                !userValidator.validatePassword(newPass)) {
            throw new ServiceException("Invalid parameters");
        }

        User user = getUserById(userId);

        if (passIsSame(user, oldPass)) {
            user.setPassword(BCrypt.hash(newPass));
            editUser(user);
            return true;
        }

        return false;

    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateLogin(login)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getUserDAO().getUserByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    @Override
    public User getUserById(int userId) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateUserId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getUserDAO().getUserById(userId);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateEmail(email)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getUserDAO().getUserByEmail(email);
        } catch (DAOException e) {
            throw new ServiceException("DAO error", e);
        }
    }

    private boolean passIsSame(User user, String password) {
        if (user == null) {
            return false;
        }

        if (BCrypt.verifyHash(password, user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateLogin(login) ||
                !userValidator.validatePassword(password)) {
            throw new ServiceException("Invalid parameters");
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            User user = userDAO.getUserByLogin(login);
            if (passIsSame(user, password)) {
                return user;
            } else {
                return null;
            }

        } catch (DAOException e) {
            throw new ServiceException("Error in login", e);
        }
    }

    @Override
    public boolean loginExists(String login) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateLogin(login)) {
            throw new ServiceException("Invalid parameters");
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.getUserByLogin(login) != null;
        } catch (DAOException e) {
            throw new ServiceException("Error in login check", e);
        }
    }

    @Override
    public boolean emailExists(String email) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateEmail(email)) {
            throw new ServiceException("Invalid parameters");
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.getUserByEmail(email) != null;
        } catch (DAOException e) {
            throw new ServiceException("Error in email check", e);
        }
    }

    @Override
    public boolean passportIdExists(String passportId) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validatePassportId(passportId)) {
            throw new ServiceException("Invalid parameters");
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            return userDAO.getUserByPassportId(passportId) != null;
        } catch (DAOException e) {
            throw new ServiceException("Error in passport id check", e);
        }
    }

    @Override
    public boolean checkUnverified(int userId) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateUserId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        User user = getUserById(userId);
        return user.getStatus().equalsIgnoreCase(UserStatus.UNVERIFIED);
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
    public synchronized void editUser(User user) throws ServiceException {
        Set<String> validate = ValidationFactory.getInstance().getUserValidator().validate(user);
        if (!validate.isEmpty()) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            DAOFactory.getInstance().getUserDAO().updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Error in user update", e);
        }
    }

    @Override
    public synchronized void register(User user) throws ServiceException {
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.UNVERIFIED);

        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        Set<String> validate = userValidator.validate(user);
        if (!validate.isEmpty() || !userValidator.validatePassword(user.getPassword())) {
            throw new ServiceException("Invalid parameters");
        }

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            if (loginExists(user.getLogin()) || emailExists(user.getEmail()) || passportIdExists(user.getPassportId())) {
                throw new ServiceException("User exists");
            }
            user.setPassword(BCrypt.hash(user.getPassword()));
            userDAO.addUser(user);

        } catch (DAOException e) {
            throw new ServiceException("Error in registration", e);
        }

    }

    @Override
    public synchronized void changePassportId(int userId, String passportId) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();

        if (!userValidator.validateUserId(userId) ||
                !userValidator.validatePassportId(passportId)) {
            throw new ServiceException("Invalid parameters");
        }

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
    public synchronized void verifyUser(int userId) throws ServiceException {
        UserValidator userValidator = ValidationFactory.getInstance().getUserValidator();
        if (!userValidator.validateUserId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            User user = DAOFactory.getInstance().getUserDAO().getUserById(userId);
            user.setStatus(UserStatus.ACTIVE);
            DAOFactory.getInstance().getUserDAO().updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Error in user verification", e);
        }
    }
}
