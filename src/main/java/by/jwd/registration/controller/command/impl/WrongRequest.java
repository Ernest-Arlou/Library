package by.jwd.registration.controller.command.impl;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WrongRequest implements Command {
    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        try {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }
    }
}
