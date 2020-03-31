package by.jwd.registration.controller.command.impl;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;

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
            response.sendRedirect("index.jsp");
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
