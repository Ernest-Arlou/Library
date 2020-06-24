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

public class Index implements Command {
    private final static String REQUEST_ATTR_PAGE_ITEMS = "mediapage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {

            MediaPage mediaPageItems = ServiceFactory.getInstance().getLibraryService().getPageItems(1, 8, null);

            request.setAttribute(REQUEST_ATTR_PAGE_ITEMS, mediaPageItems);

            request.getRequestDispatcher(JSPPath.INDEX).forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
