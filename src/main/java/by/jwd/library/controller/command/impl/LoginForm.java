package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginForm implements Command {

    private static final Logger logger = LoggerFactory.getLogger(LoginForm.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        try {
            if (request.getParameter(RequestParameter.LOGIN_FAIL_MSG) != null) {
                request.setAttribute(RequestAttribute.LOGIN_FAIL_MSG, request.getParameter(RequestParameter.LOGIN_FAIL_MSG));
            }

            request.getRequestDispatcher(JSPPath.LOGIN).forward(request, response);
        } catch (ServletException e) {
            logger.error("ServletException in LoginForm", e);
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in LoginForm", e);
            throw new CommandException(e);
        }
    }
}
