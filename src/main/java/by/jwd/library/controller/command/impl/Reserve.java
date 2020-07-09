package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.LocalMessageCoder;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constants.CommandURL;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;
import by.jwd.library.controller.constants.SessionAttributes;
import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.service.LibraryService;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Reserve implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.userLoggedIn(request);

        int mediaId = Integer.parseInt(request.getParameter(RequestParameter.MEDIA_ID));
        HttpSession session = request.getSession();

        int userId = (int) session.getAttribute(SessionAttributes.USER_ID);
        String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);

        String lastCommand = request.getParameter(RequestParameter.LAST_COMMAND);
        String mediaDetailCallPoint;

        LibraryService libraryService = ServiceFactory.getInstance().getLibraryService();
        try {

            if (lastCommand == null || lastCommand.isEmpty()){
                mediaDetailCallPoint = CommandURL.MEDIA_DETAIL + "&" + RequestParameter.MEDIA_ID + "=" + mediaId;
            }
            else {
                String lastPage = request.getParameter(RequestParameter.LAST_PAGE);
                if (lastPage == null || lastPage.isEmpty()){
                    mediaDetailCallPoint = CommandURL.MEDIA_DETAIL + "&" + RequestParameter.MEDIA_ID + "=" + mediaId;
                }
                else {
                    lastCommand = lastCommand.substring(0,lastCommand.indexOf(RequestParameter.LAST_PAGE));
                    mediaDetailCallPoint = CommandURL.CONTROLLER + "?" + lastCommand
                            + "&" + RequestAttribute.LAST_PAGE + "=" + QueryCoder.code(
                            request.getParameter(RequestParameter.LAST_PAGE) + "&" +RequestParameter.PAGE + "=" + request.getParameter(RequestParameter.PAGE) +
                                    "&" + RequestParameter.SEARCH + "=" + request.getParameter(RequestParameter.SEARCH));

                }
            }

            if (ServiceFactory.getInstance().getUserService().checkUnverified(userId)) {

                response.sendRedirect(mediaDetailCallPoint + "&" + RequestAttribute.RESERVATION_MSG + "="
                        + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.UNVERIFIED_USER_MSG));
            } else if (!libraryService.canReserve(userId)) {
                response.sendRedirect(mediaDetailCallPoint + "&" + RequestAttribute.RESERVATION_MSG + "="
                        + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.RESERVATION_MAX_LIMIT_MSG));

            } else if (libraryService.userReservedOrLoanedMedia(userId, mediaId)) {
                response.sendRedirect(mediaDetailCallPoint + "&" + RequestAttribute.RESERVATION_MSG + "="
                        + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.RESERVATION_FORBIDDEN_MSG));

            } else {
                libraryService.reserveMedia(userId, mediaId);

                response.sendRedirect(mediaDetailCallPoint + "&" + RequestAttribute.RESERVATION_MSG + "="
                        + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.RESERVATION_SUCCESS_MSG));

            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
