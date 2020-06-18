package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SwitchLocale implements Command {
    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        request.getSession().setAttribute("local", request.getParameter("local"));

        try {

            request.getRequestDispatcher("index1.jsp").forward(request, response);

//            response.sendRedirect(String.valueOf(request.getRequestURL()));
        } catch (IOException | ServletException e) {
           throw new CommandException(e);
        }

    }
}
