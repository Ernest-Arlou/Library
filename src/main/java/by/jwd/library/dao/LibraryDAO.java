package by.jwd.library.dao;

import by.jwd.library.bean.Media;

import java.util.List;

public interface LibraryDAO {
    List<Media> getAllMedia() throws DAOException;
}
