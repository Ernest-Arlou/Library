package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.DeliveryType;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Delivery implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.librarianOrAdmin(request);

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        String search = request.getParameter(RequestParameter.SEARCH);
        String deliveryMsg = request.getParameter(RequestAttribute.DELIVERY_MSG);
        try {
            List<DeliveryType> deliveryTypes = null;
            if (search != null) {
                deliveryTypes = ServiceFactory.getInstance().getLibraryService().searchReservations(search);
            } else {
                deliveryTypes = ServiceFactory.getInstance().getLibraryService().getAllReservations();
            }
            request.setAttribute(RequestAttribute.DELIVERY_RESERVATIONS, deliveryTypes);

            if (deliveryMsg != null) {
                request.setAttribute(RequestAttribute.DELIVERY_MSG, deliveryMsg);
            }
            request.getRequestDispatcher(JSPPath.DELIVERY).forward(request, response);
        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
