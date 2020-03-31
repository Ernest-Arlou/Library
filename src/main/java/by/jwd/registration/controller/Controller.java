package by.jwd.registration.controller;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;
import by.jwd.registration.dao.DAOException;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

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
    public void init () throws ServletException{
        super.init();
//        try {
//            ConnectionPoolManager.getInstance().initConnectionPool();
//        } catch (DAOException e) {
//            //log
//        }
    }

    @Override
    protected void service (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        super.service(req, resp);
    }

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
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void destroy (){
        super.destroy();
//        try {
//            ConnectionPoolManager.getInstance().disposeConnectionPull();
//        } catch (DAOException e) {
//            //log
//        }
    }
}