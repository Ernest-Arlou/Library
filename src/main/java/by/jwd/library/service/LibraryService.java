package by.jwd.library.service;

import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaDisplay;
import by.jwd.library.bean.MediaPage;

import java.util.List;

public interface LibraryService {

    MediaPage getPageItems(int page, int itemsPerPage, String search) throws ServiceException;

    MediaDetail getMediaDetail(int mediaID) throws ServiceException;
}
