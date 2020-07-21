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
//        ConnectionPoolManager.getInstance().initConnectionPool();

//        double a = 0.1232321412314124;
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        String str = decimalFormat.format(a);
//        System.out.println(str);
//        a = Double.parseDouble(str);


        double input = 3.14159265359;
        System.out.println("double : " + input);

        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
        double salary = bd.doubleValue();

        System.out.println("salary : " + salary);











    }
}
