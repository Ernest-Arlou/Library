package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.SessionAttributes;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserPasswordForm implements Command {
    private static final String REQUEST_ATTRIBUTE_EDIT_USER_PASS = "editUserPassMSG";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();

        if (session.getAttribute(SessionAttributes.USER_ROLE) == null) {
            throw new CommandException("No Logged User");
        }


        try {
            String editPassMsg = request.getParameter(REQUEST_ATTRIBUTE_EDIT_USER_PASS);
            if (editPassMsg != null) {
                request.setAttribute(REQUEST_ATTRIBUTE_EDIT_USER_PASS, editPassMsg);
            }

            request.getRequestDispatcher(JSPPath.EDIT_USER_PASSWORD_FORM).forward(request, response);

        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }

    }
}
