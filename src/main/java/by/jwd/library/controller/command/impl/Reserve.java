package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.constants.CommandURL;
import by.jwd.library.controller.constants.SessionAttributes;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;
import by.jwd.library.service.LibraryService;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Reserve implements Command {
    private final static String RESERVATION_SUCCESS_MSG = "Reservation Complete!";
    private final static String RESERVATION_FORBIDDEN_MSG = "You can reserve/loan only one copy at a time";
    private final static String RESERVATION_MAX_LIMIT = "You have reached the limit of loans and reservations";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int mediaTypeId = Integer.parseInt(request.getParameter(RequestParameter.MEDIA_TYPE_ID));
        HttpSession session = request.getSession();

        if (session.getAttribute(SessionAttributes.USER_ROLE) == null) {
            throw new CommandException("No Logged User");
        }

        int userId = (int) session.getAttribute(SessionAttributes.USER_ID);

        LibraryService libraryService = ServiceFactory.getInstance().getLibraryService();
        try {
            if (!libraryService.canReserve(userId)) {
                response.sendRedirect(CommandURL.MEDIA_DETAIL + "&" + RequestParameter.MEDIA_TYPE_ID + "=" + mediaTypeId + "&" + RequestAttribute.RESERVATION_MSG + "=" + RESERVATION_MAX_LIMIT);
            } else if (libraryService.userReservedOrLoanedMediaType(userId, mediaTypeId)) {
                response.sendRedirect(CommandURL.MEDIA_DETAIL + "&" + RequestParameter.MEDIA_TYPE_ID + "=" + mediaTypeId + "&" + RequestAttribute.RESERVATION_MSG + "=" + RESERVATION_FORBIDDEN_MSG);
            } else {
                libraryService.reserveMedia(userId, mediaTypeId);
                response.sendRedirect(CommandURL.MEDIA_DETAIL + "&" + RequestParameter.MEDIA_TYPE_ID + "=" + mediaTypeId + "&" + RequestAttribute.RESERVATION_MSG + "=" + RESERVATION_SUCCESS_MSG);
            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
