package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.JSPPath;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserVerification implements Command {
    private final static String REQUEST_ATTR_UNVERIFIED_USERS = "unverified_users";
    private final static String REQUEST_ATTR_VERIFICATION_MSG = "verificationMsg";
    private final static String REQUEST_PARAM_SEARCH = "search";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String search = request.getParameter(REQUEST_PARAM_SEARCH);
        String verificationMsg = request.getParameter(REQUEST_ATTR_VERIFICATION_MSG);
        try {
            List<User> users;
            if (search != null){
                users = ServiceFactory.getInstance().getUserService().searchUnverifiedUsers(search);
            }else {
                users = ServiceFactory.getInstance().getUserService().getUnverifiedUsers();
            }
            request.setAttribute(REQUEST_ATTR_UNVERIFIED_USERS, users);

            if (verificationMsg != null) {
                request.setAttribute(REQUEST_ATTR_VERIFICATION_MSG, verificationMsg);
            }
            request.getRequestDispatcher(JSPPath.USER_VERIFICATION).forward(request, response);
        } catch (ServiceException|IOException|ServletException e) {
            throw new CommandException(e);
        }
    }
}
