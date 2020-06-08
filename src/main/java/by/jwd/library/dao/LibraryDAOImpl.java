package by.jwd.library.dao;

import by.jwd.library.bean.Author;
import by.jwd.library.bean.Genre;
import by.jwd.library.bean.Media;
import by.jwd.library.bean.MediaType;
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
    private static final String GET_ALL_MEDIA_TYPES =
            "SELECT `media-id`, `media-type-id`, price, isbn, `material-type`, publisher, `language` FROM `media-types` " +
                    "inner join publishers on `media-types`.`publisher-id` = publishers.`publisher-id`\n" +
                    " inner join languages on `media-types`.`language-id` = languages.`language-id`\n" +
                    " inner join `material-types` on `media-types`.`material-type-id` = `material-types`.`material-type-id`;";
    private static final  String GET_ALL_MEDIA = "SELECT * FROM media inner join series on media.`series-id` = series.`series-id`;";
    private static final  String GET_AUTHORS_FOR_MEDIA = "SELECT * FROM `media-have-authors` inner join authors on `media-have-authors`.`author-id` = authors.`author-id` where `media-id` = ?;";
    private static final  String GET_GENRES_FOR_MEDIA = "SELECT * FROM `media-have-genres` inner join genres on `media-have-genres`.`genre-id` = genres.`genre-id` where `media-id` = ?;";
    private static final String MEDIA_TYPE_MEDIA_ID = "media-id";
    private static final String MEDIA_TYPE_ID = "media-type-id";
    private static final String MEDIA_TYPE_PRICE = "price";
    private static final String MEDIA_TYPE_ISBN = "isbn";
    private static final String MEDIA_TYPE_MATERIAL_TYPE = "material-type";
    private static final String MEDIA_TYPE_PUBLISHER = "publisher";
    private static final String MEDIA_TYPE_LANGUAGE = "language";

    private MediaType buildMediaType(ResultSet resultSet) throws SQLException {
        return new MediaType(
                Integer.parseInt(resultSet.getString(MEDIA_TYPE_MEDIA_ID)),
                Integer.parseInt(resultSet.getString(MEDIA_TYPE_ID)),
                Double.parseDouble(resultSet.getString(MEDIA_TYPE_PRICE)),
                resultSet.getString(MEDIA_TYPE_ISBN),
                resultSet.getString(MEDIA_TYPE_MATERIAL_TYPE),
                resultSet.getString(MEDIA_TYPE_PUBLISHER),
                resultSet.getString(MEDIA_TYPE_LANGUAGE));
    }

    @Override
    public List<Media> getAllMedia() throws DAOException {
        Connection connection = null;
        PreparedStatement mediaPreparedStatement = null;
        ResultSet mediaResultSet = null;
        PreparedStatement authorPreparedStatement = null;
        ResultSet authorResultSet = null;
        PreparedStatement genrePreparedStatement = null;
        ResultSet genreResultSet = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            mediaPreparedStatement = connection.prepareStatement(GET_ALL_MEDIA);
            mediaResultSet = mediaPreparedStatement.executeQuery();
            List<Media> mediaList = new ArrayList<>();
            while (mediaResultSet.next()) {
                Media media = new Media();
                media.setId(Integer.parseInt(mediaResultSet.getString("media-id")));
                media.setTitle( mediaResultSet.getString("title"));
                media.setPicture( mediaResultSet.getString("picture"));
                media.setSummary( mediaResultSet.getString("summary"));
                media.setSeries( mediaResultSet.getString("series"));

                List<Author> authors = new ArrayList<>();
                authorPreparedStatement = connection.prepareStatement(GET_AUTHORS_FOR_MEDIA);
                authorPreparedStatement.setString(1, String.valueOf(media.getId()));
                authorResultSet = authorPreparedStatement.executeQuery();
                while (authorResultSet.next()){
                    authors.add(new Author(
                            Integer.parseInt(authorResultSet.getString("author-id")),
                            authorResultSet.getString("full-name"),
                            authorResultSet.getString("pen-name")
                            ));
                }
                media.setAuthors(authors);

                List<Genre> genres = new ArrayList<>();
                genrePreparedStatement = connection.prepareStatement(GET_GENRES_FOR_MEDIA);
                genrePreparedStatement.setString(1, String.valueOf(media.getId()));
                genreResultSet = genrePreparedStatement.executeQuery();

                while (genreResultSet.next()){
                    genres.add(new Genre(
                            Integer.parseInt(genreResultSet.getString("genre-id")),
                            genreResultSet.getString("genre")
                    ));
                }
                media.setGenres(genres);
                mediaList.add(media);
            }
            return mediaList;
        } catch (SQLException e) {
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error", e);
        } finally {
            DAOUtil.closeResultSet(mediaResultSet);
            DAOUtil.closePreparedStatement(mediaPreparedStatement);
            DAOUtil.closeConnection(connection);
        }
    }
    public List<MediaType> getAllMediaTypes() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPoolManager.getInstance().getConnectionPool().takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_MEDIA_TYPES);
            resultSet = preparedStatement.executeQuery();
            List<MediaType> mediaTypes = new ArrayList<>();
            while (resultSet.next()) {
                mediaTypes.add(buildMediaType(resultSet));
            }
            return mediaTypes;
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

}