package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.MediaPage;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Page implements Command {
    private final static String REQUEST_ATTR_PAGE_ITEMS = "mediapage";
    private final static String REQUEST_PARAM_PAGE = "page";
    private final static String REQUEST_PARAM_SEARCH = "search";
    private final static int ITEMS_PER_PAGE = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String page = request.getParameter(REQUEST_PARAM_PAGE);
            String search = request.getParameter(REQUEST_PARAM_SEARCH);
            MediaPage mediaPageItems = ServiceFactory.getInstance().getLibraryService().getPageItems(Integer.parseInt(page), ITEMS_PER_PAGE, search);

            request.setAttribute(REQUEST_ATTR_PAGE_ITEMS, mediaPageItems);

            request.getRequestDispatcher(JSPPath.MEDIA).forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
