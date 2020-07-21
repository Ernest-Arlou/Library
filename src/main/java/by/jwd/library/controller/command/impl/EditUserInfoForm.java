package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.SessionAttributes;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserInfoForm implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.userLoggedIn(request);

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        HttpSession session = request.getSession();

        int userID = (int) session.getAttribute(SessionAttributes.USER_ID);
        User user = null;
        try {
            user = ServiceFactory.getInstance().getUserService().getUserById(userID);
            request.setAttribute(RequestAttribute.USER, user);

            String editMsg = request.getParameter(RequestAttribute.EDIT_USER_INFO_MSG);
            if (editMsg != null) {
                request.setAttribute(RequestAttribute.EDIT_USER_INFO_MSG, editMsg);
            }

            request.getRequestDispatcher(JSPPath.EDIT_USER_INFO_FORM).forward(request, response);

        } catch (ServiceException | ServletException | IOException e) {
            throw new CommandException(e);
        }

    }
}
