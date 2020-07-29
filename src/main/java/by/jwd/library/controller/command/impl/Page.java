package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.MediaPage;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Page implements Command {
    private final static int ITEMS_PER_PAGE = 4;

    private static final Logger logger = LoggerFactory.getLogger(Page.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        try {
            String page = request.getParameter(RequestParameter.PAGE);
            String search = request.getParameter(RequestParameter.SEARCH);
            MediaPage mediaPageItems = ServiceFactory.getInstance().getLibraryService().getPageItems(Integer.parseInt(page), ITEMS_PER_PAGE, search);

            request.setAttribute(RequestAttribute.PAGE_ITEMS, mediaPageItems);

            request.getRequestDispatcher(JSPPath.MEDIA).forward(request, response);

        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in Page", e);
            throw new CommandException(e);
        } catch (ServletException e) {
            logger.error("ServletException in Page", e);
            throw new CommandException(e);
        }
    }
}
