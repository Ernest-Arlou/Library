package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserPasswordForm implements Command {

    private static final Logger logger = LoggerFactory.getLogger(EditUserPasswordForm.class);

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

        } catch (ServletException e) {
            logger.error("ServletException in EditUserPasswordForm", e);
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in EditUserPasswordForm", e);
            throw new CommandException(e);
        }

    }
}
