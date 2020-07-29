package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.Controller;
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

public class AddMediaForm implements Command {

    private static final Logger logger = LoggerFactory.getLogger(AddMediaForm.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.librarianOrAdmin(request);

        try {
            request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

            request.getRequestDispatcher(JSPPath.ADD_MEDIA).forward(request, response);
        } catch (ServletException e) {
            logger.error("ServletException in AddMediaForm", e);
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in AddMediaForm", e);
            throw new CommandException(e);
        }
    }
}
