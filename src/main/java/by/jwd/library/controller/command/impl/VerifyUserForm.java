package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constant.JSPPath;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VerifyUserForm implements Command {

    private static final Logger logger = LoggerFactory.getLogger(VerifyUserForm.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

        SessionCheck.librarianOrAdmin(request);

        String search = request.getParameter(RequestParameter.SEARCH);
        String verificationMsg = request.getParameter(RequestAttribute.VERIFICATION_MSG);
        try {
            List<User> users;
            if (search != null) {
                users = ServiceFactory.getInstance().getUserService().searchUnverifiedUsers(search);
            } else {
                users = ServiceFactory.getInstance().getUserService().getUnverifiedUsers();
            }
            request.setAttribute(RequestAttribute.UNVERIFIED_USERS, users);

            if (verificationMsg != null) {
                request.setAttribute(RequestAttribute.VERIFICATION_MSG, verificationMsg);
            }
            request.getRequestDispatcher(JSPPath.USER_VERIFICATION).forward(request, response);
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in VerifyUserForm", e);
            throw new CommandException(e);
        } catch (ServletException e) {
            logger.error("ServletException in VerifyUserForm", e);
            throw new CommandException(e);
        }
    }
}
