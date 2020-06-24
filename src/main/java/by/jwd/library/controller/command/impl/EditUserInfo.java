package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.CommandURL;
import by.jwd.library.controller.SessionAttributes;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserInfo implements Command {
    private static final String REQUEST_PARAM_NAME = "name";
    private static final String REQUEST_PARAM_EMAIL = "email";
    private static final String REQUEST_PARAM_LOGIN = "login";
    private static final String REQUEST_ATTRIBUTE_EDIT = "userInfoEditMSG";
    private static final String EMAIL_EXISTS_MSG = "User with this email already exists!";
    private static final String LOGIN_EXISTS_MSG = "User with this login already exists!";
    private static final String EDIT_SUCCESS_MSG = "Info updated";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String name = request.getParameter(REQUEST_PARAM_NAME);
        String email = request.getParameter(REQUEST_PARAM_EMAIL);
        String login = request.getParameter(REQUEST_PARAM_LOGIN);


        HttpSession session = request.getSession();

        int userID = (int) session.getAttribute(SessionAttributes.USER_ID);

        if (session.getAttribute(SessionAttributes.USER_ROLE) == null) {
            throw new CommandException("No Logged User");
        }

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {

            User user = userService.getUserByEmail(email);
            if (user != null && user.getUserId() != userID) {
                response.sendRedirect(CommandURL.EDIT_USER_INFO_FORM + "&" + REQUEST_ATTRIBUTE_EDIT + "=" + EMAIL_EXISTS_MSG);
            } else {

                user = userService.getUserByLogin(login);
                if (user != null && user.getUserId() != userID) {
                    response.sendRedirect(CommandURL.EDIT_USER_INFO_FORM + "&" + REQUEST_ATTRIBUTE_EDIT + "=" + LOGIN_EXISTS_MSG);

                } else {
                    user = userService.getUserById(userID);
                    user.setName(name);
                    user.setEmail(email);
                    user.setLogin(login);

                    userService.editUser(user);

                    response.sendRedirect(CommandURL.EDIT_USER_INFO_FORM + "&" + REQUEST_ATTRIBUTE_EDIT + "=" + EDIT_SUCCESS_MSG);
                }
            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
