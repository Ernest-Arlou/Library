package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constant.CommandURL;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteReservation implements Command {
    private static final String FROM_DELIVERY = "delivery";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if (request.getParameter(RequestParameter.RESERVATION_ID) == null ||
                request.getParameter(RequestParameter.FROM) == null) {
            throw new CommandException("No parameter");
        }

        String from = request.getParameter(RequestParameter.FROM);
        int reservationId = Integer.parseInt(request.getParameter(RequestParameter.RESERVATION_ID));

        try {
            ServiceFactory.getInstance().getLibraryService().deleteReservation(reservationId);
            if (from.equalsIgnoreCase(FROM_DELIVERY)) {
                response.sendRedirect(CommandURL.DELIVERY);
            } else {
                response.sendRedirect(CommandURL.PROFILE);
            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
