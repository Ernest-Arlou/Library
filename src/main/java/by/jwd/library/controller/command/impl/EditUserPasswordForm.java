package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constants.JSPPath;
import by.jwd.library.controller.constants.RequestAttribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserPasswordForm implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.userLoggedIn(request);

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));


        try {
            String editPassMsg = request.getParameter(RequestAttribute.EDIT_USER_PASSWORD_MSG);
            if (editPassMsg != null) {
                request.setAttribute(RequestAttribute.EDIT_USER_PASSWORD_MSG, editPassMsg);
            }

            request.getRequestDispatcher(JSPPath.EDIT_USER_PASSWORD_FORM).forward(request, response);

        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }

    }
}
