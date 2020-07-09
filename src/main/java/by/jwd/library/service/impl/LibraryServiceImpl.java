package by.jwd.library.service.impl;

import by.jwd.library.bean.DeliveryType;
import by.jwd.library.bean.LoanType;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.LibraryDAO;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.LibraryService;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.util.Pagination;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {
    private final static int RESERVATION_DURATION_DAYS = 3;
    private final static int LOAN_DURATION_DAYS = 20;
    private final static int MAX_LOANS = 3;

    @Override
    public void giveOutCopy(int userId, int copyId, int reservationId) throws ServiceException {
        try {
            DAOFactory.getInstance().getLibraryDAO().giveOutCopy(userId,copyId,reservationId,LOAN_DURATION_DAYS);
        } catch (DAOException e) {
            throw new ServiceException("Error during media give out", e);
        }

    }

    @Override
    public void deleteReservation(int reservationId) throws ServiceException {
        try {
           DAOFactory.getInstance().getLibraryDAO().deleteReservation(reservationId);
        } catch (DAOException e) {
            throw new ServiceException("Error during reservation delete", e);
        }

    }

    @Override
    public void reserveMedia(int userId, int mediaId) throws ServiceException {
        LibraryDAO libraryDAO = DAOFactory.getInstance().getLibraryDAO();
        try {
            libraryDAO.reserve(RESERVATION_DURATION_DAYS, userId, mediaId);
        } catch (DAOException e) {
            throw new ServiceException("Error during reservation", e);
        }

    }

    @Override
    public MediaPage getPageItems(int page, int itemsPerPage, String search) throws ServiceException {
        try {
            MediaPage mediaPage = DAOFactory.getInstance().getLibraryDAO().getMediaPage(page, itemsPerPage, search);
            mediaPage.setNavigationPages(Pagination.getInstance().calculateNavigationPages(mediaPage));
            int totalPages;
            if (mediaPage.getTotalItems() % mediaPage.getItemsPerPage() == 0) {
                totalPages = mediaPage.getTotalItems() / mediaPage.getItemsPerPage();
            } else {
                totalPages = (mediaPage.getTotalItems() / mediaPage.getItemsPerPage()) + 1;
            }
            mediaPage.setTotalPages(totalPages);
            return mediaPage;
        } catch (DAOException e) {
            throw new ServiceException("Error during page load", e);
        }
    }

    private int totalLoansReservations(int userId) throws ServiceException {
        int total = 0;
        List<LoanType> reservations = getUserReservations(userId);
        List<LoanType> loans = getUserLoans(userId);
        total += reservations.size() + loans.size();
        return total;
    }

    @Override
    public boolean canReserve(int userId) throws ServiceException {
        return totalLoansReservations(userId) < MAX_LOANS;
    }

    @Override
    public boolean userReservedOrLoanedMedia(int userId, int mediaId) throws ServiceException {
        return userLoanedMedia(userId, mediaId) || userReservedMedia(userId, mediaId);
    }

    @Override
    public boolean userReservedMedia(int userId, int mediaId) throws ServiceException {
        List<LoanType> reservations = getUserReservations(userId);
        for (LoanType loanType :
                reservations) {
            if (loanType.getMediaDetail().getMediaID() == mediaId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean userLoanedMedia(int userId, int mediaId) throws ServiceException {
        List<LoanType> loans = getUserLoans(userId);
        for (LoanType loanType :
                loans) {
            if (loanType.getMediaDetail().getMediaID() == mediaId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MediaDetail getMediaDetail(int mediaID) throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().getMediaDetail(mediaID);
        } catch (DAOException e) {
            throw new ServiceException("Error during media details load", e);
        }
    }

    @Override
    public List<DeliveryType> searchReservations(String searchStr) throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().searchReservations(searchStr);
        } catch (DAOException e) {
            throw new ServiceException("Error during reservations load", e);
        }
    }

    @Override
    public List<DeliveryType> getAllReservations() throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().getAllReservations();
        } catch (DAOException e) {
            throw new ServiceException("Error during reservations load", e);
        }
    }

    @Override
    public List<LoanType> getUserReservations(int userId) throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().getUserReservations(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error during user reservations load", e);
        }
    }

    @Override
    public List<LoanType> getUserLoans(int userId) throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().getUserLoans(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error during user loans load", e);
        }
    }

}
