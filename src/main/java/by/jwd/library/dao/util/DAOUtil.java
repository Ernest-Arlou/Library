package by.jwd.library.dao.util;

import by.jwd.library.bean.User;
import by.jwd.library.dao.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The interface Dao util.
 */
public interface DAOUtil {

    /**
     * Gets user by id.
     *
     * @param connection the connection
     * @param userId     the user id
     * @return the user by id
     * @throws DAOException the dao exception
     */
    User getUserById(Connection connection, int userId) throws DAOException;

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     * @throws DAOException the dao exception
     */
    User getUserById(int userId) throws DAOException;

    /**
     * Gets user from prepared statement.
     *
     * @param preparedStatement the prepared statement
     * @return the user from prepared statement
     * @throws SQLException the sql exception
     */
    User getUserFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException;

    /**
     * Build user user.
     *
     * @param resultSet the result set
     * @return the user
     * @throws SQLException the sql exception
     */
    User buildUser(ResultSet resultSet) throws SQLException;

}
