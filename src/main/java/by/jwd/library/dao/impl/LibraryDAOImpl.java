package by.jwd.library.dao.impl;

import by.jwd.library.bean.*;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.LibraryDAO;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.util.DAOUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAOImpl implements LibraryDAO {
    // TODO: 22.06.2020 refactor
    private static final String GET_MEDIA_TYPES_PAGE = "SELECT * FROM `media-types`\n" +
            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
            "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
            "inner join media on `media-types`.`media-id` = `media`.`media-id` \n" +
            "where \n" +
            "`media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' \n" +
            "order by `media-type-id` desc LIMIT ?,?";

    private static final String GET_MEDIA_TYPES_PAGE_WITH_SEARCH = "SELECT  * FROM `media-types`\n" +
            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
            "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
            "inner join media on `media-types`.`media-id` = `media`.`media-id` \n" +
            "where `media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' and media.title like ?" +
            "or `media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' and `material-types`.`material-type` like ? \n" +
            "order by `media-type-id` desc LIMIT ?,?; ";

    private static final String GET_MEDIA_TYPES_TOTAL_ITEMS_WITH_SEARCH = "SELECT  *, count(*) `total-items` FROM `media-types`\n" +
            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
            "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
            "inner join media on `media-types`.`media-id` = `media`.`media-id` \n" +
            "where `media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' and media.title like ?" +
            "or `media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' and `material-types`.`material-type` like ? \n" +
            "order by `media-type-id`; ";

    private static final String GET_ALL_RESERVATIONS = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted';";

    private static final String SEARCH_RESERVATIONS = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "inner join users on reservations.`user-id` = users.`user-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted' and users.status != 'deleted' and `passport-id` = ? ;";


    private static final String GET_RESERVATION_BY_ID = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted' and `reservation-id`  = ?;";

    private static final String GET_RESERVATIONS_BY_USER_ID = "SELECT * FROM reservations\n" +
            "inner join copies on reservations.`copy-id` = copies.`copy-id` \n" +
            "where reservations.status = 'Active' and copies.status != 'deleted' and `user-id`  = ?;";

    private static final String GET_LOANS_BY_USER_ID = "SELECT * FROM loans\n" +
            "inner join copies on loans.`copy-id` = copies.`copy-id` \n" +
            "where loans.status = 'Active' and copies.status != 'deleted' and `user-id`  = ?;";


    private static final String GET_MEDIA_TYPE_DETAILS = "SELECT * FROM `media-types` \n" +
            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
            "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
            "inner join media on `media-types`.`media-id` = `media`.`media-id` \n" +
            "where `media-types`.status != 'deleted' and `material-types`.status != 'deleted' \n" +
            "and publishers.status != 'deleted' and languages.status != 'deleted' and media.`media-id` != 'deleted' and `media-types`.`media-type-id` = ?";

    private static final String GET_LAST_COPY_ID = "SELECT `copy-id` FROM copies order by `copy-id` desc limit 1;";
    private static final String GET_MEDIA_TYPES_TOTAL_ITEMS = "SELECT * , count(*) `total-items` FROM `media-types` where status != 'deleted';";

    //    private static final String GET_MEDIA_TYPES_COUNT = "SELECT `media-type-id` FROM `media-types` order by `media-type-id` desc limit 1;";
    private static final String GET_AUTHORS_FOR_MEDIA = "SELECT * FROM `media-have-authors` inner join authors on `media-have-authors`.`author-id` = authors.`author-id` " +
            "where `media-id` = ? and `media-have-authors`.status != 'deleted' and authors.status != 'deleted' ;";
    private static final String GET_GENRES_FOR_MEDIA = "SELECT * FROM `media-have-genres` inner join genres on `media-have-genres`.`genre-id` = genres.`genre-id` " +
            "where `media-id` = ? and `media-have-genres`.status != 'deleted' and genres.status != 'deleted' ;";

    private static final String GET_COPIES_FOR_MEDIA_TYPE = "SELECT * FROM copies where `media-type-id` = ? and status != 'Deleted';";

    private static final String MEDIA_TITLE = "title";
    private static final String MEDIA_SUMMARY = "summary";

    private static final String MEDIA_TYPES_TOTAL_ITEMS = "total-items";
    private static final String MEDIA_TYPES_MEDIA_ID = "media-id";
    private static final String MEDIA_TYPES_MEDIA_TYPE_ID = "media-type-id";
    private static final String MEDIA_TYPES_PRICE = "price";
    private static final String MEDIA_TYPES_ISBN = "isbn";
    private static final String MEDIA_TYPES_MATERIAL_TYPE = "material-type";
    private static final String MEDIA_TYPES_PUBLISHER = "publisher";
    private static final String MEDIA_TYPES_LANGUAGE = "language";
    private static final String MEDIA_TYPES_PICTURE = "picture";
    private static final String MEDIA_TYPES_RESTRICTION = "restriction";


    private static final String COPIES_COPY_ID = "copy-id";
    private static final String FIELD_STATUS = "status";
    private static final String COPIES_STATUS_LOANED = "loaned";
    private static final String COPIES_STATUS_RESERVED = "reserved";

    private static final String AUTHORS_AUTHOR_ID = "author-id";
    private static final String AUTHORS_AUTHOR_FULL_NAME = "full-name";
    private static final String AUTHORS_AUTHOR_PEN_NAME = "pen-name";

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

    private static final String ADD_USER =
            "insert into users(name,email,login,password,`passport-id`,role, status) values(?,?,?,?,?,?,?)";

    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_LOANED = "Loaned";
    private static final String STATUS_RESERVED = "Reserved";
    private static final String STATUS_DELETED = "Deleted";

    private static final String ADD_RESERVATION =
            "insert into reservations(`start-date`,duration,status,`user-id`,`copy-id`) values(?,?,?,?,?)";

    private static final String GET_AVAILABLE_COPIES_FOR_MEDIA_TYPE =
            "SELECT * FROM library.copies where status = 'Active' and `media-type-id` = ? ;";

    private static final String UPDATE_COPY_STATUS =
            "update copies set status = ? where `copy-id` = ?;";

    private static final String UPDATE_RESERVATION_STATUS =
            "update reservations set status = ? where `reservation-id` = ?;";

    @Override
    public void reserveCopy(int copyId) throws DAOException {
        setCopyStatus(copyId, STATUS_RESERVED);
    }

    @Override
    public void cancelCopyReservation(int copyId) throws DAOException {
        setCopyStatus(copyId, STATUS_ACTIVE);
    }


    @Override
    public void deleteReservation(int reservationId) throws DAOException {
        setReservationStatus(reservationId,STATUS_DELETED);

    }

    private void setReservationStatus(int reservationId, String status) throws DAOException {
        statusUpdate(reservationId, status, UPDATE_RESERVATION_STATUS);
    }

    private void statusUpdate(int reservationId, String status, String updateReservationStatus) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(updateReservationStatus);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, reservationId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    private void setCopyStatus(int copyId, String status) throws DAOException {
        statusUpdate(copyId, status, UPDATE_COPY_STATUS);
    }


    @Override
    public int getAvailableCopyId(int mediaTypeId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(GET_AVAILABLE_COPIES_FOR_MEDIA_TYPE);
            preparedStatement.setInt(1, mediaTypeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(COPIES_COPY_ID);
            }
            return -1;
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(resultSet);
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    @Override
    public void addReservation(int daysDuration, int userId, int copyId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(ADD_RESERVATION);
            preparedStatement.setDate(1,new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setInt(2, daysDuration);
            preparedStatement.setString(3, STATUS_ACTIVE);
            preparedStatement.setInt(4, userId);
            preparedStatement.setInt(5, copyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closePreparedStatement(preparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    @Override
    public MediaPage getMediaTypePage(int page, int itemsPerPage, String search) throws DAOException {
        Connection connection = null;

        PreparedStatement totalMediaTypesStatement = null;
        ResultSet totalMediaTypesSet = null;

        PreparedStatement mediaTypesPageStatement = null;
        ResultSet mediaTypesPageSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            if (search == null) {
                totalMediaTypesStatement = connection.prepareStatement(GET_MEDIA_TYPES_TOTAL_ITEMS);
            } else {
                totalMediaTypesStatement = connection.prepareStatement(GET_MEDIA_TYPES_TOTAL_ITEMS_WITH_SEARCH);
                totalMediaTypesStatement.setString(1, "%" + search + "%");
                totalMediaTypesStatement.setString(2, "%" + search + "%");
            }

            totalMediaTypesSet = totalMediaTypesStatement.executeQuery();
            int totalItems = 0;
            if (totalMediaTypesSet.next()) {
                totalItems = totalMediaTypesSet.getInt(MEDIA_TYPES_TOTAL_ITEMS);
            }
            int startItem = (page - 1) * itemsPerPage;

            if (search == null) {
                mediaTypesPageStatement = connection.prepareStatement(GET_MEDIA_TYPES_PAGE);
                mediaTypesPageStatement.setInt(1, startItem);
                mediaTypesPageStatement.setInt(2, itemsPerPage);
            } else {
                mediaTypesPageStatement = connection.prepareStatement(GET_MEDIA_TYPES_PAGE_WITH_SEARCH);
                mediaTypesPageStatement.setString(1, "%" + search + "%");
                mediaTypesPageStatement.setString(2, "%" + search + "%");
                mediaTypesPageStatement.setInt(3, startItem);
                mediaTypesPageStatement.setInt(4, itemsPerPage);
            }

            mediaTypesPageSet = mediaTypesPageStatement.executeQuery();

            List<MediaDisplay> mediaTypes = new ArrayList<>();
            while (mediaTypesPageSet.next()) {
                mediaTypes.add(
                        new MediaDisplay(
                                mediaTypesPageSet.getInt(MEDIA_TYPES_MEDIA_TYPE_ID),
                                mediaTypesPageSet.getString(MEDIA_TYPES_PICTURE),
                                mediaTypesPageSet.getString(MEDIA_TYPES_MATERIAL_TYPE),
                                mediaTypesPageSet.getString(MEDIA_TITLE),
                                mediaTypesPageSet.getString(MEDIA_SUMMARY),
                                mediaTypesPageSet.getString(MEDIA_TYPES_PUBLISHER),
                                mediaTypesPageSet.getString(MEDIA_TYPES_LANGUAGE)));
            }

            MediaPage mediaPage = new MediaPage();
            mediaPage.setSearch(search);
            mediaPage.setPage(page);
            mediaPage.setItemsPerPage(itemsPerPage);
            mediaPage.setTotalItems(totalItems);
            mediaPage.setMediaDisplay(mediaTypes);

            return mediaPage;

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(mediaTypesPageSet);
            DAOUtil.closeResultSet(totalMediaTypesSet);
            DAOUtil.closePreparedStatement(mediaTypesPageStatement);
            DAOUtil.closePreparedStatement(totalMediaTypesStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    @Override
    public LoanType getReservationById(int reservationId) throws DAOException {
        Connection connection = null;
        PreparedStatement reservationStatement = null;
        ResultSet reservationSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            reservationStatement = connection.prepareStatement(GET_RESERVATION_BY_ID);
            reservationStatement.setInt(1, reservationId);
            reservationSet = reservationStatement.executeQuery();
            if (reservationSet.next()) {
               return buildReservation(reservationSet);
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(reservationSet);
            DAOUtil.closePreparedStatement(reservationStatement);
            DAOUtil.closeConnection(connection);
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
        PreparedStatement loanTypeStatement = null;
        ResultSet loanTypeSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            List<DeliveryType> deliveryTypes = new ArrayList<>();

            if (searchStr != null){
                loanTypeStatement = connection.prepareStatement(SEARCH_RESERVATIONS);
                loanTypeStatement.setString(1, searchStr);
            }else {
                loanTypeStatement = connection.prepareStatement(GET_ALL_RESERVATIONS);

            }

            loanTypeSet = loanTypeStatement.executeQuery();
            while (loanTypeSet.next()) {
                deliveryTypes.add(buildDeliveryReservation(loanTypeSet));
            }

            return deliveryTypes;

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(loanTypeSet);
            DAOUtil.closePreparedStatement(loanTypeStatement);
            DAOUtil.closeConnection(connection);
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
        PreparedStatement loanTypeStatement = null;
        ResultSet loanTypeSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            List<LoanType> loanTypes = new ArrayList<>();

            if (type.equals(LOAN_TYPE_RESERVATION)) {
                loanTypeStatement = connection.prepareStatement(GET_RESERVATIONS_BY_USER_ID);
                loanTypeStatement.setInt(1, userId);
                loanTypeSet = loanTypeStatement.executeQuery();
                while (loanTypeSet.next()) {
                    loanTypes.add(buildReservation(loanTypeSet));
                }
            } else if (type.equals(LOAN_TYPE_LOAN)) {
                loanTypeStatement = connection.prepareStatement(GET_LOANS_BY_USER_ID);
                loanTypeStatement.setInt(1, userId);
                loanTypeSet = loanTypeStatement.executeQuery();
                while (loanTypeSet.next()) {
                    loanTypes.add(buildLoan(loanTypeSet));
                }
            }
            return loanTypes;

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(loanTypeSet);
            DAOUtil.closePreparedStatement(loanTypeStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    @Override
    public MediaDetail getMediaDetail(int mediaTypeId) throws DAOException {
        Connection connection = null;

        PreparedStatement mediaDetailsStatement = null;
        ResultSet mediaDetailsSet = null;

        PreparedStatement copiesStatement = null;
        ResultSet copiesSet = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            mediaDetailsStatement = connection.prepareStatement(GET_MEDIA_TYPE_DETAILS);
            mediaDetailsStatement.setInt(1, mediaTypeId);
            mediaDetailsSet = mediaDetailsStatement.executeQuery();

            copiesStatement = connection.prepareStatement(GET_COPIES_FOR_MEDIA_TYPE);
            copiesStatement.setInt(1, mediaTypeId);
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
            int availableCopies = totalCopies - loanedCopies - reservedCopies;

            MediaDetail mediaDetail = null;
            if (mediaDetailsSet.next()) {
                mediaDetail = new MediaDetail(
                        mediaDetailsSet.getInt(MEDIA_TYPES_MEDIA_ID),
                        mediaDetailsSet.getInt(MEDIA_TYPES_MEDIA_TYPE_ID),
                        totalCopies,
                        availableCopies,
                        reservedCopies,
                        loanedCopies,
                        mediaDetailsSet.getDouble(MEDIA_TYPES_PRICE),
                        mediaDetailsSet.getString(MEDIA_TITLE),
                        mediaDetailsSet.getString(MEDIA_SUMMARY),
                        mediaDetailsSet.getString(MEDIA_TYPES_ISBN),
                        mediaDetailsSet.getString(MEDIA_TYPES_PICTURE),
                        mediaDetailsSet.getString(MEDIA_TYPES_PUBLISHER),
                        mediaDetailsSet.getString(MEDIA_TYPES_MATERIAL_TYPE),
                        mediaDetailsSet.getString(MEDIA_TYPES_LANGUAGE),
                        mediaDetailsSet.getString(MEDIA_TYPES_RESTRICTION),
                        getAuthorsByMediaID(mediaDetailsSet.getInt(MEDIA_TYPES_MEDIA_ID)),
                        getGenresByMediaID(mediaDetailsSet.getInt(MEDIA_TYPES_MEDIA_ID)));
            }

            return mediaDetail;

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(mediaDetailsSet);
            DAOUtil.closePreparedStatement(mediaDetailsStatement);
            DAOUtil.closeConnection(connection);
        }

    }

    private List<Author> getAuthorsByMediaID(int mediaID) throws DAOException {
        Connection connection = null;

        PreparedStatement authorPreparedStatement = null;
        ResultSet authorResultSet = null;

        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

            List<Author> authors = new ArrayList<>();
            authorPreparedStatement = connection.prepareStatement(GET_AUTHORS_FOR_MEDIA);
            authorPreparedStatement.setInt(1, mediaID);
            authorResultSet = authorPreparedStatement.executeQuery();

            while (authorResultSet.next()) {
                authors.add(new Author(
                        authorResultSet.getInt(AUTHORS_AUTHOR_ID),
                        authorResultSet.getString(AUTHORS_AUTHOR_FULL_NAME),
                        authorResultSet.getString(AUTHORS_AUTHOR_PEN_NAME)
                ));
            }

            return authors;

        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(authorResultSet);
            DAOUtil.closePreparedStatement(authorPreparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    private List<Genre> getGenresByMediaID(int mediaID) throws DAOException {
        Connection connection = null;

        PreparedStatement genrePreparedStatement = null;
        ResultSet genreResultSet = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();

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
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(genreResultSet);
            DAOUtil.closePreparedStatement(genrePreparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }

    private DeliveryType buildDeliveryReservation(ResultSet deliveryReservationSet) throws SQLException, DAOException {
        DeliveryType deliveryType = new DeliveryType();
        LoanType loanType = buildReservation(deliveryReservationSet);
        deliveryType.setLoanType(loanType);
        deliveryType.setUser(DAOUtil.getUserById(loanType.getUserId()));
        return deliveryType;
    }



    private LoanType buildLoan(ResultSet loanSet) throws SQLException, DAOException {
        LoanType loanType = new LoanType();
        setLoanType(loanSet, LOANS_LOAN_ID,loanType);
        if (loanSet.getDate(LOAN_TYPE_END_DATE) == null) {
            loanType.setEndDate(loanType.getStartDate().plusDays(loanType.getDuration()));
        } else {
            loanType.setEndDate(loanSet.getDate(LOAN_TYPE_END_DATE).toLocalDate());
        }
        loanType.setMediaDetail(getMediaDetail(loanSet.getInt(MEDIA_TYPES_MEDIA_TYPE_ID)));

        return loanType;
    }

    private LoanType buildReservation(ResultSet reservationSet) throws SQLException, DAOException {
        LoanType loanType = new LoanType();
        setLoanType(reservationSet, RESERVATIONS_RESERVATION_ID,loanType);
        loanType.setEndDate(loanType.getStartDate().plusDays(loanType.getDuration()));
        loanType.setStatus(reservationSet.getString(FIELD_STATUS));
        loanType.setMediaDetail(getMediaDetail(reservationSet.getInt(MEDIA_TYPES_MEDIA_TYPE_ID)));

        return loanType;
    }

    private void setLoanType(ResultSet loanSet, String loansLoanId, LoanType loanType) throws SQLException {
        loanType.setUserId(loanSet.getInt(LOAN_TYPE_USER_ID));
        loanType.setLoanTypeId(loanSet.getInt(loansLoanId));
        loanType.setCopyId(loanSet.getInt(LOAN_TYPE_COPY_ID));
        loanType.setDuration(loanSet.getInt(LOAN_TYPE_DURATION));
        loanType.setStartDate(loanSet.getDate(LOAN_TYPE_START_DATE).toLocalDate());
    }
}

