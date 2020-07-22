package by.jwd.library.dao;


import by.jwd.library.bean.DeliveryType;
import by.jwd.library.bean.LoanType;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;

import java.util.List;

public interface LibraryDAO {

    void deleteReservation(int reservationId) throws DAOException;

    void returnMedia(int copyId, int loanId) throws DAOException;

    List<LoanType> getLoansForMedia(int mediaId) throws DAOException;

    List<LoanType> getReservationsForMedia(int mediaId) throws DAOException;

    void editMedia(MediaDetail mediaDetail) throws DAOException;

    int addMedia(MediaDetail mediaDetail) throws DAOException;

    void giveOutCopy(int userId, int copyId, int reservationId, int daysDuration) throws DAOException;

    void reserve(int daysDuration, int userId, int copyId) throws DAOException;

    MediaPage getMediaPage(int page, int itemsPerPage, String search) throws DAOException;

    List<DeliveryType> getAllReservations() throws DAOException;

    List<DeliveryType> searchReservations(String searchStr) throws DAOException;

    List<DeliveryType> getAllLoans() throws DAOException;

    List<DeliveryType> searchLoans(String searchStr) throws DAOException;

    List<LoanType> getUserReservations(int userId) throws DAOException;

    List<LoanType> getUserLoans(int userId) throws DAOException;

    MediaDetail getMediaDetail(int mediaTypeId) throws DAOException;

}
