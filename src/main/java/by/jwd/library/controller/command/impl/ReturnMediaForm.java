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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReturnMediaForm implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ReturnMediaForm.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.librarianOrAdmin(request);

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        String search = request.getParameter(RequestParameter.SEARCH);
        String returnMediaMsg = request.getParameter(RequestAttribute.RETURN_MEDIA_MSG);
        try {
            List<DeliveryType> deliveryTypes = null;
            if (search != null) {
                deliveryTypes = ServiceFactory.getInstance().getLibraryService().searchLoans(search);
            } else {
                deliveryTypes = ServiceFactory.getInstance().getLibraryService().getAllLoans();
            }
            request.setAttribute(RequestAttribute.DELIVERY_RESERVATIONS, deliveryTypes);

            if (returnMediaMsg != null) {
                request.setAttribute(RequestAttribute.RETURN_MEDIA_MSG, returnMediaMsg);
            }
            request.getRequestDispatcher(JSPPath.RETURN_MEDIA).forward(request, response);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in ReturnMediaForm", e);
            throw new CommandException(e);
        } catch (ServletException e) {
            logger.error("ServletException in ReturnMediaForm", e);
            throw new CommandException(e);
        }
    }

}
