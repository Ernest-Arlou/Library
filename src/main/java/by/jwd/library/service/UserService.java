package by.jwd.library.service;

import by.jwd.library.bean.User;

import java.util.List;

public interface UserService {
    boolean changePassword(int userId, String oldPass, String newPass) throws ServiceException;

    User getUserByLogin(String login) throws ServiceException;

    User getUserById(int userId) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    boolean loginExists(String login) throws ServiceException;

    boolean emailExists(String email) throws ServiceException;

    boolean passportIdExists(String passportId) throws ServiceException;

    boolean checkUnverified(int userId) throws ServiceException;

    List<User> getUnverifiedUsers() throws ServiceException;

    void editUser(User user) throws ServiceException;

    void register(User user) throws ServiceException;


    void changePassportId(int userId, String passportId) throws ServiceException;


    List<User> searchUnverifiedUsers(String searchStr) throws ServiceException;

    void verifyUser(int userId) throws ServiceException;
}
