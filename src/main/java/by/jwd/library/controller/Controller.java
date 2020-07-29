package by.jwd.library.controller;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.constant.JSPPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1708654178823021864L;
    private final static String REQUEST_PARAM_COMMAND = "command";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final CommandProvider provider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processCommand(request, response);
    }

    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName;
        Command command;
        commandName = request.getParameter(REQUEST_PARAM_COMMAND);
        command = provider.getCommand(commandName);

        try {
            command.execute(request, response);
        } catch (CommandException e) {
            logger.error("CommandException in Controller", e);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPath.ERROR);
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

}