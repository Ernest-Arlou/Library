package by.jwd.library;

import by.jwd.library.bean.MediaDisplay;
import by.jwd.library.bean.MediaPage;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;
import by.jwd.library.service.util.Pagination;

import java.util.List;



public class Main {



    public static void main(String[] args) throws DAOException, ServiceException {
        ConnectionPoolManager.getInstance().initConnectionPool();
//        System.out.println( DAOFactory.getInstance().getUserDAO().getUserByEmail("2"));
        System.out.println(ServiceFactory.getInstance().getUserService().emailExists(DAOFactory.getInstance().getUserDAO().getUserByEmail("2")));




    }
}
