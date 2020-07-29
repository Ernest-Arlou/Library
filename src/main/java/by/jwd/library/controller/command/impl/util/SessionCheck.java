package by.jwd.library.controller.command.impl.util;

import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constant.SessionAttributes;
import by.jwd.library.util.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCheck {

    private static final Logger logger = LoggerFactory.getLogger(SessionCheck.class);

    private SessionCheck() {

    }

    public static void librarianOrAdmin(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        if (session.getAttribute(SessionAttributes.USER_ID) == null) {
            logger.error("CommandException in SessionCheck method librarianOrAdmin() - No logged user");
            throw new CommandException("No logged user");
        }

        String role = (String) session.getAttribute(SessionAttributes.USER_ROLE);
        if (!(role.equalsIgnoreCase(UserRole.ADMIN) ||
                role.equalsIgnoreCase(UserRole.LIBRARIAN))) {
            logger.error("CommandException in SessionCheck method librarianOrAdmin() - Wrong role");
            throw new CommandException("Wrong role");
        }
    }

    public static void userLoggedIn(HttpServletRequest request) throws CommandException {
        if (request.getSession().getAttribute(SessionAttributes.USER_ROLE) == null) {
            logger.error("CommandException in SessionCheck method userLoggedIn() - No Logged User");
            throw new CommandException("No Logged User");
        }
    }
}
