package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.LocalMessageCoder;
import by.jwd.library.controller.command.impl.util.SessionCheck;
import by.jwd.library.controller.constant.CommandURL;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.controller.constant.SessionAttributes;
import by.jwd.library.controller.constant.local.LocalParameter;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserPassword implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.userLoggedIn(request);

        String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
        String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);

        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute(SessionAttributes.USER_ID);
        UserService userService = ServiceFactory.getInstance().getUserService();
        String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);
        try {

            if (!userService.changePassword(userID, oldPassword, newPassword)) {
                response.sendRedirect(CommandURL.EDIT_USER_PASSWORD_FORM + "&" + RequestAttribute.EDIT_USER_PASSWORD_MSG + "="
                        + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.WRONG_OLD_PASS_MSG));
            } else {
                response.sendRedirect(CommandURL.EDIT_USER_PASSWORD_FORM + "&" + RequestAttribute.EDIT_USER_PASSWORD_MSG + "="
                        + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.NEW_PASSWORD_SET));
            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
