package by.jwd.library.dao;


import by.jwd.library.bean.DeliveryType;
import by.jwd.library.bean.LoanType;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;

import java.util.List;

/**
 * The interface Library dao.
 */
public interface LibraryDAO {

    /**
     * Delete reservation.
     *
     * @param reservationId the reservation id
     * @throws DAOException the dao exception
     */
    void deleteReservation(int reservationId) throws DAOException;

    /**
     * Close outdated reservations.
     *
     * @throws DAOException the dao exception
     */
    void closeOutdatedReservations() throws DAOException;

    /**
     * Return media.
     *
     * @param copyId the copy id
     * @param loanId the loan id
     * @throws DAOException the dao exception
     */
    void returnMedia(int copyId, int loanId) throws DAOException;

    /**
     * Gets loans for media.
     *
     * @param mediaId the media id
     * @return the loans for media
     * @throws DAOException the dao exception
     */
    List<LoanType> getLoansForMedia(int mediaId) throws DAOException;

    /**
     * Edit media.
     *
     * @param mediaDetail the media detail
     * @throws DAOException the dao exception
     */
    void editMedia(MediaDetail mediaDetail) throws DAOException;

    /**
     * Add media int.
     *
     * @param mediaDetail the media detail
     * @return the int new media id
     * @throws DAOException the dao exception
     */
    int addMedia(MediaDetail mediaDetail) throws DAOException;

    /**
     * Give out copy.
     *
     * @param userId        the user id
     * @param copyId        the copy id
     * @param reservationId the reservation id
     * @param daysDuration  the days duration
     * @throws DAOException the dao exception
     */
    void giveOutCopy(int userId, int copyId, int reservationId, int daysDuration) throws DAOException;

    /**
     * Reserve.
     *
     * @param daysDuration the days duration
     * @param userId       the user id
     * @param mediaId      the media id
     * @throws DAOException the dao exception
     */
    void reserve(int daysDuration, int userId, int mediaId) throws DAOException;

    /**
     * Gets media page.
     *
     * @param page         the page
     * @param itemsPerPage the items per page
     * @param search       the search
     * @return the media page
     * @throws DAOException the dao exception
     */
    MediaPage getMediaPage(int page, int itemsPerPage, String search) throws DAOException;

    /**
     * Gets all reservations.
     *
     * @return the all reservations
     * @throws DAOException the dao exception
     */
    List<DeliveryType> getAllReservations() throws DAOException;

    /**
     * Search reservations list.
     *
     * @param searchStr the search str
     * @return the list
     * @throws DAOException the dao exception
     */
    List<DeliveryType> searchReservations(String searchStr) throws DAOException;

    /**
     * Gets all loans.
     *
     * @return the all loans
     * @throws DAOException the dao exception
     */
    List<DeliveryType> getAllLoans() throws DAOException;

    /**
     * Search loans list.
     *
     * @param searchStr the search str
     * @return the list
     * @throws DAOException the dao exception
     */
    List<DeliveryType> searchLoans(String searchStr) throws DAOException;

    /**
     * Gets user reservations.
     *
     * @param userId the user id
     * @return the user reservations
     * @throws DAOException the dao exception
     */
    List<LoanType> getUserReservations(int userId) throws DAOException;

    /**
     * Gets user loans.
     *
     * @param userId the user id
     * @return the user loans
     * @throws DAOException the dao exception
     */
    List<LoanType> getUserLoans(int userId) throws DAOException;

    /**
     * Gets media detail.
     *
     * @param mediaTypeId the media type id
     * @return the media detail
     * @throws DAOException the dao exception
     */
    MediaDetail getMediaDetail(int mediaTypeId) throws DAOException;

}
