package by.jwd.library;

import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.dao.DAOException;
import by.jwd.library.service.ServiceException;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main {
    private static final String format = "hh:mm:ss dd.MM.yyyy";
    private static final String format1 = "dd.MM.yyyy";



    public static void main(String[] args) throws DAOException, ServiceException {
//        ConnectionPoolManager.getInstance().initConnectionPool();

//        FormatStyle formatStyle = FormatStyle.valueOf(format1);
//        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofLocalizedDate(formatStyle);

//        Locale locale = new Locale("ru_RU");

        String s = "adasdaads+1=lastPage";
        System.out.println(s.indexOf("lastPage"));
        System.out.println(s.substring(0,s.indexOf("lastPage")));


    }
}
