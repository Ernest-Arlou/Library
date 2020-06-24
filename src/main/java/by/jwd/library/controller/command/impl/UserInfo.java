package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.SessionAttributes;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserInfo implements Command {
    private static final String REQUEST_ATTRIBUTE_NAME = "name";
    private static final String REQUEST_ATTRIBUTE_USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = (String) request.getSession().getAttribute(SessionAttributes.LOGIN);
        try {
            User user = ServiceFactory.getInstance().getUserService().getUserByLogin(login);
            request.setAttribute(REQUEST_ATTRIBUTE_NAME, user.getName());
            request.setAttribute(REQUEST_ATTRIBUTE_USER, user);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPath.USER_INFO);
            requestDispatcher.forward(request, response);

        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}

