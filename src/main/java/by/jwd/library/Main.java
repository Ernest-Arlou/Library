package by.jwd.library;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.factory.DAOFactory;

public class Main {

    public static void main(String[] args) throws DAOException {
        ConnectionPoolManager.getInstance().initConnectionPool();
//        List <MediaType> mediaTypes = new UserDAOImpl().getAllMediaTypes();
//        System.out.println(mediaTypes);

        System.out.println(
                DAOFactory.getInstance().getLibraryDAO().getAllMedia()
        );

    }
}
