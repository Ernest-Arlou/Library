package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WrongRequest implements Command {

    private static final Logger logger = LoggerFactory.getLogger(WrongRequest.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));
            request.getRequestDispatcher(JSPPath.ERROR).forward(request, response);
        } catch (ServletException e) {
            logger.error("ServletException in WrongRequest", e);
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in WrongRequest", e);
            throw new CommandException(e);
        }
    }
}
