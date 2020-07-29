package by.jwd.library.dao.connectionpool;


import by.jwd.library.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public final class ConnectionPoolManager {
    private static final ConnectionPoolManager instance = new ConnectionPoolManager();

    private final ConnectionPool connectionPool = new ConnectionPool();

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPoolManager.class);

    private ConnectionPoolManager() {
    }

    public static ConnectionPoolManager getInstance() {
        return instance;
    }

    public void initConnectionPool() throws DAOException {
        try {
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in ConnectionPoolManager method initPoolData()", e);
            throw new DAOException("Connection pool initiation error", e);
        }
    }

    public void disposeConnectionPull() throws DAOException {
        try {
            connectionPool.dispose();
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPoolManager method disposeConnectionPull()", e);
            throw new DAOException("Connection pool dispose error", e);
        }
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }


}
