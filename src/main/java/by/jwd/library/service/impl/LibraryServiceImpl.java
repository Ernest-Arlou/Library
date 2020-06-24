package by.jwd.library.service.impl;

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
    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_LOANED = "Loaned";
    private static final String STATUS_RESERVED = "Reserved";

    private final static int RESERVATION_DURATION_DAYS = 3;

    private final static int MAX_LOANS = 3;

    @Override
    public void reserveMedia(int userId, int mediaTypeId) throws ServiceException {
        LibraryDAO libraryDAO = DAOFactory.getInstance().getLibraryDAO();
        try {
            int copyId = libraryDAO.getAvailableCopyId(mediaTypeId);
            if (copyId > 0) {
                libraryDAO.setCopyStatus(copyId, STATUS_RESERVED);
                libraryDAO.addReservation(RESERVATION_DURATION_DAYS, userId, copyId);
            }
        } catch (DAOException e) {
            throw new ServiceException("Error during reservation", e);
        }

    }

    @Override
    public MediaPage getPageItems(int page, int itemsPerPage, String search) throws ServiceException {
        try {
            MediaPage mediaPage = DAOFactory.getInstance().getLibraryDAO().getMediaTypePage(page, itemsPerPage, search);
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
    public boolean canReserve (int userId) throws ServiceException {
        return totalLoansReservations(userId) < MAX_LOANS;
    }

    @Override
    public boolean userReservedOrLoanedMediaType(int userId, int mediaTypeId) throws ServiceException {
        return userLoanedMediaType(userId, mediaTypeId) || userReservedMediaType(userId, mediaTypeId);
    }

    @Override
    public boolean userReservedMediaType(int userId, int mediaTypeId) throws ServiceException {
        List<LoanType> reservations = getUserReservations(userId);
        for (LoanType loanType :
                reservations) {
            if (loanType.getMediaDetail().getMediaTypeID() == mediaTypeId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean userLoanedMediaType(int userId, int mediaTypeId) throws ServiceException {
        List<LoanType> loans = getUserLoans(userId);
        for (LoanType loanType :
                loans) {
            if (loanType.getMediaDetail().getMediaTypeID() == mediaTypeId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MediaDetail getMediaDetail(int mediaTypeId) throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().getMediaDetail(mediaTypeId);
        } catch (DAOException e) {
            throw new ServiceException("Error during media details load", e);
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
            throw new ServiceException("Error during user reservations load", e);
        }
    }

}
