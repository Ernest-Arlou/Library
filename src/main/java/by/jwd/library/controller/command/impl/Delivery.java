package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.DeliveryType;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Delivery implements Command {
    private final static String REQUEST_ATTR_RESERVATIONS = "deliveryTypes";
    private final static String REQUEST_ATTR_DELIVERY_MSG = "deliveryMsg";
    private final static String REQUEST_PARAM_SEARCH = "search";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String search = request.getParameter(REQUEST_PARAM_SEARCH);
        String deliveryMsg = request.getParameter(REQUEST_ATTR_DELIVERY_MSG);
        try {
            List<DeliveryType> deliveryTypes = null;
            if (search != null){
                deliveryTypes = ServiceFactory.getInstance().getLibraryService().searchReservations(search);
            }else {
                deliveryTypes = ServiceFactory.getInstance().getLibraryService().getAllReservations();
            }
            request.setAttribute(REQUEST_ATTR_RESERVATIONS, deliveryTypes);

            if (deliveryMsg != null) {
                request.setAttribute(REQUEST_ATTR_DELIVERY_MSG, deliveryMsg);
            }
            request.getRequestDispatcher(JSPPath.DELIVERY).forward(request, response);
        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
