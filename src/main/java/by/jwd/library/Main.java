package by.jwd.library;

import by.jwd.library.bean.Author;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main {




    public static void main(String[] args) throws DAOException, ServiceException {
        ConnectionPoolManager.getInstance().initConnectionPool();


    }
}
