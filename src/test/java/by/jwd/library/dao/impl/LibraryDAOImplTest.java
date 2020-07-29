package by.jwd.library.dao.impl;


import by.jwd.library.bean.MediaDetail;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.LibraryDAO;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.*;
import org.slf4j.LoggerFactory;


public class LibraryDAOImplTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LibraryDAOImplTest.class);

    private static final LibraryDAO libraryDAO = new LibraryDAOImpl();

    private ListAppender<ILoggingEvent> appender;
    private Logger appLogger = (Logger) LoggerFactory.getLogger(LibraryDAOImpl.class);

    @BeforeClass
    public static void initializeConnectionPool() {
        try {
            ConnectionPoolManager.getInstance().initConnectionPool();
        } catch (DAOException e) {
            throw new RuntimeException();
        }
    }

    @AfterClass
    public static void disposeConnectionPool() {
        try {
            ConnectionPoolManager.getInstance().disposeConnectionPull();
        } catch (DAOException e) {
            throw new RuntimeException();
        }
    }

    @Before
    public void setUp() {
        appender = new ListAppender<>();
        appender.start();
        appLogger.addAppender(appender);
    }

    @After
    public void tearDown() {
        appLogger.detachAppender(appender);
    }

    @Test
    public void getMediaDetail() throws DAOException {

        MediaDetail mediaDetail2 = libraryDAO.getMediaDetail(1);
        System.out.println(mediaDetail2);
    }


//    @Test
//    public void closeOutdatedReservations() {
//    }
//
//    @Test
//    public void returnMedia() {
//    }
//
//    @Test
//    public void getLoansForMedia() {
//    }
//
//    @Test
//    public void editMedia() {
//    }
//
//    @Test
//    public void addMedia() {
//    }
//
//    @Test
//    public void giveOutCopy() {
//    }
//
//    @Test
//    public void reserve() {
//    }
//
//    @Test
//    public void deleteReservation() {
//    }
//
//    @Test
//    public void getMediaPage() {
//    }
//
//    @Test
//    public void getAllReservations() {
//    }
//
//    @Test
//    public void searchReservations() {
//    }
//
//    @Test
//    public void getAllLoans() {
//    }
//
//    @Test
//    public void searchLoans() {
//    }
//
//    @Test
//    public void getUserReservations() {
//    }
//
//    @Test
//    public void getUserLoans() {
//    }


}