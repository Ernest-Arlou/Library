package by.jwd.registration.controller.command.impl;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SwitchLocale implements Command {
    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        request.getSession().setAttribute("local", request.getParameter("local"));

        try {








            request.getRequestDispatcher("index.jsp").forward(request, response);
//            response.sendRedirect(String.valueOf(request.getRequestURL()));
        } catch (IOException | ServletException e) {
           throw new CommandException(e);
        }

    }
}
