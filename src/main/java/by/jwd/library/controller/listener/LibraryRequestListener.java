package by.jwd.library.controller.listener;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LibraryRequestListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(LibraryRequestListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPoolManager.getInstance().initConnectionPool();
        } catch (DAOException e) {
            logger.error("DAOException in LibraryRequestListener method contextInitialized() - initConnectionPool fail", e);
            throw new RuntimeException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPoolManager.getInstance().disposeConnectionPull();
        } catch (DAOException e) {
            logger.error("DAOException in LibraryRequestListener method contextDestroyed() - disposeConnectionPull fail", e);
            throw new RuntimeException();
        }
    }
}
