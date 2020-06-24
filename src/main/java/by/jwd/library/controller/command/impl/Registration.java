package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.dao.DAOException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration implements Command {
    private static final String REQUEST_PARAM_NAME = "name";
    private static final String REQUEST_PARAM_EMAIL = "email";
    private static final String REQUEST_PARAM_LOGIN = "login";
    private static final String REQUEST_PARAM_PASSWORD = "password";
    private static final String REQUEST_PARAM_PASSPORT_ID = "passport-id";
    private static final String REQUEST_ATTRIBUTE_REGISTRATION_FAIL = "RegisterErrorMsg";
    private static final String REQUEST_ATTRIBUTE_REGISTRATION_SUCCESS = "RegistrationSuccessMsg";
    private static final String EMAIL_EXISTS_MSG = "User with this email already exists!";
    private static final String LOGIN_EXISTS_MSG = "User with this login already exists!";
    private static final String PASSPORT_ID_EXISTS_MSG = "User with this passportID already exists!";
    private static final String REGISTRATION_SUCCESS_MSG = "User registered!";
    private static final String USER_ROLE_USER = "user";
    private static final String USER_STATUS_UNVERIFIED = "Unverified";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String name = request.getParameter(REQUEST_PARAM_NAME);
        String email = request.getParameter(REQUEST_PARAM_EMAIL);
        String login = request.getParameter(REQUEST_PARAM_LOGIN);
        String password = request.getParameter(REQUEST_PARAM_PASSWORD);
        String passportId = request.getParameter(REQUEST_PARAM_PASSPORT_ID);

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setPassportId(passportId);
        user.setRole(USER_ROLE_USER);
        user.setStatus(USER_STATUS_UNVERIFIED);

        UserService userService = ServiceFactory.getInstance().getUserService();
        RequestDispatcher requestDispatcher = null;
        try {
            if (userService.emailExists(email)) {
                request.setAttribute(REQUEST_ATTRIBUTE_REGISTRATION_FAIL, EMAIL_EXISTS_MSG);
                requestDispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION);
                requestDispatcher.forward(request, response);
            } else if (userService.loginExists(login)) {
                request.setAttribute(REQUEST_ATTRIBUTE_REGISTRATION_FAIL, LOGIN_EXISTS_MSG);
                requestDispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION);
                requestDispatcher.forward(request, response);
            } else if (userService.passportIdExists(passportId)) {
                request.setAttribute(REQUEST_ATTRIBUTE_REGISTRATION_FAIL, PASSPORT_ID_EXISTS_MSG);
                requestDispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION);
                requestDispatcher.forward(request, response);
            } else {
                ServiceFactory.getInstance().getUserService().register(user);
                request.setAttribute(REQUEST_ATTRIBUTE_REGISTRATION_SUCCESS, REGISTRATION_SUCCESS_MSG);
                requestDispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION);
                requestDispatcher.forward(request, response);
            }


        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }

}
