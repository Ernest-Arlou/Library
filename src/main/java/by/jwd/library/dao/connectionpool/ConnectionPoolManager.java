package by.jwd.library.dao.connectionpool;


import by.jwd.library.dao.DAOException;

import java.sql.SQLException;

public final class ConnectionPoolManager {
    private static final ConnectionPoolManager instance = new ConnectionPoolManager();

    private final ConnectionPool connectionPool = new ConnectionPool();

    private ConnectionPoolManager (){
    }

    public void initConnectionPool() throws DAOException{
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool initiation error",e);
        }
    }

    public void disposeConnectionPull() throws DAOException{
        try {
            connectionPool.dispose();
        } catch (SQLException e) {
            throw new DAOException("Connection pool dispose error",e);
        }
    }

    public static ConnectionPoolManager getInstance (){
        return instance;
    }

    public ConnectionPool getConnectionPool (){
        return connectionPool;
    }


}
