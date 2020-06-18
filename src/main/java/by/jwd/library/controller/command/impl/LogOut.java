package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOut implements Command {
    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        HttpSession session = request.getSession();
        session.invalidate();
        try {
            response.sendRedirect(JSPPath.CONTROLLER);
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
