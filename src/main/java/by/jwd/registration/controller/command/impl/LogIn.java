package by.jwd.registration.controller.command.impl;

import by.jwd.registration.bean.LoginInfo;
import by.jwd.registration.controller.JSPPagePath;
import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;
import by.jwd.registration.service.ServiceException;
import by.jwd.registration.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogIn implements Command {

    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        LoginInfo loginInfo = new LoginInfo(login, password);
        String authorize = null;
        try {
            authorize = ServiceFactory.getInstance().getLibraryService().login(loginInfo);
            if (authorize.equals("SUCCESS LOGIN")) {
                HttpSession session = request.getSession();
                session.setAttribute("login", login);
                response.sendRedirect("/Controller?command=user_info");
            } else {
                request.setAttribute("WrongLoginMsg", authorize);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPagePath.INDEX);
                requestDispatcher.include(request, response);
            }
        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
