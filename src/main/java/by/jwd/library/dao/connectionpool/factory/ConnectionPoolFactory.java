package by.jwd.library.dao.connectionpool.factory;

import by.jwd.library.dao.connectionpool.ConnectionPool;
import by.jwd.library.dao.connectionpool.impl.ConnectionPoolImpl;

public class ConnectionPoolFactory {
    private static final ConnectionPoolFactory instance = new ConnectionPoolFactory();

    private final ConnectionPool connectionPool = new ConnectionPoolImpl();

    private ConnectionPoolFactory() {
    }

    public static ConnectionPoolFactory getInstance() {
        return instance;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
