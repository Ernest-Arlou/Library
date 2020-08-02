package by.jwd.library.dao.testconnection;

import by.jwd.library.dao.connectionpool.ConnectionPool;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class TestConnectionPoolImpl implements ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(TestConnectionPoolImpl.class);
    private final String driverName;
    private final String url;
    private final String user;
    private final String password;
    private int poolsize;
    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    public TestConnectionPoolImpl() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolsize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolsize = 5;
        }
    }

    @Override
    public void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            givenAwayConQueue = new
                    ArrayBlockingQueue<>(poolsize);
            connectionQueue = new ArrayBlockingQueue<>(poolsize);
            for (int i = 0; i < poolsize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPool method initPoolData()", e);
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException in ConnectionPool method initPoolData()", e);
            throw new ConnectionPoolException("Can’t find database driver class", e);
        }
    }

    @Override
    public void dispose() throws ConnectionPoolException {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() throws ConnectionPoolException {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPool method clearConnectionQueue()", e);
            throw  new ConnectionPoolException(e);
        }

    }

    private void closeConnectionsQueue(BlockingQueue<Connection> connectionQueue) throws SQLException {
        for (Connection con :
                connectionQueue) {
            if (con.getClass() == PooledConnection.class) {
                ((PooledConnection) con).reallyClose();
            } else {
                con.close();
            }
            connectionQueue.remove(con);
        }
    }

    @Override
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            logger.error("InterruptedException in ConnectionPool method takeConnection() - Error connecting to the data source", e);
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    private void closeConnectionMethod(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPool method closeConnection() - Connection isn't return to the pool.", e);
        }
    }

    private void closeStatementMethod(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPool method closeStatement() - Statement isn't closed", e);
        }
    }

    private void closeResultSetMethod(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPool method closeResultSet() - ResultSet isn't closed.", e);
        }
    }


    @Override
    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        closeResultSetMethod(resultSet);
        closeStatementMethod(statement);
        closeConnectionMethod(connection);
    }

    @Override
    public void closeConnection(Connection connection, Statement statement) {
        closeStatementMethod(statement);
        closeConnectionMethod(connection);
    }

    @Override
    public void closeConnection(Connection connection) {
        closeConnectionMethod(connection);
    }

    @Override
    public void closeStatement(Statement statement) {
        closeStatementMethod(statement);
    }

    @Override
    public void closeStatement(Statement statement, ResultSet resultSet) {
        closeStatementMethod(statement);
        closeResultSetMethod(resultSet);
    }

    @Override
    public void closeResultSet(ResultSet resultSet) {
        closeResultSetMethod(resultSet);
    }


    private class PooledConnection implements Connection {
        private final Connection connection;

        public PooledConnection(Connection c) throws SQLException {
            this.connection = c;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                logger.error("SQLException in ConnectionPool method close() - Attempting to close closed connection");
                throw new SQLException("Attempting to close closed connection. ");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConQueue.remove(this)) {
                logger.error("SQLException in ConnectionPool method close() - Error deleting connection from the given away connections pool");
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }

            if (!connectionQueue.offer(this)) {
                logger.error("SQLException in ConnectionPool method close() - Error allocating connection in the pool");
                throw new SQLException("Error allocating connection in the pool.");
            }
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public void setClientInfo(Properties argO) throws SQLClientInfoException {
            connection.setClientInfo(argO);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> argO) throws SQLException {
            connection.setTypeMap(argO);
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setReadOnly(boolean readonly) throws SQLException {
            connection.setReadOnly(readonly);
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws
                SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                             int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnindexes) throws SQLException {
            return connection.prepareStatement(sql, columnindexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws
                SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public void abort(Executor argO) throws SQLException {
            connection.abort(argO);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void setSchema(String argO) throws SQLException {
            connection.setSchema(argO);
        }

        @Override
        public void releaseSavepoint(Savepoint argO) throws SQLException {
            connection.releaseSavepoint(argO);
        }

        @Override
        public void rollback(Savepoint argO) throws SQLException {
            connection.rollback(argO);
        }

        @Override
        public void setNetworkTimeout(Executor argO, int argl) throws SQLException {
            connection.setNetworkTimeout(argO, argl);
        }
    }
}



