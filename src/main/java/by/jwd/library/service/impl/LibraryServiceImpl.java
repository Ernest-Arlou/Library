package by.jwd.library.service.impl;

import by.jwd.library.bean.MediaDetail;
import by.jwd.library.bean.MediaPage;
import by.jwd.library.dao.DAOException;
import by.jwd.library.dao.factory.DAOFactory;
import by.jwd.library.service.LibraryService;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.util.Pagination;

public class LibraryServiceImpl implements LibraryService {

    @Override
    public MediaPage getPageItems(int page, int itemsPerPage, String search) throws ServiceException {
        try {
            MediaPage mediaPage = DAOFactory.getInstance().getLibraryDAO().getMediaTypePage(page, itemsPerPage, search);
            mediaPage.setNavigationPages(Pagination.getInstance().calculateNavigationPages(mediaPage));
            int totalPages;
            if (mediaPage.getTotalItems() % mediaPage.getItemsPerPage() == 0) {
                totalPages = mediaPage.getTotalItems() / mediaPage.getItemsPerPage();
            } else {
                totalPages = (mediaPage.getTotalItems() / mediaPage.getItemsPerPage()) + 1;
            }
            mediaPage.setTotalPages(totalPages);
            return mediaPage;
        } catch (DAOException e) {
            throw new ServiceException("Error during page load",e);
        }
    }

    @Override
    public MediaDetail getMediaDetail(int mediaTypeId) throws ServiceException {
        try {
            return DAOFactory.getInstance().getLibraryDAO().getMediaDetail(mediaTypeId);
        } catch (DAOException e) {
            throw new ServiceException("Error during media details load", e);
        }
    }

}
