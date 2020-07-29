package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constant.CommandURL;
import by.jwd.library.controller.constant.SessionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOut implements Command {

    private static final Logger logger = LoggerFactory.getLogger(LogOut.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttributes.LOGIN, null);
        session.setAttribute(SessionAttributes.USER_ID, null);
        session.setAttribute(SessionAttributes.USER_ROLE, null);
        try {
            response.sendRedirect(CommandURL.CONTROLLER);
        } catch (IOException e) {
            logger.error("IOException in LogOut", e);
            throw new CommandException(e);
        }
    }
}
