package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.LoanType;
import by.jwd.library.bean.User;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.SessionAttributes;
import by.jwd.library.controller.UserRole;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class Profile implements Command {
    private final static String REQUEST_ATTR_RESERVATIONS = "reservations";
    private final static String REQUEST_ATTR_LOANS = "loans";
    private final static String REQUEST_ATTR_USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            HttpSession session = request.getSession();

            if (session.getAttribute(SessionAttributes.USER_ROLE) == null){
                throw new CommandException("No Logged User");
            }

            int userID = (int) session.getAttribute(SessionAttributes.USER_ID);
            String userRole = (String) session.getAttribute(SessionAttributes.USER_ROLE);

            User user = ServiceFactory.getInstance().getUserService().getUserById(userID);
            request.setAttribute(REQUEST_ATTR_USER, user);

            if (userRole.equalsIgnoreCase(UserRole.USER)){
                List<LoanType> reservations = ServiceFactory.getInstance().getLibraryService().getUserReservations(userID);
                request.setAttribute(REQUEST_ATTR_RESERVATIONS, reservations);

                List<LoanType> loans = ServiceFactory.getInstance().getLibraryService().getUserLoans(userID);
                request.setAttribute(REQUEST_ATTR_LOANS, loans);
            }

            request.getRequestDispatcher(JSPPath.PROFILE).forward(request, response);


        } catch (ServiceException | IOException | ServletException e) {
            throw new CommandException(e);
        }
    }
}
