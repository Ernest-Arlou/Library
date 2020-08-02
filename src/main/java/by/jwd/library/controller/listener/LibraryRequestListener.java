package by.jwd.library.controller.listener;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.factory.ConnectionPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LibraryRequestListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(LibraryRequestListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPoolFactory.getInstance().getConnectionPool().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryRequestListener method contextInitialized() - initConnectionPool fail", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPoolFactory.getInstance().getConnectionPool().dispose();
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryRequestListener method contextDestroyed() - disposeConnectionPull fail", e);
            throw new RuntimeException();
        }
    }
}
