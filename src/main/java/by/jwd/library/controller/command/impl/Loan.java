package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.impl.util.LocalMessageCoder;
import by.jwd.library.controller.constants.CommandURL;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;
import by.jwd.library.controller.constants.SessionAttributes;
import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Loan implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.librarianOrAdmin(request);

        try {

            int userId = Integer.parseInt(request.getParameter(RequestParameter.USER_ID));
            int copyId = Integer.parseInt(request.getParameter(RequestParameter.COPY_ID));
            int reservationId = Integer.parseInt(request.getParameter(RequestParameter.RESERVATION_ID));

            String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);

            ServiceFactory.getInstance().getLibraryService().giveOutCopy(userId, copyId, reservationId);

            response.sendRedirect(CommandURL.DELIVERY + "&" + RequestAttribute.DELIVERY_MSG
                    + "=" + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.GIVE_OUT_SUCCESS_MSG));

        } catch (ServiceException | IOException e) {
            throw new CommandException(e);
        }
    }
}
