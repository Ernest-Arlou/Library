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

public class RegistrationForm implements Command {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationForm.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {

            request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

            if (request.getParameter(RequestParameter.REGISTRATION_FAIL_MSG) != null) {
                request.setAttribute(RequestAttribute.REGISTRATION_FAIL_MSG, request.getParameter(RequestParameter.REGISTRATION_FAIL_MSG));
            }
            if (request.getParameter(RequestParameter.REGISTRATION_SUCCESS_MSG) != null) {
                request.setAttribute(RequestAttribute.REGISTRATION_SUCCESS_MSG, request.getParameter(RequestParameter.REGISTRATION_SUCCESS_MSG));
            }

            request.getRequestDispatcher(JSPPath.REGISTRATION).forward(request, response);
        } catch (ServletException e) {
            logger.error("ServletException in RegistrationForm", e);
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in RegistrationForm", e);
            throw new CommandException(e);
        }
    }
}
