package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.MediaPage;
import by.jwd.library.controller.constants.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Index implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {

            MediaPage mediaPageItems = ServiceFactory.getInstance().getLibraryService().getPageItems(1, 8, null);

            request.setAttribute(RequestAttribute.PAGE_ITEMS, mediaPageItems);

            request.getRequestDispatcher(JSPPath.INDEX).forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
