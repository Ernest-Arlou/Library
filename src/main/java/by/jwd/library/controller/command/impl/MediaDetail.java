package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constants.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MediaDetail implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        try {

            String lastPage = request.getParameter(RequestParameter.LAST_PAGE);
            request.setAttribute(RequestAttribute.LAST_PAGE,lastPage);

            int mediaTypeID = Integer.parseInt(request.getParameter(RequestParameter.MEDIA_TYPE_ID));
            by.jwd.library.bean.MediaDetail mediaDetail = ServiceFactory.getInstance().getLibraryService().getMediaDetail(mediaTypeID);

            String reservationMsg = request.getParameter(RequestAttribute.RESERVATION_MSG);
            if (reservationMsg != null) {
                request.setAttribute(RequestAttribute.RESERVATION_MSG, reservationMsg);
            }

            request.setAttribute(RequestAttribute.MEDIA_DETAIL, mediaDetail);
            request.getRequestDispatcher(JSPPath.DETAIL).forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
