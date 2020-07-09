package by.jwd.library;

import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main {
    private static final String format = "hh:mm:ss dd.MM.yyyy";
    private static final String format1 = "dd.MM.yyyy";



    public static void main(String[] args) throws DAOException, ServiceException {
        ConnectionPoolManager.getInstance().initConnectionPool();

        System.out.println(ServiceFactory.getInstance().getLibraryService().getMediaDetail(1));





    }
}
