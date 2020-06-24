package by.jwd.library;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class Main {
//    private static String format = "hh:mm:ss dd.MM.yyyy";
//
//    public static String getFormattedDate(Calendar calendar){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//        return simpleDateFormat.format(calendar.getTime());
//    }

    public static void main(String[] args) throws DAOException, ServiceException {
        ConnectionPoolManager.getInstance().initConnectionPool();

        ServiceFactory.getInstance().getLibraryService().reserveMedia(2,1);

//        DAOFactory.getInstance().getLibraryDAO().addReservation(3,1,2);
//
//        System.out.println(ServiceFactory.getInstance().getLibraryService().getUserLoans(4));


////        System.out.println( DAOFactory.getInstance().getUserDAO().getUserByEmail("2"));
//        ServiceFactory.getInstance().getUserService().verifyUser(7);
//        Calendar calendar = Calendar.getInstance();
//
//        Date date = new Date();
//
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_WEEK,6);
//
//
//        System.out.println(getFormattedDate(calendar));
//
//        DateFormat dateFormat = new SimpleDateFormat(format);
//        List <Reservation> reservations = DAOFactory.getInstance().getLibraryDAO().getUserReservations(4);
//        for (Reservation reservation :
//                reservations) {
//            System.out.println(dateFormat.format(reservation.getStartDate()));
//            System.out.println(dateFormat.format(reservation.getEndDate()));
//        }


//        LocalDate localDate = LocalDate.now();
//        localDate = localDate.plusDays(6);
//        System.out.println(localDate);



    }
}
