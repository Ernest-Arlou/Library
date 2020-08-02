package by.jwd.library.dao.impl;

import by.jwd.library.bean.*;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.LibraryDAO;
import by.jwd.library.dao.connectionpool.ConnectionPool;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.factory.ConnectionPoolFactory;
import by.jwd.library.dao.factory.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLLibraryDAO implements LibraryDAO {

    private final ConnectionPoolFactory connectionPoolFactory = ConnectionPoolFactory.getInstance();
    private final ConnectionPool connectionPool = connectionPoolFactory.getConnectionPool();

    private static final String GET_MEDIA_TYPES_PAGE = "SELECT * FROM `media`\n" +
            "inner join `material-types` on media.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on media.`language-id` = languages.`language-id`\n" +
            "inner join publishers on media.`publisher-id` = publishers.`publisher-id`\n" +
            "where \n" +
            "media.status = 'active' and `material-types`.status = 'active' and publishers.status = 'active' and languages.status = 'active' \n" +
            "order by `media-id` desc LIMIT ?,?";

    private static final String GET_MEDIA_PAGE_WITH_SEARCH = "SELECT  * FROM media\n" +
            "inner join `material-types` on media.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on media.`language-id` = languages.`language-id`\n" +
            "inner join publishers on media.`publisher-id` = publishers.`publisher-id`\n" +
            "where media.status = 'active' and `material-types`.status = 'active' and publishers.status = 'active' and languages.status = 'active' and media.title like ?" +
            "or media.status = 'active' and `material-types`.status = 'active' and publishers.status = 'active' and languages.status = 'active' and `material-types`.`material-type` like ? \n" +
            "order by `media-id` desc LIMIT ?,?; ";

    private static final String GET_MEDIA_TOTAL_ITEMS_WITH_SEARCH = "SELECT  *, count(*) `total-items` FROM media\n" +
            "inner join `material-types` on media.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on media.`language-id` = languages.`language-id`\n" +
            "inner join publishers on media.`publisher-id` = publishers.`publisher-id`\n" +
            "where media.status = 'active' and `material-types`.status = 'active' and publishers.status = 'active' and languages.status = 'active' and media.title like ?" +
            "or media.status = 'active' and `material-types`.status = 'active' and publishers.status = 'active' and languages.status = 'active' and `material-types`.`material-type` like ? \n" +
            "order by `media-id`; ";

    private static final String GET_ALL_RESERVATIONS = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted';";

    private static final String GET_ALL_LOANS = "SELECT * FROM loans\n" +
            "inner join copies on loans.`copy-id` = copies.`copy-id` \n" +
            "where loans.status = 'Active' and copies.status != 'deleted';";

    private static final String SEARCH_RESERVATIONS = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "inner join users on reservations.`user-id` = users.`user-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted' and users.status != 'deleted' and `passport-id` = ? ;";

    private static final String SEARCH_LOANS = "SELECT * FROM loans\n" +
            "inner join copies on loans.`copy-id` = copies.`copy-id` \n" +
            "inner join users on loans.`user-id` = users.`user-id` \n" +
            "where loans.status = 'Active' and copies.status != 'deleted' and users.status != 'deleted' and `passport-id` = ? ;";

    private static final String GET_LOANS_FOR_MEDIA = "select * from media\n" +
            "inner join copies on media.`media-id` = copies.`media-id`\n" +
            "inner join loans on copies.`copy-id` = loans.`copy-id`\n" +
            "where copies.status != 'deleted' and loans.status != 'deleted'\n" +
            "and media.`media-id` = ?";

    private static final String GET_RESERVATIONS_FOR_MEDIA = "select * from media\n" +
            "inner join copies on media.`media-id` = copies.`media-id`\n" +
            "inner join reservations on copies.`copy-id` = reservations.`copy-id`\n" +
            "where copies.status != 'deleted' and reservations.status != 'deleted'\n" +
            "and media.`media-id` = ?";


    private static final String GET_RESERVATION_BY_ID = "SELECT * FROM reservations inner join copies on reservations.`copy-id` = copies.`copy-id` where reservations.status = 'Active' and copies.status != 'deleted' and `reservation-id` = ? ";

    private static final String GET_RESERVATIONS_BY_USER_ID = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted' and `user-id`  = ?;";

    private static final String GET_LOANS_BY_USER_ID = "SELECT * FROM loans\n" +
            "inner join copies on loans.`copy-id` = copies.`copy-id` \n" +
            "where loans.status = 'Active' and copies.status != 'deleted' and `user-id`  = ?;";


    private static final String GET_MEDIA_DETAILS = "SELECT * FROM media \n" +
            "inner join `material-types` on media.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on media.`language-id` = languages.`language-id`\n" +
            "inner join publishers on media.`publisher-id` = publishers.`publisher-id`\n" +
            "where media.status != 'deleted' and `material-types`.status != 'deleted' \n" +
            "and publishers.status != 'deleted' and languages.status != 'deleted' and media.`media-id` = ?";

    private static final String GET_MEDIA_TOTAL_ITEMS = "SELECT * , count(*) `total-items` FROM `media` where status = 'active';";
    private static final String GET_COPIES_FOR_MEDIA_COUNT = "SELECT * , count(*) `total-items` FROM copies where status != 'deleted' and `media-id` = ?;";

    private static final String GET_AUTHORS_FOR_MEDIA = "SELECT * FROM `media-have-authors` inner join authors on `media-have-authors`.`author-id` = authors.`author-id` " +
            "where `media-id` = ? and `media-have-authors`.status != 'deleted' and authors.status != 'deleted' ;";
    private static final String GET_GENRES_FOR_MEDIA = "SELECT * FROM `media-have-genres` inner join genres on `media-have-genres`.`genre-id` = genres.`genre-id` " +
            "where `media-id` = ? and `media-have-genres`.status != 'deleted' and genres.status != 'deleted' ;";

    private static final String GET_COPIES_FOR_MEDIA = "SELECT * FROM copies where `media-id` = ? and status != 'Deleted';";

    private static final String GET_PUBLISHER_BY_NAME = "SELECT * FROM publishers where status = 'Active' and publisher = ?;";
    private static final String GET_LANGUAGE_BY_NAME = "SELECT * FROM languages where status = 'Active' and language = ?;";
    private static final String GET_MATERIAL_TYPE_BY_NAME = "SELECT * FROM `material-types` where status = 'Active' and `material-type` = ?;";
    private static final String GET_AUTHOR_BY_NAME = "SELECT * FROM authors where status = 'Active' and `full-name` = ?;";
    private static final String GET_GENRE_BY_NAME = "SELECT * FROM genres where status = 'Active' and genre = ?;";


    private static final String MEDIA_TITLE = "title";
    private static final String MEDIA_SUMMARY = "summary";
    private static final String TOTAL_ITEMS = "total-items";
    private static final String MEDIA_ID = "media-id";
    private static final String MEDIA_PRICE = "price";
    private static final String MEDIA_ISBN = "isbn";
    private static final String MEDIA_MATERIAL_TYPE = "material-type";
    private static final String MEDIA_PUBLISHER = "publisher";
    private static final String MEDIA_LANGUAGE = "language";
    private static final String MEDIA_PICTURE = "picture";
    private static final String MEDIA_RESTRICTION = "restriction";


    private static final String COPIES_COPY_ID = "copy-id";
    private static final String FIELD_STATUS = "status";
    private static final String COPIES_STATUS_LOANED = "loaned";
    private static final String COPIES_STATUS_RESERVED = "reserved";

    private static final String AUTHORS_AUTHOR_ID = "author-id";
    private static final String AUTHORS_AUTHOR_FULL_NAME = "full-name";

    private static final String GENRES_GENRE_ID = "genre-id";
    private static final String GENRES_GENRE = "genre";

    private static final String RESERVATIONS_RESERVATION_ID = "reservation-id";
    private static final String LOANS_LOAN_ID = "loan-id";
    private static final String LOAN_TYPE_USER_ID = "user-id";
    private static final String LOAN_TYPE_RESERVATION = "Reservation";
    private static final String LOAN_TYPE_LOAN = "Loan";
    private static final String LOAN_TYPE_COPY_ID = "copy-id";
    private static final String LOAN_TYPE_DURATION = "duration";
    private static final String LOAN_TYPE_START_DATE = "start-date";
    private static final String LOAN_TYPE_END_DATE = "end-date";

    private static final String LANGUAGE_ID = "language-id";
    private static final String PUBLISHER_ID = "publisher-id";
    private static final String AUTHOR_ID = "author-id";
    private static final String GENRE_ID = "genre-id";
    private static final String MATERIAL_TYPE_ID = "material-type-id";

    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_LOANED = "Loaned";
    private static final String STATUS_RESERVED = "Reserved";
    private static final String STATUS_DELETED = "Deleted";

    private static final String GET_LAST_MEDIA_ID = "SELECT * FROM media order by `media-id` desc;";

    private static final String ADD_COPY = "insert into copies (`media-id`) values (?);";

    private static final String ADD_AUTHOR_TO_MEDIA = "insert into `media-have-authors` (`media-id`, `author-id`) values (?,?); ";

    private static final String DELETE_AUTHORS_FROM_MEDIA = "delete  from `media-have-authors` where `media-id` = ?;";
    private static final String DELETE_GENRES_FROM_MEDIA = "delete from `media-have-genres` where `media-id` = ?;";

    private static final String ADD_GENRE_TO_MEDIA = "insert into `media-have-genres` (`media-id`, `genre-id`) values (?,?); ";

    private static final String ADD_MEDIA =
            "insert into media (title, summary, price, isbn, picture, `publisher-id`, `material-type-id`, `language-id`, restriction ) values(?,?,?,?,?,?,?,?,?);";
    private static final String EDIT_MEDIA =
            "update media set title = ?, summary = ?, price = ?, isbn = ?, picture = ?, `publisher-id` = ?, `material-type-id` = ?, " +
                    "`language-id` = ?, restriction = ? where `media-id` = ? ;";
    private static final String ADD_AUTHOR =
            "insert into authors (`full-name`) values(?)";
    private static final String ADD_GENRE =
            "insert into genres (genre) values(?)";
    private static final String ADD_PUBLISHER =
            "insert into publishers (publisher) values(?)";
    private static final String ADD_LANGUAGE =
            "insert into languages (language) values(?)";
    private static final String ADD_MATERIAL_TYPE =
            "insert into `material-types` (`material-type`) values(?)";

    private static final String ADD_RESERVATION =
            "insert into reservations(`start-date`,duration,`user-id`,`copy-id`) values(?,?,?,?)";

    private static final String ADD_LOAN =
            "insert into loans(`start-date`,duration,`user-id`,`copy-id`) values(?,?,?,?)";

    private static final String GET_AVAILABLE_COPIES_FOR_MEDIA =
            "SELECT * FROM library.copies where status = 'Active' and `media-id` = ? ;";

    private static final String UPDATE_COPY_STATUS =
            "update copies set status = ? where `copy-id` = ?;";

    private static final String UPDATE_RESERVATION_STATUS =
            "update reservations set status = ? where `reservation-id` = ?;";

    private static final String UPDATE_LOAN_STATUS =
            "update loans set status = ? where `loan-id` = ?;";

    private static final int INCORRECT_ID = -1;

    private static final Logger logger = LoggerFactory.getLogger(MySQLLibraryDAO.class);

    @Override
    public void closeOutdatedReservations() throws DAOException {
        Connection connection = null;
        try {
            List<DeliveryType> reservations = getAllReservations();
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            for (DeliveryType deliveryType :
                    reservations) {
                LoanType reservation = deliveryType.getLoanType();
                LocalDate startDate = reservation.getStartDate();
                LocalDate now = LocalDate.now();
                int duration = reservation.getDuration();
                LocalDate endDate = startDate.plusDays(duration);
                if (now.isAfter(endDate)) {
                    logger.debug("closeOutdatedReservations reservation to delete = {}", deliveryType);
                    deleteReservation(connection, reservation.getLoanTypeId());
                    statusUpdate(connection, UPDATE_COPY_STATUS, STATUS_ACTIVE, reservation.getCopyId());
                }
            }

            connection.commit();
            connection.setAutoCommit(true);


        } catch (SQLException e) {
            try {
                logger.debug("closeOutdatedReservations trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("closeOutdatedReservations rollback failed");
                throw new DAOException("Impossible to rollback method closeOutdatedReservations", e);
            }
            logger.error("SQLException in LibraryDAOImpl method closeOutdatedReservations()", e);
            throw new DAOException("SQLException in method closeOutdatedReservations", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method closeOutdatedReservations()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public void returnMedia(int copyId, int loanId) throws DAOException {
        Connection connection = null;
        try {
            logger.debug("returnMedia copyId = {} loanId = {}", copyId, loanId);

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            statusUpdate(connection, UPDATE_COPY_STATUS, STATUS_ACTIVE, copyId);

            statusUpdate(connection, UPDATE_LOAN_STATUS, STATUS_DELETED, loanId);

            connection.commit();
            connection.setAutoCommit(true);


        } catch (SQLException e) {
            try {
                logger.debug("returnMedia trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("returnMedia rollback failed");
                throw new DAOException("Impossible to rollback method returnMedia", e);
            }
            logger.error("SQLException in LibraryDAOImpl method returnMedia()", e);
            throw new DAOException("SQLException in method returnMedia", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method returnMedia()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    private List<LoanType> getLoanTypesForMedia(Connection connection, int mediaId, String query, String type) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("getLoanTypesForMedia mediaId = {} query = {} type = {}", mediaId, query, type);

            List<LoanType> loanTypes = new ArrayList<>();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mediaId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (type.equals(LOAN_TYPE_LOAN)) {
                    loanTypes.add(buildLoan(connection, resultSet));
                } else {
                    loanTypes.add(buildReservation(connection, resultSet));
                }
            }

            logger.debug("getLoanTypesForMedia loanTypes = {}", loanTypes);

            return loanTypes;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getLoanTypesForMedia()", e);
            throw new DAOException("SQL error", e);
        } finally {
            connectionPool.closeStatement(preparedStatement, resultSet);
        }

    }

    @Override
    public List<LoanType> getLoansForMedia(int mediaId) throws DAOException {
        return getLoanTypesForMedia(mediaId, GET_LOANS_FOR_MEDIA, LOAN_TYPE_LOAN);
    }

    private List<LoanType> getLoanTypesForMedia(int mediaId, String query, String type) throws DAOException {
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            return getLoanTypesForMedia(connection, mediaId, query, type);

        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getLoanTypesForMedia()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection);
        }

    }

    private void setMediaStatement(Connection connection, PreparedStatement preparedStatement, MediaDetail mediaDetail) throws SQLException {
        int languageId = getOrCreateTableId(connection, GET_LANGUAGE_BY_NAME, ADD_LANGUAGE, mediaDetail.getLanguage(), LANGUAGE_ID);
        int publisherId = getOrCreateTableId(connection, GET_PUBLISHER_BY_NAME, ADD_PUBLISHER, mediaDetail.getPublisher(), PUBLISHER_ID);
        int materialTypeId = getOrCreateTableId(connection, GET_MATERIAL_TYPE_BY_NAME, ADD_MATERIAL_TYPE, mediaDetail.getFormat(), MATERIAL_TYPE_ID);


        preparedStatement.setString(1, mediaDetail.getTitle());
        preparedStatement.setString(2, mediaDetail.getSummary());
        preparedStatement.setDouble(3, mediaDetail.getPrice());
        preparedStatement.setString(4, mediaDetail.getiSBN());
        preparedStatement.setString(5, mediaDetail.getPicture());
        preparedStatement.setInt(6, publisherId);
        preparedStatement.setInt(7, materialTypeId);
        preparedStatement.setInt(8, languageId);
        preparedStatement.setString(9, mediaDetail.getRestriction());
    }

    private int addMedia(Connection connection, MediaDetail mediaDetail) throws SQLException {

        int newMediaId;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("addMedia mediaDetail = {}", mediaDetail);

            preparedStatement = connection.prepareStatement(ADD_MEDIA);

            setMediaStatement(connection, preparedStatement, mediaDetail);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(GET_LAST_MEDIA_ID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            newMediaId = resultSet.getInt(MEDIA_ID);

            addAuthorToMedia(connection, mediaDetail.getAuthors(), newMediaId);
            addGenreToMedia(connection, mediaDetail.getGenres(), newMediaId);

            for (int i = 0; i < mediaDetail.getTotalCopies(); i++) {
                preparedStatement = connection.prepareStatement(ADD_COPY);
                preparedStatement.setInt(1, newMediaId);
                preparedStatement.executeUpdate();
            }


        } finally {
            connectionPool.closeStatement(preparedStatement, resultSet);
        }
        return newMediaId;
    }

    @Override
    public void editMedia(MediaDetail mediaDetail) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            logger.debug("editMedia mediaDetail = {}", mediaDetail);

            connection =connectionPool.takeConnection();

            int targetTotalCopies = mediaDetail.getTotalCopies();

            List<LoanType> loans = getLoanTypesForMedia(connection, mediaDetail.getMediaID(), GET_LOANS_FOR_MEDIA, LOAN_TYPE_LOAN);
            int numberOfLoans = loans.size();
            if (numberOfLoans > targetTotalCopies) {
                logger.error("DAOException in LibraryDAOImpl method editMedia() - There are less total copies than total loans");
                throw new DAOException("There are less total copies than total loans");
            }

            connection.setAutoCommit(false);

            List<LoanType> reservations = getLoanTypesForMedia(connection, mediaDetail.getMediaID(), GET_RESERVATIONS_FOR_MEDIA, LOAN_TYPE_RESERVATION);
            int numberOfReservations = reservations.size();
            int numberOfReservationsToDelete = numberOfReservations - (targetTotalCopies - numberOfLoans);

            if (numberOfReservationsToDelete > 0) {
                for (int i = 0; i < numberOfReservationsToDelete; i++) {
                    deleteReservation(connection, reservations.get(i).getLoanTypeId());
                }
            }

            preparedStatement = connection.prepareStatement(EDIT_MEDIA);
            setMediaStatement(connection, preparedStatement, mediaDetail);
            preparedStatement.setInt(10, mediaDetail.getMediaID());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_AUTHORS_FROM_MEDIA);
            preparedStatement.setInt(1, mediaDetail.getMediaID());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_GENRES_FROM_MEDIA);
            preparedStatement.setInt(1, mediaDetail.getMediaID());
            preparedStatement.executeUpdate();

            addAuthorToMedia(connection, mediaDetail.getAuthors(), mediaDetail.getMediaID());
            addGenreToMedia(connection, mediaDetail.getGenres(), mediaDetail.getMediaID());


            preparedStatement = connection.prepareStatement(GET_COPIES_FOR_MEDIA_COUNT);
            preparedStatement.setInt(1, mediaDetail.getMediaID());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int totalCopies = resultSet.getInt(TOTAL_ITEMS);

            int copiesDiff = targetTotalCopies - totalCopies;
            if (copiesDiff < 0) {

                preparedStatement = connection.prepareStatement(GET_AVAILABLE_COPIES_FOR_MEDIA);
                preparedStatement.setInt(1, mediaDetail.getMediaID());
                resultSet = preparedStatement.executeQuery();
                List<Integer> activeCopies = new ArrayList<>();

                while (resultSet.next()) {
                    activeCopies.add(resultSet.getInt(COPIES_COPY_ID));
                }

                for (int i = 0; i < Math.abs(copiesDiff); i++) {
                    statusUpdate(connection, UPDATE_COPY_STATUS, STATUS_DELETED, activeCopies.get(i));
                }
            }
            if (copiesDiff > 0) {

                for (int i = 0; i < copiesDiff; i++) {
                    preparedStatement = connection.prepareStatement(ADD_COPY);
                    preparedStatement.setInt(1, mediaDetail.getMediaID());
                    preparedStatement.executeUpdate();
                }

            }

            connection.commit();
            connection.setAutoCommit(true);


        } catch (SQLException e) {
            try {
                logger.debug("editMedia trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("editMedia rollback failed");
                throw new DAOException("Impossible to rollback method editMedia", e);
            }
            logger.error("SQLException in LibraryDAOImpl method editMedia()", e);
            throw new DAOException("SQLException in method editMedia", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method editMedia()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private void addAuthorToMedia(Connection connection, List<Author> authors, int mediaId) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            for (Author author :
                    authors) {
                int authorId = getOrCreateTableId(connection, GET_AUTHOR_BY_NAME, ADD_AUTHOR, author.getFullName(), AUTHOR_ID);
                preparedStatement = connection.prepareStatement(ADD_AUTHOR_TO_MEDIA);
                preparedStatement.setInt(1, mediaId);
                preparedStatement.setInt(2, authorId);
                preparedStatement.executeUpdate();
            }
        } finally {
            connectionPool.closeStatement(preparedStatement);
        }
    }

    private void addGenreToMedia(Connection connection, List<Genre> genres, int mediaId) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            for (Genre genre :
                    genres) {
                int genreId = getOrCreateTableId(connection, GET_GENRE_BY_NAME, ADD_GENRE, genre.getGenre(), GENRE_ID);
                preparedStatement = connection.prepareStatement(ADD_GENRE_TO_MEDIA);
                preparedStatement.setInt(1, mediaId);
                preparedStatement.setInt(2, genreId);
                preparedStatement.executeUpdate();
            }
        } finally {
            connectionPool.closeStatement(preparedStatement);
        }
    }


    private int getTableId(Connection connection, String statement, String parameter, String columnName) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, parameter);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(columnName);
            } else {
                return INCORRECT_ID;
            }
        } finally {
            connectionPool.closeStatement(preparedStatement, resultSet);
        }
    }

    private void addColumn(Connection connection, String statement, String parameter) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, parameter);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.closeStatement(preparedStatement);
        }
    }

    private int getOrCreateTableId(Connection connection, String getStatement, String createStatement, String parameter, String columnName) throws SQLException {
        int tableId;
        tableId = getTableId(connection, getStatement, parameter, columnName);
        if (tableId == INCORRECT_ID) {
            addColumn(connection, createStatement, parameter);
        }
        return getTableId(connection, getStatement, parameter, columnName);
    }

    @Override
    public int addMedia(MediaDetail mediaDetail) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            logger.debug("addMedia mediaDetail = {}", mediaDetail);

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            int newMediaId = addMedia(connection, mediaDetail);

            for (int i = 0; i < mediaDetail.getTotalCopies(); i++) {
                preparedStatement = connection.prepareStatement(ADD_COPY);
                preparedStatement.setInt(1, newMediaId);
                preparedStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            return newMediaId;

        } catch (SQLException e) {
            try {
                logger.debug("addMedia trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("addMedia rollback failed");
                throw new DAOException("Impossible to rollback method addMedia", e);
            }
            logger.error("SQLException in LibraryDAOImpl method addMedia()", e);
            throw new DAOException("SQLException in method addMedia", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method addMedia()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }


    private void statusUpdate(Connection connection, String query, String status, int itemId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, itemId);
        preparedStatement.executeUpdate();
        connectionPool.closeStatement(preparedStatement);

    }

    private void addLoanOrReservation(Connection connection, String query, int daysDuration, int userId, int copyId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setInt(2, daysDuration);
        preparedStatement.setInt(3, userId);
        preparedStatement.setInt(4, copyId);
        preparedStatement.executeUpdate();
        connectionPool.closeStatement(preparedStatement);

    }

    @Override
    public void giveOutCopy(int userId, int copyId, int reservationId, int daysDuration) throws DAOException {
        Connection connection = null;
        try {

            logger.debug("giveOutCopy userId = {} copyId = {} reservationId = {} daysDuration = {}", userId, copyId, reservationId, daysDuration);

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            statusUpdate(connection, UPDATE_COPY_STATUS, STATUS_LOANED, copyId);

            addLoanOrReservation(connection, ADD_LOAN, daysDuration, userId, copyId);

            statusUpdate(connection, UPDATE_RESERVATION_STATUS, STATUS_DELETED, reservationId);

            connection.commit();
            connection.setAutoCommit(true);


        } catch (SQLException e) {
            try {
                logger.debug("giveOutCopy trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("giveOutCopy rollback failed");
                throw new DAOException("Impossible to rollback method giveOutCopy", e);
            }
            logger.error("ConnectionPoolException in LibraryDAOImpl method giveOutCopy()", e);
            throw new DAOException("SQLException in method giveOutCopy", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method giveOutCopy()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection);

        }
    }

    @Override
    public void reserve(int daysDuration, int userId, int mediaId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("reserve userId = {} mediaId = {} daysDuration = {}", userId, mediaId, daysDuration);

            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(GET_AVAILABLE_COPIES_FOR_MEDIA);
            preparedStatement.setInt(1, mediaId);
            resultSet = preparedStatement.executeQuery();
            int copyId = -1;
            if (resultSet.next()) {
                copyId = resultSet.getInt(COPIES_COPY_ID);
            }
            if (copyId > 0) {
                connection.setAutoCommit(false);

                statusUpdate(connection, UPDATE_COPY_STATUS, STATUS_RESERVED, copyId);

                addLoanOrReservation(connection, ADD_RESERVATION, daysDuration, userId, copyId);

                connection.commit();
                connection.setAutoCommit(true);
            } else {
                logger.error("DAOException in LibraryDAOImpl method reserve() - No available copies");
                throw new DAOException("No available copies");
            }

        } catch (SQLException e) {
            try {
                logger.debug("reserve trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("reserve rollback failed");
                throw new DAOException("Impossible to rollback method reserve", e);
            }
            logger.error("SQLException in LibraryDAOImpl method reserve()", e);
            throw new DAOException("SQLException in method reserve", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method reserve()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void deleteReservation(int reservationId) throws DAOException {
        Connection connection = null;

        try {

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            deleteReservation(connection, reservationId);

            connection.commit();
            connection.setAutoCommit(true);


        } catch (SQLException e) {
            try {
                logger.debug("deleteReservation trying to rollback");
                connection.rollback();
            } catch (SQLException sqlException) {
                logger.debug("deleteReservation rollback failed");
                throw new DAOException("Impossible to rollback method deleteReservation", e);
            }
            logger.error("SQLException in LibraryDAOImpl method deleteReservation()", e);
            throw new DAOException("SQLException in method deleteReservation", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method deleteReservation()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    private void deleteReservation(Connection connection, int reservationId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("deleteReservation reservationId = {}", reservationId);

            preparedStatement = connection.prepareStatement(GET_RESERVATION_BY_ID);
            preparedStatement.setInt(1, reservationId);
            resultSet = preparedStatement.executeQuery();

            int copyId;

            resultSet.next();
            copyId = resultSet.getInt(COPIES_COPY_ID);

            statusUpdate(connection, UPDATE_COPY_STATUS, STATUS_ACTIVE, copyId);

            statusUpdate(connection, UPDATE_RESERVATION_STATUS, STATUS_DELETED, reservationId);

        } finally {
            connectionPool.closeStatement(preparedStatement, resultSet);
        }
    }

    @Override
    public MediaPage getMediaPage(int page, int itemsPerPage, String search) throws DAOException {
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("getMediaPage page = {} itemsPerPage = {} search = {}", page, itemsPerPage, search);

            connection = connectionPool.takeConnection();

            if (search == null) {
                preparedStatement = connection.prepareStatement(GET_MEDIA_TOTAL_ITEMS);
            } else {
                preparedStatement = connection.prepareStatement(GET_MEDIA_TOTAL_ITEMS_WITH_SEARCH);
                preparedStatement.setString(1, "%" + search + "%");
                preparedStatement.setString(2, "%" + search + "%");
            }

            resultSet = preparedStatement.executeQuery();
            int totalItems = 0;
            if (resultSet.next()) {
                totalItems = resultSet.getInt(TOTAL_ITEMS);
            }
            int startItem = (page - 1) * itemsPerPage;

            if (search == null) {
                preparedStatement = connection.prepareStatement(GET_MEDIA_TYPES_PAGE);
                preparedStatement.setInt(1, startItem);
                preparedStatement.setInt(2, itemsPerPage);
            } else {
                preparedStatement = connection.prepareStatement(GET_MEDIA_PAGE_WITH_SEARCH);
                preparedStatement.setString(1, "%" + search + "%");
                preparedStatement.setString(2, "%" + search + "%");
                preparedStatement.setInt(3, startItem);
                preparedStatement.setInt(4, itemsPerPage);
            }

            resultSet = preparedStatement.executeQuery();

            List<MediaDisplay> media = new ArrayList<>();
            while (resultSet.next()) {
                media.add(
                        buildMediaDisplay(resultSet));

            }

            MediaPage mediaPage = new MediaPage();
            mediaPage.setSearch(search);
            mediaPage.setPage(page);
            mediaPage.setItemsPerPage(itemsPerPage);
            mediaPage.setTotalItems(totalItems);
            mediaPage.setMediaDisplay(media);

            return mediaPage;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getMediaPage()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getMediaPage()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }


    @Override
    public List<DeliveryType> getAllReservations() throws DAOException {
        return getReservations(null);
    }

    @Override
    public List<DeliveryType> searchReservations(String searchStr) throws DAOException {
        return getReservations(searchStr);
    }

    private List<DeliveryType> getReservations(String searchStr) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("getReservations  searchStr = {}", searchStr);

            connection = connectionPool.takeConnection();

            List<DeliveryType> deliveryTypes = new ArrayList<>();

            if (searchStr != null) {
                preparedStatement = connection.prepareStatement(SEARCH_RESERVATIONS);
                preparedStatement.setString(1, searchStr);
            } else {
                preparedStatement = connection.prepareStatement(GET_ALL_RESERVATIONS);

            }

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                deliveryTypes.add(buildDeliveryReservation(connection, resultSet));
            }

            return deliveryTypes;

        } catch (SQLException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getReservations()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getReservations()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

    }

    @Override
    public List<DeliveryType> getAllLoans() throws DAOException {
        return getLoans(null);
    }

    @Override
    public List<DeliveryType> searchLoans(String searchStr) throws DAOException {
        return getLoans(searchStr);
    }

    private List<DeliveryType> getLoans(String searchStr) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("getLoans  searchStr = {}", searchStr);

            connection = connectionPool.takeConnection();

            List<DeliveryType> deliveryTypes = new ArrayList<>();

            if (searchStr != null) {
                preparedStatement = connection.prepareStatement(SEARCH_LOANS);
                preparedStatement.setString(1, searchStr);
            } else {
                preparedStatement = connection.prepareStatement(GET_ALL_LOANS);

            }

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                deliveryTypes.add(buildDeliveryLoans(connection, resultSet));
            }

            return deliveryTypes;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getLoans()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getLoans()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

    }


    @Override
    public List<LoanType> getUserReservations(int userId) throws DAOException {
        return getUserLoanTypes(userId, LOAN_TYPE_RESERVATION);
    }

    @Override
    public List<LoanType> getUserLoans(int userId) throws DAOException {
        return getUserLoanTypes(userId, LOAN_TYPE_LOAN);
    }

    private List<LoanType> getUserLoanTypes(int userId, String type) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            logger.debug("getUserLoanTypes  userId = {} type = {}", userId, type);

            connection = connectionPool.takeConnection();

            List<LoanType> loanTypes = new ArrayList<>();

            if (type.equals(LOAN_TYPE_RESERVATION)) {
                preparedStatement = connection.prepareStatement(GET_RESERVATIONS_BY_USER_ID);
                preparedStatement.setInt(1, userId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    loanTypes.add(buildReservation(connection, resultSet));
                }
            } else if (type.equals(LOAN_TYPE_LOAN)) {
                preparedStatement = connection.prepareStatement(GET_LOANS_BY_USER_ID);
                preparedStatement.setInt(1, userId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    loanTypes.add(buildLoan(connection, resultSet));
                }
            }
            return loanTypes;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getUserLoanTypes()", e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getUserLoanTypes()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private MediaDetail getMediaDetail(Connection connection, int mediaId) throws DAOException {
        PreparedStatement mediaDetailsStatement = null;
        ResultSet mediaDetailsSet = null;

        PreparedStatement copiesStatement = null;
        ResultSet copiesSet = null;

        try {

            logger.debug("getUserLoanTypes  mediaId = {}", mediaId);

            mediaDetailsStatement = connection.prepareStatement(GET_MEDIA_DETAILS);
            mediaDetailsStatement.setInt(1, mediaId);
            mediaDetailsSet = mediaDetailsStatement.executeQuery();

            copiesStatement = connection.prepareStatement(GET_COPIES_FOR_MEDIA);
            copiesStatement.setInt(1, mediaId);
            copiesSet = copiesStatement.executeQuery();

            int totalCopies = 0;
            int loanedCopies = 0;
            int reservedCopies = 0;

            while (copiesSet.next()) {
                totalCopies++;
                String status = copiesSet.getString(FIELD_STATUS);
                if (status.equalsIgnoreCase(COPIES_STATUS_LOANED)) {
                    loanedCopies++;
                }
                if (status.equalsIgnoreCase(COPIES_STATUS_RESERVED)) {
                    reservedCopies++;
                }
            }

            MediaDetail mediaDetail = null;
            if (mediaDetailsSet.next()) {
                mediaDetail = buildMediaDetail(connection, totalCopies, loanedCopies, reservedCopies, mediaDetailsSet);

            }

            return mediaDetail;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getMediaDetail()", e);
            throw new DAOException("SQL error", e);
        } finally {
            connectionPool.closeStatement(copiesStatement, copiesSet);
            connectionPool.closeStatement(mediaDetailsStatement, mediaDetailsSet);

        }

    }

    @Override
    public MediaDetail getMediaDetail(int mediaId) throws DAOException {
        Connection connection = null;

        try {
            connection =connectionPool.takeConnection();

            return getMediaDetail(connection, mediaId);

        } catch (ConnectionPoolException e) {
            logger.error("ConnectionPoolException in LibraryDAOImpl method getMediaDetail()", e);
            throw new DAOException("ConnectionPool error", e);
        } finally {
            connectionPool.closeConnection(connection);
        }

    }

    private List<Author> getAuthorsByMediaID(Connection connection, int mediaID) throws DAOException {

        PreparedStatement authorPreparedStatement = null;
        ResultSet authorResultSet = null;

        try {
            List<Author> authors = new ArrayList<>();
            authorPreparedStatement = connection.prepareStatement(GET_AUTHORS_FOR_MEDIA);
            authorPreparedStatement.setInt(1, mediaID);
            authorResultSet = authorPreparedStatement.executeQuery();

            while (authorResultSet.next()) {
                authors.add(new Author(
                        authorResultSet.getInt(AUTHORS_AUTHOR_ID),
                        authorResultSet.getString(AUTHORS_AUTHOR_FULL_NAME)
                ));
            }

            return authors;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getAuthorsByMediaID()", e);
            throw new DAOException("SQL error", e);
        } finally {
            connectionPool.closeStatement(authorPreparedStatement, authorResultSet);
        }
    }

    private List<Genre> getGenresByMediaID(Connection connection, int mediaID) throws DAOException {
        PreparedStatement genrePreparedStatement = null;
        ResultSet genreResultSet = null;

        try {

            List<Genre> genres = new ArrayList<>();
            genrePreparedStatement = connection.prepareStatement(GET_GENRES_FOR_MEDIA);
            genrePreparedStatement.setInt(1, mediaID);
            genreResultSet = genrePreparedStatement.executeQuery();

            while (genreResultSet.next()) {
                genres.add(new Genre(
                        genreResultSet.getInt(GENRES_GENRE_ID),
                        genreResultSet.getString(GENRES_GENRE)
                ));
            }

            return genres;

        } catch (SQLException e) {
            logger.error("SQLException in LibraryDAOImpl method getGenresByMediaID()", e);
            throw new DAOException("SQL error", e);
        } finally {
            connectionPool.closeStatement(genrePreparedStatement, genreResultSet);
        }
    }

    private DeliveryType buildDeliveryReservation(Connection connection, ResultSet deliveryReservationSet) throws SQLException, DAOException {
        DeliveryType deliveryType = new DeliveryType();
        LoanType loanType = buildReservation(connection, deliveryReservationSet);
        deliveryType.setLoanType(loanType);
        deliveryType.setUser(DAOFactory.getInstance().getDaoUtil().getUserById(connection, loanType.getUserId()));
        return deliveryType;
    }

    private DeliveryType buildDeliveryLoans(Connection connection, ResultSet deliveryLoanSet) throws SQLException, DAOException {
        DeliveryType deliveryType = new DeliveryType();
        LoanType loanType = buildLoan(connection, deliveryLoanSet);
        deliveryType.setLoanType(loanType);
        deliveryType.setUser(DAOFactory.getInstance().getDaoUtil().getUserById(connection, loanType.getUserId()));
        return deliveryType;
    }

    private LoanType buildLoan(Connection connection, ResultSet loanSet) throws SQLException, DAOException {
        LoanType loanType = new LoanType();
        setLoanType(loanSet, LOANS_LOAN_ID, loanType);
        if (loanSet.getDate(LOAN_TYPE_END_DATE) == null) {
            loanType.setEndDate(loanType.getStartDate().plusDays(loanType.getDuration()));
        } else {
            loanType.setEndDate(loanSet.getDate(LOAN_TYPE_END_DATE).toLocalDate());
        }
        loanType.setMediaDetail(getMediaDetail(connection, loanSet.getInt(MEDIA_ID)));

        return loanType;
    }


    private LoanType buildReservation(Connection connection, ResultSet reservationSet) throws SQLException, DAOException {
        LoanType loanType = new LoanType();
        setLoanType(reservationSet, RESERVATIONS_RESERVATION_ID, loanType);
        loanType.setEndDate(loanType.getStartDate().plusDays(loanType.getDuration()));
        loanType.setStatus(reservationSet.getString(FIELD_STATUS));
        loanType.setMediaDetail(getMediaDetail(connection, reservationSet.getInt(MEDIA_ID)));

        return loanType;
    }

    private void setLoanType(ResultSet loanSet, String loanTypeId, LoanType loanType) throws SQLException {
        loanType.setUserId(loanSet.getInt(LOAN_TYPE_USER_ID));
        loanType.setLoanTypeId(loanSet.getInt(loanTypeId));
        loanType.setCopyId(loanSet.getInt(LOAN_TYPE_COPY_ID));
        loanType.setDuration(loanSet.getInt(LOAN_TYPE_DURATION));
        loanType.setStartDate(loanSet.getDate(LOAN_TYPE_START_DATE).toLocalDate());
    }

    private MediaDisplay buildMediaDisplay(ResultSet resultSet) throws SQLException {
        return new MediaDisplay(
                resultSet.getInt(MEDIA_ID),
                resultSet.getString(MEDIA_PICTURE),
                resultSet.getString(MEDIA_MATERIAL_TYPE),
                resultSet.getString(MEDIA_TITLE),
                resultSet.getString(MEDIA_SUMMARY),
                resultSet.getString(MEDIA_PUBLISHER),
                resultSet.getString(MEDIA_LANGUAGE));

    }

    private MediaDetail buildMediaDetail(Connection connection, int totalCopies, int loanedCopies, int reservedCopies, ResultSet mediaDetailsSet) throws SQLException, DAOException {
        int availableCopies = totalCopies - loanedCopies - reservedCopies;
        return new MediaDetail(
                mediaDetailsSet.getInt(MEDIA_ID),
                totalCopies,
                availableCopies,
                reservedCopies,
                loanedCopies,
                mediaDetailsSet.getDouble(MEDIA_PRICE),
                mediaDetailsSet.getString(MEDIA_TITLE),
                mediaDetailsSet.getString(MEDIA_SUMMARY),
                mediaDetailsSet.getString(MEDIA_ISBN),
                mediaDetailsSet.getString(MEDIA_PICTURE),
                mediaDetailsSet.getString(MEDIA_PUBLISHER),
                mediaDetailsSet.getString(MEDIA_MATERIAL_TYPE),
                mediaDetailsSet.getString(MEDIA_LANGUAGE),
                mediaDetailsSet.getString(MEDIA_RESTRICTION),
                getAuthorsByMediaID(connection, mediaDetailsSet.getInt(MEDIA_ID)),
                getGenresByMediaID(connection, mediaDetailsSet.getInt(MEDIA_ID)));

    }

}

