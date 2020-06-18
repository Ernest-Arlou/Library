package by.jwd.library.dao;


import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;

public interface LibraryDAO {
    MediaPage getMediaTypePage(int page, int itemsPerPage, String search) throws DAOException;

    MediaDetail getMediaDetail(int mediaTypeId) throws DAOException;
//    List <MediaDisplay> getMediaDisplayItems(int numberPerPage, int pageNumb) throws DAOException;


//    List <MediaDisplay> getMediaTypePage(int numberPerPage, int pageNumb) throws DAOException;

//    List<Media> getAllMedia() throws DAOException;
}
