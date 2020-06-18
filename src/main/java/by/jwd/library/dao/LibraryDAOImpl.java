package by.jwd.library.dao;

import by.jwd.library.bean.*;
import by.jwd.library.dao.connectionpool.ConnectionPoolException;
import by.jwd.library.dao.connectionpool.ConnectionPoolManager;
import by.jwd.library.dao.util.DAOUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAOImpl implements LibraryDAO {
//    private static final String GET_ALL_MEDIA_TYPES =
//            "SELECT `media-id`, `media-type-id`, price, isbn, `material-type`, publisher, `language` FROM `media-types` " +
//                    "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`" +
//                    " inner join languages on `media-types`.`language-id` = languages.`language-id`" +
//                    " inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id`;";
//    private static final  String GET_ALL_MEDIA = "SELECT * FROM media inner join series on media.`series-id` = series.`series-id`;";


////    private static final String GET_MEDIA_DISPLAY_PAGE = "SELECT * FROM copies " +
////            "inner join `media-types` on copies.`media-type-id` = `media-types`.`media-type-id` " +
////            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` " +
////            "inner join languages on `media-types`.`language-id` = languages.`language-id`" +
////            "inner join media on `media-types`.`media-id` = `media`.`media-id` where `copy-id` <=? order by `date-added` desc LIMIT ?;";


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
            "where media.title like ? or `material-types`.`material-type` like ? and\n" +
            "`media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' \n" +
            "order by `media-type-id` desc LIMIT ?,?; ";


    private static final String GET_MEDIA_TYPE_DETAILS = "SELECT * FROM `media-types` \n" +
            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
            "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
            "inner join media on `media-types`.`media-id` = `media`.`media-id` \n" +
            "where `media-types`.status != 'deleted' and `material-types`.status != 'deleted' \n" +
            "and publishers.status != 'deleted' and languages.status != 'deleted' and media.`media-id` != 'deleted' and `media-types`.`media-type-id` = ?";

    private static final String GET_LAST_COPY_ID = "SELECT `copy-id` FROM copies order by `copy-id` desc limit 1;";
    private static final String GET_MEDIA_TYPES_TOTAL_ITEMS = "SELECT * , count(*) `total-items` FROM `media-types` where status != 'deleted';";

    private static final String GET_MEDIA_TYPES_TOTAL_ITEMS_WITH_SEARCH = "SELECT  *, count(*) `total-items` FROM `media-types`\n" +
            "inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id` \n" +
            "inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
            "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
            "inner join media on `media-types`.`media-id` = `media`.`media-id` \n" +
            "where media.title like ? or `material-types`.`material-type` like ? and\n" +
            "`media-types`.status != 'deleted' and `material-types`.status != 'deleted' and publishers.status != 'deleted' and languages.status != 'deleted' \n" +
            "order by `media-type-id`; ";

    //    private static final String GET_MEDIA_TYPES_COUNT = "SELECT `media-type-id` FROM `media-types` order by `media-type-id` desc limit 1;";
    private static final String GET_AUTHORS_FOR_MEDIA = "SELECT * FROM `media-have-authors` inner join authors on `media-have-authors`.`author-id` = authors.`author-id` " +
            "where `media-id` = ? and `media-have-authors`.status != 'deleted' and authors.status != 'deleted' ;";
    private static final String GET_GENRES_FOR_MEDIA = "SELECT * FROM `media-have-genres` inner join genres on `media-have-genres`.`genre-id` = genres.`genre-id` " +
            "where `media-id` = ? and `media-have-genres`.status != 'deleted' and genres.status != 'deleted' ;";

    private static final String GET_COPIES_FOR_MEDIA_TYPE = "SELECT * FROM copies where `media-type-id` = ? and status != 'deleted' ;";

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
    private static final String COPIES_STATUS = "status";
    private static final String COPIES_STATUS_LOANED = "loaned";
    private static final String COPIES_STATUS_RESERVED = "reserved";

    private static final String AUTHORS_AUTHOR_ID = "author-id";
    private static final String AUTHORS_AUTHOR_FULL_NAME = "full-name";
    private static final String AUTHORS_AUTHOR_PEN_NAME = "pen-name";

    private static final String GENRES_GENRE_ID = "genre-id";
    private static final String GENRES_GENRE = "genre";


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
                String status = copiesSet.getString(COPIES_STATUS);
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
                        Integer.parseInt(authorResultSet.getString(AUTHORS_AUTHOR_ID)),
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
                        Integer.parseInt(genreResultSet.getString(GENRES_GENRE_ID)),
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
}

//    public List<MediaType> getAllMediaTypes() throws DAOException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
//            preparedStatement = connection.prepareStatement(GET_ALL_MEDIA_TYPES);
//            resultSet = preparedStatement.executeQuery();
//            List<MediaType> mediaTypes = new ArrayList<>();
//            while (resultSet.next()) {
//                mediaTypes.add(buildMediaType(resultSet));
//            }
//            return mediaTypes;
//        } catch (SQLException e) {
//            throw new DAOException("SQL error", e);
//        } catch (ConnectionPoolException e) {
//            throw new DAOException("ConnectionPool error", e);
//        } finally {
//            DAOUtil.closeResultSet(resultSet);
//            DAOUtil.closePreparedStatement(preparedStatement);
//            DAOUtil.closeConnection(connection);
//        }
//    }


//    @Override
//    public List <MediaDisplay> getMediaDisplayItems(int numberPerPage, int pageNumb) throws DAOException {
//        int maxCopies = getLastCopyNumb();
//        int startCopy = maxCopies - (pageNumb - 1) * numberPerPage;
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
//            preparedStatement = connection.prepareStatement(GET_MEDIA_TYPES_PAGE);
//            preparedStatement.setInt(1, startCopy);
//            preparedStatement.setInt(2, numberPerPage);
//            resultSet = preparedStatement.executeQuery();
//            List<MediaDisplay> mediaDisplays = new ArrayList<>();
//            while (resultSet.next()) {
//                mediaDisplays.add(
//                        new MediaDisplay(
//                                resultSet.getInt(COPIES_COPY_ID),
//                                resultSet.getString(MEDIA_TYPES_PICTURE),
//                                resultSet.getString(MEDIA_TYPES_MATERIAL_TYPE),
//                                resultSet.getString(MEDIA_TITLE),
//                                resultSet.getString(MEDIA_SUMMARY)));
//            }
//            return mediaDisplays;
//        } catch (SQLException e) {
//            throw new DAOException("SQL error", e);
//        } catch (ConnectionPoolException e) {
//            throw new DAOException("ConnectionPool error", e);
//        } finally {
//            DAOUtil.closeResultSet(resultSet);
//            DAOUtil.closePreparedStatement(preparedStatement);
//            DAOUtil.closeConnection(connection);
//        }
//
//
//
//    }

//    private MediaType buildMediaType(ResultSet MediaTypesPageSet) throws SQLException {
//        return new MediaType(
//                MediaTypesPageSet.getInt(MEDIA_TYPES_MEDIA_ID),
//                MediaTypesPageSet.getInt(MEDIA_TYPES_MEDIA_TYPE_ID),
//                MediaTypesPageSet.getDouble(MEDIA_TYPES_PRICE),
//                MediaTypesPageSet.getString(MEDIA_TYPES_ISBN),
//                MediaTypesPageSet.getString(MEDIA_TYPES_MATERIAL_TYPE),
//                MediaTypesPageSet.getString(MEDIA_TYPES_PUBLISHER),
//                MediaTypesPageSet.getString(MEDIA_TYPES_LANGUAGE));
//    }
//
//    @Override
//    public List<Media> getAllMedia() throws DAOException {
//        Connection connection = null;
//        PreparedStatement mediaPreparedStatement = null;
//        ResultSet mediaResultSet = null;
//        PreparedStatement authorPreparedStatement = null;
//        ResultSet authorResultSet = null;
//        PreparedStatement genrePreparedStatement = null;
//        ResultSet genreResultSet = null;
//        try {
//            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
//            mediaPreparedStatement = connection.prepareStatement(GET_ALL_MEDIA);
//            mediaResultSet = mediaPreparedStatement.executeQuery();
//            List<Media> mediaList = new ArrayList<>();
//            while (mediaResultSet.next()) {
//                Media media = new Media();
//                media.setMediaId(Integer.parseInt(mediaResultSet.getString("media-id")));
//                media.setTitle( mediaResultSet.getString("title"));
//                media.setPicture( mediaResultSet.getString("picture"));
//                media.setSummary( mediaResultSet.getString("summary"));
//                media.setSeries( mediaResultSet.getString("series"));
//
//                List<Author> authors = new ArrayList<>();
//                authorPreparedStatement = connection.prepareStatement(GET_AUTHORS_FOR_MEDIA);
//                authorPreparedStatement.setString(1, String.valueOf(media.getMediaId()));
//                authorResultSet = authorPreparedStatement.executeQuery();
//                while (authorResultSet.next()){
//                    authors.add(new Author(
//                            Integer.parseInt(authorResultSet.getString("author-id")),
//                            authorResultSet.getString("full-name"),
//                            authorResultSet.getString("pen-name")
//                            ));
//                }
//                media.setAuthors(authors);
//
//                List<Genre> genres = new ArrayList<>();
//                genrePreparedStatement = connection.prepareStatement(GET_GENRES_FOR_MEDIA);
//                genrePreparedStatement.setString(1, String.valueOf(media.getMediaId()));
//                genreResultSet = genrePreparedStatement.executeQuery();
//
//                while (genreResultSet.next()){
//                    genres.add(new Genre(
//                            Integer.parseInt(genreResultSet.getString("genre-id")),
//                            genreResultSet.getString("genre")
//                    ));
//                }
//                media.setGenres(genres);
//                mediaList.add(media);
//            }
//            return mediaList;
//        } catch (SQLException e) {
//            throw new DAOException("SQL error", e);
//        } catch (ConnectionPoolException e) {
//            throw new DAOException("ConnectionPool error", e);
//        } finally {
//            DAOUtil.closeResultSet(mediaResultSet);
//            DAOUtil.closePreparedStatement(mediaPreparedStatement);
//            DAOUtil.closeConnection(connection);
//        }
//    }
//    public List<MediaType> getAllMediaTypes() throws DAOException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
//            preparedStatement = connection.prepareStatement(GET_ALL_MEDIA_TYPES);
//            resultSet = preparedStatement.executeQuery();
//            List<MediaType> mediaTypes = new ArrayList<>();
//            while (resultSet.next()) {
//                mediaTypes.add(buildMediaType(resultSet));
//            }
//            return mediaTypes;
//        } catch (SQLException e) {
//            throw new DAOException("SQL error", e);
//        } catch (ConnectionPoolException e) {
//            throw new DAOException("ConnectionPool error", e);
//        } finally {
//            DAOUtil.closeResultSet(resultSet);
//            DAOUtil.closePreparedStatement(preparedStatement);
//            DAOUtil.closeConnection(connection);
//        }
//    }


/*
SELECT * FROM copies
inner join `media-types` on copies.`media-type-id` = `media-types`.`media-type-id`
inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id`
inner join languages on `media-types`.`language-id` = languages.`language-id`
inner join media on `media-types`.`media-id` = `media`.`media-id` where `copy-id` < 3 order by `date-added` desc
;

SELECT * , count(*) `total-rows` FROM library.copies;

 */