package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.LoanType;
import by.jwd.library.bean.User;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.SessionAttributes;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;
import by.jwd.library.util.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class Profile implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.userLoggedIn(request);

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        try {
            HttpSession session = request.getSession();

            int userID = (int) session.getAttribute(SessionAttributes.USER_ID);
            String userRole = (String) session.getAttribute(SessionAttributes.USER_ROLE);

            User user = ServiceFactory.getInstance().getUserService().getUserById(userID);
            request.setAttribute(RequestAttribute.USER, user);

            if (userRole.equalsIgnoreCase(UserRole.USER)) {
                List<LoanType> reservations = ServiceFactory.getInstance().getLibraryService().getUserReservations(userID);
                request.setAttribute(RequestAttribute.RESERVATIONS, reservations);

                List<LoanType> loans = ServiceFactory.getInstance().getLibraryService().getUserLoans(userID);
                request.setAttribute(RequestAttribute.LOANS, loans);
            }

            request.getRequestDispatcher(JSPPath.PROFILE).forward(request, response);


        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
