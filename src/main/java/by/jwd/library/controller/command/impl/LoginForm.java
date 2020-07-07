package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constants.JSPPath;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginForm implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        try {
            if (request.getParameter(RequestParameter.LOGIN_FAIL_MSG) != null) {
                request.setAttribute(RequestAttribute.LOGIN_FAIL_MSG, request.getParameter(RequestParameter.LOGIN_FAIL_MSG));
            }

            request.getRequestDispatcher(JSPPath.LOGIN).forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }
    }
}
