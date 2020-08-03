package by.jwd.library.dao;

import by.jwd.library.bean.User;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDAO {

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     * @throws DAOException the dao exception
     */
    User getUserById(int userId) throws DAOException;

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     * @throws DAOException the dao exception
     */
    User getUserByEmail(String email) throws DAOException;

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     * @throws DAOException the dao exception
     */
    User getUserByLogin(String login) throws DAOException;

    /**
     * Gets user by passport id.
     *
     * @param passportId the passport id
     * @return the user by passport id
     * @throws DAOException the dao exception
     */
    User getUserByPassportId(String passportId) throws DAOException;

    /**
     * Search unverified users list.
     *
     * @param searchStr the search str
     * @return the list
     * @throws DAOException the dao exception
     */
    List<User> searchUnverifiedUsers(String searchStr) throws DAOException;

    /**
     * Gets unverified users.
     *
     * @return the unverified users
     * @throws DAOException the dao exception
     */
    List<User> getUnverifiedUsers() throws DAOException;

    /**
     * Update user.
     *
     * @param user the user
     * @throws DAOException the dao exception
     */
    void updateUser(User user) throws DAOException;

    /**
     * Add user.
     *
     * @param user the user
     * @throws DAOException the dao exception
     */
    void addUser(User user) throws DAOException;

}
