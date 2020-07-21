package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constant.CommandURL;
import by.jwd.library.controller.constant.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOut implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttributes.LOGIN, null);
        session.setAttribute(SessionAttributes.USER_ID, null);
        session.setAttribute(SessionAttributes.USER_ROLE, null);
        try {
            response.sendRedirect(CommandURL.CONTROLLER);
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
