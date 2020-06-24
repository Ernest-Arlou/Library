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

public class EditUserPassword implements Command {
    private static final String REQUEST_PARAM_NEW_PASSWORD = "new_password";
    private static final String REQUEST_PARAM_OLD_PASSWORD = "old_password";
    private static final String REQUEST_ATTRIBUTE_EDIT_USER_PASS = "editUserPassMSG";
    private static final String WRONG_OLD_PASS_MSG = "Old password does not match";
    private static final String PASS_CHANGED_MSG = "New password has been set";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String newPassword = request.getParameter(REQUEST_PARAM_NEW_PASSWORD);
        String oldPassword = request.getParameter(REQUEST_PARAM_OLD_PASSWORD);

        HttpSession session = request.getSession();

        int userID = (int) session.getAttribute(SessionAttributes.USER_ID);

        if (session.getAttribute(SessionAttributes.USER_ROLE) == null) {
            throw new CommandException("No Logged User");
        }

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User user = userService.getUserById(userID);
            if (!user.getPassword().equalsIgnoreCase(oldPassword)){
                response.sendRedirect(CommandURL.EDIT_USER_PASSWORD_FORM + "&" + REQUEST_ATTRIBUTE_EDIT_USER_PASS + "=" + WRONG_OLD_PASS_MSG);
            }else {
                user.setPassword(newPassword);
                userService.editUser(user);
                response.sendRedirect(CommandURL.EDIT_USER_PASSWORD_FORM + "&" + REQUEST_ATTRIBUTE_EDIT_USER_PASS + "=" + PASS_CHANGED_MSG);
            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
