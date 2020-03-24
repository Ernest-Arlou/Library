package by.jwd.registration.dao.factory;

import by.jwd.registration.dao.LibraryDAO;
import by.jwd.registration.dao.LibraryDAOImpl;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final LibraryDAO libraryDAO = new LibraryDAOImpl();

    private DAOFactory (){
    }

    public static DAOFactory getInstance (){
        return instance;
    }

    public LibraryDAO getLibraryDAO (){
        return libraryDAO;
    }
}
