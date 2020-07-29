package by.jwd.library.dao.impl;

import by.jwd.library.bean.Author;
import by.jwd.library.bean.Genre;
import by.jwd.library.bean.MediaDetail;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.LibraryDAO;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class LibraryDAOImplTest {

    private static final Logger logger = LoggerFactory.getLogger(LibraryDAOImplTest.class);

    private static final LibraryDAO libraryDAO = new LibraryDAOImpl();


    @BeforeClass
    public static void initializeConnectionPool() {
        try {
            ConnectionPoolManager.getInstance().initConnectionPool();
        } catch (DAOException e) {
            logger.error("DAOException - Connection pool didn't initialize ", e);
            throw new RuntimeException();
        }
    }

    @AfterClass
    public static void disposeConnectionPool() {
        try {
            ConnectionPoolManager.getInstance().disposeConnectionPull();
        } catch (DAOException e) {
            logger.error("DAOException - Connection pool didn't dispose ", e);
            throw new RuntimeException();
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

    @Test
    public void getMediaDetail() throws DAOException {

        MediaDetail mediaDetail2 = libraryDAO.getMediaDetail(1);

        Assert.assertNull(libraryDAO.getMediaDetail(-1));
        Assert.assertEquals(getMedia1Detail(), mediaDetail2);

    }

    @Test
    public void editMedia() throws DAOException {
        MediaDetail mediaDetail1 = getMedia1Detail();
        int newMediaId = libraryDAO.addMedia(mediaDetail1);

        MediaDetail mediaForEdit = libraryDAO.getMediaDetail(newMediaId);

        mediaDetail1.setTitle("AAAAAA");
        mediaForEdit.setTitle("AAAAAA");

        libraryDAO.editMedia(mediaForEdit);

        MediaDetail mediaDetail =  libraryDAO.getMediaDetail(newMediaId);
        mediaDetail.setMediaID(1);
        Assert.assertEquals(mediaDetail1, mediaDetail);
    }

    @Test
    public void addMedia() throws DAOException {
        int newMediaId = libraryDAO.addMedia(getMedia1Detail());
        MediaDetail mediaDetail =  libraryDAO.getMediaDetail(newMediaId);
        mediaDetail.setMediaID(1);
        Assert.assertEquals(getMedia1Detail(), mediaDetail);

    }

    @Test
    public void closeOutdatedReservations() {
    }

    @Test
    public void returnMedia() {
    }

    @Test
    public void getLoansForMedia() {
    }


    @Test
    public void giveOutCopy() {
    }

    @Test
    public void reserve() {
    }

    @Test
    public void deleteReservation() {
    }

    @Test
    public void getMediaPage() {
    }

    @Test
    public void getAllReservations() {
    }

    @Test
    public void searchReservations() {
    }

    @Test
    public void getAllLoans() {
    }

    @Test
    public void searchLoans() {
    }

    @Test
    public void getUserReservations() {
    }

    @Test
    public void getUserLoans() {
    }


}