package by.jwd.library.dao;


import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;
import by.jwd.library.bean.LoanType;

import java.util.List;

public interface LibraryDAO {
    void setCopyStatus(int copyId, String status) throws DAOException;

    int getAvailableCopyId(int mediaTypeId) throws DAOException;

    void addReservation(int daysDuration, int userId, int copyId) throws DAOException;

    MediaPage getMediaTypePage(int page, int itemsPerPage, String search) throws DAOException;

    List<LoanType> getUserReservations(int userId) throws DAOException;

    List<LoanType> getUserLoans(int userId) throws DAOException;

    MediaDetail getMediaDetail(int mediaTypeId) throws DAOException;
//    List <MediaDisplay> getMediaDisplayItems(int numberPerPage, int pageNumb) throws DAOException;


//    List <MediaDisplay> getMediaTypePage(int numberPerPage, int pageNumb) throws DAOException;

//    List<Media> getAllMedia() throws DAOException;
}
