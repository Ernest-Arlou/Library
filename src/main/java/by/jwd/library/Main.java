package by.jwd.library;

import by.jwd.library.dao.DAOException;
import by.jwd.library.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws DAOException, ServiceException {
//        ConnectionPoolManager.getInstance().initConnectionPool();


//        System.out.println(ServiceFactory.getInstance().getLibraryService().searchLoans("11111111111111"));

//        ServiceFactory.getInstance().getLibraryService().closeOutdatedReservations();


        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("TEST {}", localDateTime);

    }
}
