package by.jwd.library.dao.connectionpool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public interface ConnectionPool {

    void initPoolData() throws ConnectionPoolException;
    void dispose() throws ConnectionPoolException;
    Connection takeConnection() throws ConnectionPoolException;


    void closeConnection(Connection connection, Statement statement, ResultSet resultSet);

    void closeConnection(Connection connection, Statement statement);

    void closeConnection(Connection connection);

    void closeStatement(Statement statement);

    void closeStatement(Statement statement, ResultSet resultSet);

    void closeResultSet(ResultSet resultSet);
}
