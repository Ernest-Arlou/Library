package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.LocalMessageCoder;
import by.jwd.library.controller.constant.CommandURL;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.controller.constant.SessionAttributes;
import by.jwd.library.controller.constant.local.LocalParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogIn implements Command {

    private void loginFailRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);
        response.sendRedirect(CommandURL.LOGIN_FORM + "&" + RequestAttribute.LOGIN_FAIL_MSG
                + "=" + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.LOGIN_FAIL_MSG));
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);

        User user = null;
        try {
            user = ServiceFactory.getInstance().getUserService().login(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttributes.LOGIN, user.getLogin());
                session.setAttribute(SessionAttributes.USER_ID, user.getUserId());
                session.setAttribute(SessionAttributes.USER_ROLE, user.getRole());
                response.sendRedirect(CommandURL.PROFILE);
            } else {

                loginFailRedirect(request, response);

            }
        } catch (ServiceException e) {
            try {
                loginFailRedirect(request, response);
            } catch (IOException ioException) {
                throw new CommandException(e);
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
