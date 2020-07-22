package by.jwd.library;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;


public class Main {


    public static void main(String[] args) throws DAOException, ServiceException {
        ConnectionPoolManager.getInstance().initConnectionPool();


        System.out.println(ServiceFactory.getInstance().getLibraryService().searchLoans("11111111111111"));














    }
}
