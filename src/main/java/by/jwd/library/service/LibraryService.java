package by.jwd.library.service;

import by.jwd.library.bean.DeliveryType;
import by.jwd.library.bean.LoanType;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;

import java.util.List;

public interface LibraryService {

    void giveOutCopy(int userId, int copyId, int reservationId) throws ServiceException;

    void deleteReservation(int reservationId) throws ServiceException;

    void reserveMedia(int userId, int mediaTypeId) throws ServiceException;

    MediaPage getPageItems(int page, int itemsPerPage, String search) throws ServiceException;

    boolean canReserve(int userId) throws ServiceException;

    boolean userReservedOrLoanedMedia(int userId, int mediaTypeId) throws ServiceException;

    boolean userReservedMedia(int userId, int mediaTypeId) throws ServiceException;

    boolean userLoanedMedia(int userId, int mediaTypeId) throws ServiceException;

    MediaDetail getMediaDetail(int mediaID) throws ServiceException;

    List<DeliveryType> searchReservations(String searchStr) throws ServiceException;

    List<DeliveryType> getAllReservations() throws ServiceException;

    List<LoanType> getLoansForMedia(int mediaId) throws ServiceException;

    List<LoanType> getUserReservations(int userId) throws ServiceException;

    int addMedia(MediaDetail mediaDetail) throws ServiceException;

    List<LoanType> getUserLoans(int userId) throws ServiceException;
}
