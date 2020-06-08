package by.jwd.library.controller.listener;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LibraryRequestListener implements ServletContextListener {
    @Override
    public void contextInitialized (ServletContextEvent servletContextEvent){
        try {
            ConnectionPoolManager.getInstance().initConnectionPool();
        } catch (DAOException e) {
            //log
            throw new RuntimeException();
        }
    }

    @Override
    public void contextDestroyed (ServletContextEvent servletContextEvent){
        try {
            ConnectionPoolManager.getInstance().disposeConnectionPull();
        } catch (DAOException e) {
            //log
            throw new RuntimeException();
        }
    }
}
