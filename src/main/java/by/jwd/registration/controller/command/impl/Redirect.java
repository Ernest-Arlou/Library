package by.jwd.registration.controller.command.impl;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Redirect implements Command {
    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        String jspname;
        jspname = request.getParameter("redirect");
        try {
            request.getRequestDispatcher("WEB-INF/jsp/" + jspname + ".jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }

    }
}
