package by.jwd.registration.controller;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.dao.DAOException;
import by.jwd.registration.dao.connectionpool.ConnectionPool;
import by.jwd.registration.dao.connectionpool.ConnectionPoolManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private final CommandProvider provider = new CommandProvider();


    private static final long serialVersionUID = 1708654178823021864L;

    @Override
    public void init() throws ServletException{
        super.init();
        try {
            ConnectionPoolManager.getInstance().initConnectionPool();
        } catch (DAOException e) {
            //log
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        String commandName;
        Command command;
        commandName = request.getParameter("command");
        System.out.println(commandName);
        command = provider.getCommand(commandName);

        command.execute(request,response);



//        System.out.println("here");
//        String command = request.getParameter("command");




//        String ajaxCommandName = request.getParameter(RequestParameterName.COMMAND_NAME);
//
//        AjaxCommand ajaxCommand = AjaxCommandProvider.getInstance().getAjaxCommand(ajaxCommandName.toUpperCase());
//        try {
//            String jsonAnswer = ajaxCommand.execute(request, response);
//            PrintWriter out = response.getWriter();
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            out.print(jsonAnswer);
//            out.flush();
//        } catch (CommandException e) {
//            logger.log(Level.ERROR, "Exception in doPost method");
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher(JspPageName.ERROR_PAGE);
//            if (requestDispatcher != null) {
//                requestDispatcher.forward(request, response);
//            }
//        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPoolManager.getInstance().disposeConnectionPull();
        } catch (DAOException e) {
            //log
        }
    }
}