package by.jwd.library.dao;

import by.jwd.library.bean.User;

import java.util.List;

public interface UserDAO {

    User getUserById(int userId) throws DAOException;

    User getUserByEmail(String email) throws DAOException;

    User getUserByLogin(String login) throws DAOException;

    User getUserByPassportId(String passportId) throws DAOException;

    List<User> searchUnverifiedUsers(String searchStr) throws DAOException;

    List<User> getUnverifiedUsers() throws DAOException;

    void updateUser(User user) throws DAOException;

    void addUser(User user) throws DAOException;


    User getUserByLogin(String login, String password) throws DAOException;
}
