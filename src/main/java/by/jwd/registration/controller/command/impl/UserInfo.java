package by.jwd.registration.controller.command.impl;

import by.jwd.registration.bean.User;
import by.jwd.registration.controller.JSPPagePath;
import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;
import by.jwd.registration.service.ServiceException;
import by.jwd.registration.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserInfo implements Command {
    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        String login = (String) request.getSession().getAttribute("login");
        try {
            User user = ServiceFactory.getInstance().getLibraryService().getUser(login);
            request.setAttribute("login", login);
            request.setAttribute("user", user);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userinfo.jsp");
            requestDispatcher.forward(request, response);

        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}

