package by.jwd.library.controller.command.impl.util;

import by.jwd.library.controller.constants.SessionAttributes;
import by.jwd.library.controller.constants.UserRole;
import by.jwd.library.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

public class SessionCheck {
    public static void librarianOrAdmin(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        if (session.getAttribute(SessionAttributes.USER_ID) == null){
            throw new CommandException("No logged user");
        }

        String role = (String) session.getAttribute(SessionAttributes.USER_ROLE);
        if (!(role.equalsIgnoreCase(UserRole.ADMIN) ||
                role.equalsIgnoreCase(UserRole.LIBRARIAN))) {
            throw new CommandException("Wrong role");
        }
    }
    public static void userLoggedIn(HttpServletRequest request) throws CommandException {
        if (request.getSession().getAttribute(SessionAttributes.USER_ROLE) == null) {
            throw new CommandException("No Logged User");
        }
    }

    private SessionCheck (){

    }
}
