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
import by.jwd.library.service.validation.LibraryValidator;
import by.jwd.library.service.validation.factory.ValidationFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;


public class LibraryServiceImpl implements LibraryService {
    private final static int RESERVATION_DURATION_DAYS = 3;
    private final static int LOAN_DURATION_DAYS = 20;
    private final static int DURATION_ONE_DAY = 1;
    private final static int MAX_LOANS = 3;
    private final static double FEE_PER_DAY = 0.005;
    private final static int DIGITS = 2;
    private final static String NO_RESTRICTION_FIELD = "NoRestriction";
    private final static String READING_ROOM_RESTRICTION = "reading room only";

    @Override
    public synchronized void closeOutdatedReservations() throws ServiceException {

        try {
            DAOFactory.getInstance().getLibraryDAO().closeOutdatedReservations();
        } catch (DAOException e) {
            throw new ServiceException("Error during reservation closing", e);
        }

    }

    @Override
    public synchronized void returnMedia(int copyId, int loanId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if ((!libraryValidator.validateId(copyId)) ||
                (!libraryValidator.validateId(loanId))) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            DAOFactory.getInstance().getLibraryDAO().returnMedia(copyId, loanId);
        } catch (DAOException e) {
            throw new ServiceException("Error during media give out", e);
        }

    }

    @Override
    public synchronized void giveOutCopy(int userId, int copyId, int reservationId, int mediaId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if ((!libraryValidator.validateId(userId)) ||
                (!libraryValidator.validateId(copyId)) ||
                (!libraryValidator.validateId(reservationId)) ||
                (!libraryValidator.validateId(mediaId))) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            MediaDetail mediaDetail = DAOFactory.getInstance().getLibraryDAO().getMediaDetail(mediaId);
            int duration;
            if (mediaDetail.getRestriction().equals(READING_ROOM_RESTRICTION)) {
                duration = DURATION_ONE_DAY;
            } else {
                duration = LOAN_DURATION_DAYS;
            }
            DAOFactory.getInstance().getLibraryDAO().giveOutCopy(userId, copyId, reservationId, duration);
        } catch (DAOException e) {
            throw new ServiceException("Error during media give out", e);
        }

    }

    @Override
    public synchronized void deleteReservation(int reservationId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(reservationId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            DAOFactory.getInstance().getLibraryDAO().deleteReservation(reservationId);
        } catch (DAOException e) {
            throw new ServiceException("Error during reservation delete", e);
        }

    }

    @Override
    public synchronized void reserveMedia(int userId, int mediaId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if ((!libraryValidator.validateId(userId)) ||
                (!libraryValidator.validateId(mediaId))) {
            throw new ServiceException("Invalid parameters");
        }


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

            LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
            if ((!libraryValidator.validatePage(page)) ||
                    (!libraryValidator.validateItemsPerPage(itemsPerPage))) {
                throw new ServiceException("Invalid parameters");
            }

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

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        int total = 0;
        List<LoanType> reservations = getUserReservations(userId);
        List<LoanType> loans = getUserLoans(userId);
        total += reservations.size() + loans.size();
        return total;
    }

    @Override
    public boolean canReserve(int userId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        return totalLoansReservations(userId) < MAX_LOANS;
    }

    @Override
    public boolean userReservedOrLoanedMedia(int userId, int mediaId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if ((!libraryValidator.validateId(userId)) ||
                (!libraryValidator.validateId(mediaId))) {
            throw new ServiceException("Invalid parameters");
        }

        return userLoanedMedia(userId, mediaId) || userReservedMedia(userId, mediaId);
    }

    @Override
    public boolean userReservedMedia(int userId, int mediaId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if ((!libraryValidator.validateId(userId)) ||
                (!libraryValidator.validateId(mediaId))) {
            throw new ServiceException("Invalid parameters");
        }

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

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if ((!libraryValidator.validateId(userId)) ||
                (!libraryValidator.validateId(mediaId))) {
            throw new ServiceException("Invalid parameters");
        }

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
    public MediaDetail getMediaDetail(int mediaId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(mediaId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getLibraryDAO().getMediaDetail(mediaId);
        } catch (DAOException e) {
            throw new ServiceException("Error during media details load", e);
        }
    }

    @Override
    public List<DeliveryType> searchLoans(String searchStr) throws ServiceException {
        try {
            List<DeliveryType> loans = DAOFactory.getInstance().getLibraryDAO().searchLoans(searchStr);
            calculateLoanPrice(loans);
            return loans;
        } catch (DAOException e) {
            throw new ServiceException("Error during reservations load", e);
        }
    }

    @Override
    public List<DeliveryType> getAllLoans() throws ServiceException {
        try {
            List<DeliveryType> loans = DAOFactory.getInstance().getLibraryDAO().getAllLoans();
            calculateLoanPrice(loans);
            return loans;
        } catch (DAOException e) {
            throw new ServiceException("Error during reservations load", e);
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
    public List<LoanType> getLoansForMedia(int mediaId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(mediaId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getLibraryDAO().getLoansForMedia(mediaId);
        } catch (DAOException e) {
            throw new ServiceException("Error during loans load", e);
        }
    }

    @Override
    public List<LoanType> getUserReservations(int userId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getLibraryDAO().getUserReservations(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error during user reservations load", e);
        }
    }

    @Override
    public synchronized int addMedia(MediaDetail mediaDetail) throws ServiceException {

        Set<String> validate = ValidationFactory.getInstance().getLibraryValidator().validateMediaDetail(mediaDetail);
        if (!validate.isEmpty()) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            if (mediaDetail.getRestriction().equals(NO_RESTRICTION_FIELD)) {
                mediaDetail.setRestriction(null);
            }
            return DAOFactory.getInstance().getLibraryDAO().addMedia(mediaDetail);
        } catch (DAOException e) {
            throw new ServiceException("Error during adding new media", e);
        }
    }

    @Override
    public synchronized void editMedia(MediaDetail mediaDetail) throws ServiceException {

        Set<String> validate = ValidationFactory.getInstance().getLibraryValidator().validateMediaDetail(mediaDetail);
        if (!validate.isEmpty()) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            if (mediaDetail.getRestriction().equals(NO_RESTRICTION_FIELD)) {
                mediaDetail.setRestriction(null);
            }
            DAOFactory.getInstance().getLibraryDAO().editMedia(mediaDetail);
        } catch (DAOException e) {
            throw new ServiceException("Error during media edit", e);
        }
    }

    @Override
    public List<LoanType> getUserLoans(int userId) throws ServiceException {

        LibraryValidator libraryValidator = ValidationFactory.getInstance().getLibraryValidator();
        if (!libraryValidator.validateId(userId)) {
            throw new ServiceException("Invalid parameters");
        }

        try {
            return DAOFactory.getInstance().getLibraryDAO().getUserLoans(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error during user loans load", e);
        }
    }

    private void calculateLoanPrice(List<DeliveryType> loans) {
        for (DeliveryType deliveryType :
                loans) {
            LocalDate startDate = deliveryType.getLoanType().getStartDate();
            LocalDate now = LocalDate.now();
            long duration = DAYS.between(startDate, now);
            double priceOfMedia = deliveryType.getLoanType().getMediaDetail().getPrice();
            double priceForLoan = duration * FEE_PER_DAY * priceOfMedia;

            BigDecimal bd = new BigDecimal(priceForLoan).setScale(DIGITS, RoundingMode.HALF_UP);
            priceForLoan = bd.doubleValue();


            deliveryType.getLoanType().getMediaDetail().setPrice(priceForLoan);
        }
    }

}
