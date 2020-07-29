package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.MediaPage;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Index implements Command {

    private static final Logger logger = LoggerFactory.getLogger(Index.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {

            MediaPage mediaPageItems = ServiceFactory.getInstance().getLibraryService().getPageItems(1, 4, null);

            request.setAttribute(RequestAttribute.PAGE_ITEMS, mediaPageItems);

            request.getRequestDispatcher(JSPPath.INDEX).forward(request, response);

        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in Index", e);
            throw new CommandException(e);
        } catch (ServletException e) {
            logger.error("ServletException in Index", e);
            throw new CommandException(e);
        }
    }
}
