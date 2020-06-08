package by.jwd.library.controller;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1708654178823021864L;
    private final CommandProvider provider = new CommandProvider();


    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        doPost(req, resp);
    }


    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String commandName;
        Command command;
        commandName = request.getParameter("command");
        command = provider.getCommand(commandName);

        try {
            command.execute(request, response);
        } catch (CommandException e) {
            //log
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPath.ERROR);
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

}