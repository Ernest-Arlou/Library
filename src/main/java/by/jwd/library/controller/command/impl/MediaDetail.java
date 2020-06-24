package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MediaDetail implements Command {
    private final static String REQUEST_PARAM_MEDIA_TYPE_ID = "media_type_id";
    private final static String REQUEST_ATTR_MEDIA_DETAIL = "mediaDetail";
    private final static String REQUEST_ATTR_RESERVATION_MSG = "reservationMsg";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            int mediaTypeID = Integer.parseInt(request.getParameter(REQUEST_PARAM_MEDIA_TYPE_ID));
            by.jwd.library.bean.MediaDetail mediaDetail = ServiceFactory.getInstance().getLibraryService().getMediaDetail(mediaTypeID);


            String reservationMsg = request.getParameter(REQUEST_ATTR_RESERVATION_MSG);
            if (reservationMsg != null) {
                request.setAttribute(REQUEST_ATTR_RESERVATION_MSG, reservationMsg);
            }

            request.setAttribute(REQUEST_ATTR_MEDIA_DETAIL, mediaDetail);
            request.getRequestDispatcher(JSPPath.DETAIL).forward(request, response);

        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
