package by.jwd.library.dao;


import by.jwd.library.bean.DeliveryType;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;
import by.jwd.library.bean.LoanType;

import java.util.List;

public interface LibraryDAO {
    void reserveCopy(int copyId) throws DAOException;

    void cancelCopyReservation(int copyId) throws DAOException;

    void deleteReservation(int reservationId) throws DAOException;

    int getAvailableCopyId(int mediaTypeId) throws DAOException;

    void addReservation(int daysDuration, int userId, int copyId) throws DAOException;

    MediaPage getMediaTypePage(int page, int itemsPerPage, String search) throws DAOException;

    LoanType getReservationById(int reservationId) throws DAOException;

    List<DeliveryType> getAllReservations() throws DAOException;

    List<DeliveryType> searchReservations(String searchStr) throws DAOException;

    List<LoanType> getUserReservations(int userId) throws DAOException;

    List<LoanType> getUserLoans(int userId) throws DAOException;

    MediaDetail getMediaDetail(int mediaTypeId) throws DAOException;
//    List <MediaDisplay> getMediaDisplayItems(int numberPerPage, int pageNumb) throws DAOException;


//    List <MediaDisplay> getMediaTypePage(int numberPerPage, int pageNumb) throws DAOException;

//    List<Media> getAllMedia() throws DAOException;
}
