package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.SessionAttributes;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserInfoForm implements Command {
    private final static String REQUEST_ATTR_USER = "user";
    private static final String REQUEST_ATTRIBUTE_EDIT = "userInfoEditMSG";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();

        if (session.getAttribute(SessionAttributes.USER_ROLE) == null) {
            throw new CommandException("No Logged User");
        }

        int userID = (int) session.getAttribute(SessionAttributes.USER_ID);
        User user = null;
        try {
            user = ServiceFactory.getInstance().getUserService().getUserById(userID);
            request.setAttribute(REQUEST_ATTR_USER, user);

            String editMsg = request.getParameter(REQUEST_ATTRIBUTE_EDIT);
            if (editMsg != null) {
                request.setAttribute(REQUEST_ATTRIBUTE_EDIT, editMsg);
            }

            request.getRequestDispatcher(JSPPath.EDIT_USER_INFO_FORM).forward(request, response);

        } catch (ServiceException | ServletException | IOException e) {
            throw new CommandException(e);
        }

    }
}
