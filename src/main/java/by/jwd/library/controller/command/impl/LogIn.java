package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.LoginInfo;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogIn implements Command {
    private static final String LOGIN_FAIL_MSG = "Wrong login or password!";
    private static final String LOGIN_FAIL_ATTRIBUTE = "LoginFailMsg";
    private static final String REQUEST_PARAM_LOGIN = "login";
    private static final String REQUEST_PARAM_PASSWORD = "password";
    private static final String USER_INFO_COMMAND = "?command=user_info";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(REQUEST_PARAM_LOGIN);
        String password = request.getParameter(REQUEST_PARAM_PASSWORD);
        LoginInfo loginInfo = new LoginInfo(login, password);
        User user = null;
        System.out.println(request.getQueryString());
        try {
            user = ServiceFactory.getInstance().getUserService().login(loginInfo);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttributes.LOGIN, login);
                response.sendRedirect(JSPPath.CONTROLLER + USER_INFO_COMMAND);
            } else {
                request.setAttribute(LOGIN_FAIL_ATTRIBUTE, LOGIN_FAIL_MSG);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPath.INDEX);
                requestDispatcher.forward(request, response);
            }
        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
