package by.jwd.library;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;


public class Main {
    private static String format = "hh:mm:ss dd.MM.yyyy";
    private static String format1 = "dd.MM.yyyy";



    public static void main(String[] args) throws DAOException, ServiceException {
//        ConnectionPoolManager.getInstance().initConnectionPool();

//        FormatStyle formatStyle = FormatStyle.valueOf(format1);
//        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofLocalizedDate(formatStyle);


        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
    }
}
