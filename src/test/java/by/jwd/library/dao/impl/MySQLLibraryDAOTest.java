package by.jwd.library.dao.impl;

import by.jwd.library.bean.*;

import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.LibraryDAO;


import by.jwd.library.dao.connectionpool.ConnectionPool;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.dao.testconnection.TestConnectionPoolImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class MySQLLibraryDAOTest {

    private static final Logger logger = LoggerFactory.getLogger(MySQLLibraryDAOTest.class);

    @Mock
    private static ConnectionPool mockedConnectionPool;

    private static final LibraryDAO libraryDAO = DAOFactory.getInstance().getLibraryDAO();



    @BeforeClass
    public static void initializeConnectionPool() {

        mockedConnectionPool = new TestConnectionPoolImpl();
        try {
            mockedConnectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            logger.error("DAOException - Connection pool didn't initialize ", e);
        }

        Whitebox.setInternalState(libraryDAO, "connectionPool", mockedConnectionPool);
    }


    @AfterClass
    public static void disposeConnectionPool() {
        try {
            mockedConnectionPool.dispose();
        } catch (ConnectionPoolException e) {
                        logger.error("DAOException - Connection pool didn't dispose ", e);
        }
    }

    private MediaDetail getMedia1Detail(){
        MediaDetail mediaDetail1 = new MediaDetail();
        mediaDetail1.setMediaID(1);
        mediaDetail1.setPrice(11);
        mediaDetail1.setTitle("Harry Potter and the Philosopher's Stone");
        mediaDetail1.setSummary("When mysterious letters start arriving on his doorstep, Harry Potter has never heard of Hogwarts School of Witchcraft and Wizardry.");
        mediaDetail1.setiSBN("978-1408855898");
        mediaDetail1.setPicture("https://images-na.ssl-images-amazon.com/images/I/51ifu1aebKL._SX332_BO1,204,203,200_.jpg");
        mediaDetail1.setPublisher("Bloomsbury");
        mediaDetail1.setFormat("Твердый переплет");
        mediaDetail1.setLanguage("Английский");
        mediaDetail1.setRestriction("reading room only");

        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1,"Joanne Kathleen Rowling"));

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1,"Fantasy"));
        genres.add(new Genre(2,"Magic"));

        mediaDetail1.setAuthors(authors);
        mediaDetail1.setGenres(genres);

        return mediaDetail1;
    }

    @Test(expected = DAOException.class)
    public synchronized void giveOutCopy() throws DAOException {
        int daysDuration = 3;
        int userId = 2;
        int mediaId = 3;

        libraryDAO.reserve(daysDuration,userId,mediaId);
        List<LoanType> allReservations = libraryDAO.getUserReservations(userId);
        int newReservationId =  allReservations.get(allReservations.size()-1).getLoanTypeId();
        int copyId =  allReservations.get(allReservations.size()-1).getCopyId();

        libraryDAO.giveOutCopy(userId,copyId,newReservationId,daysDuration);

        List<LoanType> loansAfterAdd = libraryDAO.getUserLoans(userId);
        LoanType lastLoan = loansAfterAdd.get(loansAfterAdd.size()-1);

        Assert.assertTrue(lastLoan.getUserId() == userId && lastLoan.getMediaDetail().getMediaID() == mediaId);

        libraryDAO.returnMedia(lastLoan.getCopyId(),lastLoan.getLoanTypeId());

        libraryDAO.giveOutCopy(-1,-1,-1,-1);

    }

    @Test
    public synchronized void editMedia() throws DAOException {
        String sameTitle = "AAAAAA";

        MediaDetail mediaDetail1 = getMedia1Detail();
        int newMediaId = libraryDAO.addMedia(mediaDetail1);

        MediaDetail mediaForEdit = libraryDAO.getMediaDetail(newMediaId);

        mediaDetail1.setTitle(sameTitle);
        mediaForEdit.setTitle(sameTitle);

        libraryDAO.editMedia(mediaForEdit);

        MediaDetail mediaDetail =  libraryDAO.getMediaDetail(newMediaId);
        mediaDetail.setMediaID(1);
        Assert.assertEquals(mediaDetail1, mediaDetail);

    }

    @Test
    public synchronized void returnMedia() throws DAOException {
        int daysDuration = 3;
        int userId = 2;
        int mediaId = 2;

        libraryDAO.reserve(daysDuration,userId,mediaId);
        List<LoanType> allReservations = libraryDAO.getUserReservations(userId);
        int newReservationId =  allReservations.get(allReservations.size()-1).getLoanTypeId();
        int copyId =  allReservations.get(allReservations.size()-1).getCopyId();

        List<LoanType> loansBeforeGiveOut = libraryDAO.getUserLoans(userId);
        libraryDAO.giveOutCopy(userId,copyId,newReservationId,daysDuration);
        List<LoanType> loansBeforeReturn = libraryDAO.getUserLoans(userId);
        LoanType lastLoan = loansBeforeReturn.get(loansBeforeReturn.size()-1);

        libraryDAO.returnMedia(lastLoan.getCopyId(),lastLoan.getLoanTypeId());

        List<LoanType> loansAfterReturn = libraryDAO.getUserLoans(userId);

        Assert.assertEquals(loansBeforeGiveOut,loansAfterReturn);
        Assert.assertNotEquals(loansBeforeReturn,loansAfterReturn);

    }

    @Test
    public synchronized void deleteReservation() throws DAOException {
        int daysDuration = 3;
        int userId = 2;
        int mediaId = 3;
        libraryDAO.reserve(daysDuration,userId,mediaId);
        List<LoanType> allReservations = libraryDAO.getUserReservations(userId);

        int newReservationId =  allReservations.get(allReservations.size()-1).getLoanTypeId();
        libraryDAO.deleteReservation(newReservationId);

        List<DeliveryType> allReservationsWithoutDeleted = libraryDAO.getAllReservations();
        Assert.assertNotEquals(allReservationsWithoutDeleted, allReservations);

    }

    @Test
    public void getMediaDetail() throws DAOException {

        MediaDetail mediaDetail2 = libraryDAO.getMediaDetail(1);

        Assert.assertNull(libraryDAO.getMediaDetail(-1));
        Assert.assertEquals(getMedia1Detail(), mediaDetail2);

    }

    @Test(expected = NullPointerException.class)
    public synchronized void addMedia() throws DAOException {
        int newMediaId = libraryDAO.addMedia(getMedia1Detail());
        MediaDetail mediaDetail =  libraryDAO.getMediaDetail(newMediaId);
        mediaDetail.setMediaID(1);
        Assert.assertEquals(getMedia1Detail(), mediaDetail);

        libraryDAO.addMedia(null);

    }

    @Test(expected = DAOException.class)
    public synchronized void reserve() throws DAOException {
        int daysDuration = 3;
        int userId = 2;
        int mediaId = 3;
        boolean reservationSuccess = false;
        libraryDAO.reserve(daysDuration,userId,mediaId);
        List<LoanType> reservations = libraryDAO.getUserReservations(userId);
        for (LoanType reservation
                :reservations) {
            if (reservation.getMediaDetail().getMediaID() == mediaId && reservation.getDuration() == daysDuration && reservation.getUserId() == userId){
                reservationSuccess = true;
                libraryDAO.deleteReservation(reservation.getLoanTypeId());
            }
        }
        Assert.assertTrue(reservationSuccess);

        libraryDAO.reserve(-1,-1,-1);
        libraryDAO.reserve(-1,-1,1);

    }

    @Test
    public synchronized void closeOutdatedReservations() throws DAOException {
        List<DeliveryType> reservationsBeforeAdd = libraryDAO.getAllReservations();
        int daysDuration = -1;
        int userId = 2;
        int mediaId = 3;

        libraryDAO.reserve(daysDuration,userId,mediaId);
        List<DeliveryType> reservationsAfterAdd = libraryDAO.getAllReservations();
        libraryDAO.closeOutdatedReservations();

        List<DeliveryType> reservationsAfterClose = libraryDAO.getAllReservations();

        Assert.assertEquals(reservationsBeforeAdd,reservationsAfterClose);
        Assert.assertNotEquals(reservationsBeforeAdd,reservationsAfterAdd);

    }

}