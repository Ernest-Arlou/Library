package by.jwd.library.dao.factory;

import by.jwd.library.dao.LibraryDAO;
import by.jwd.library.dao.impl.LibraryDAOImpl;
import by.jwd.library.dao.UserDAO;
import by.jwd.library.dao.impl.UserDAOImpl;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO userDAO = new UserDAOImpl();
    private final LibraryDAO libraryDAO = new LibraryDAOImpl();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public LibraryDAO getLibraryDAO() {
        return libraryDAO;
    }


}
