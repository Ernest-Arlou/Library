package by.jwd.registration.controller;

import by.jwd.registration.dao.DAOException;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LibraryRequestListener implements ServletContextListener {
    @Override
    public void contextInitialized (ServletContextEvent servletContextEvent){
        try {
            ConnectionPoolManager.getInstance().initConnectionPool();
        } catch (DAOException e) {
            //log
        }
    }

    @Override
    public void contextDestroyed (ServletContextEvent servletContextEvent){
        try {
            ConnectionPoolManager.getInstance().disposeConnectionPull();
        } catch (DAOException e) {
            //log
        }
    }
}
