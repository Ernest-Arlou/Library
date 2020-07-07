package by.jwd.library.controller.command.impl;

import by.jwd.library.bean.User;
import by.jwd.library.controller.constants.local.LocalParameter;
import by.jwd.library.controller.command.impl.util.LocalMessageCoder;
import by.jwd.library.controller.constants.*;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration implements Command {

    private static final String USER_ROLE_USER = "user";
    private static final String USER_STATUS_UNVERIFIED = "Unverified";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String name = request.getParameter(RequestParameter.NAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String passportId = request.getParameter(RequestParameter.PASSPORT_ID);

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setPassportId(passportId);
        user.setRole(USER_ROLE_USER);
        user.setStatus(USER_STATUS_UNVERIFIED);

        UserService userService = ServiceFactory.getInstance().getUserService();

        String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);

        try {
            if (userService.emailExists(email)) {

                response.sendRedirect(CommandURL.REGISTRATION_FORM + "&" + RequestAttribute.REGISTRATION_FAIL_MSG
                        + "=" + LocalMessageCoder.getCodedLocalizedMsg(localeStr,LocalParameter.EMAIL_EXISTS_MSG));

            } else if (userService.loginExists(login)) {
                response.sendRedirect(CommandURL.REGISTRATION_FORM + "&" + RequestAttribute.REGISTRATION_FAIL_MSG
                        + "=" +  LocalMessageCoder.getCodedLocalizedMsg(localeStr,LocalParameter.LOGIN_EXISTS_MSG));

            } else if (userService.passportIdExists(passportId)) {
                response.sendRedirect(CommandURL.REGISTRATION_FORM + "&" + RequestAttribute.REGISTRATION_FAIL_MSG
                        + "=" +  LocalMessageCoder.getCodedLocalizedMsg(localeStr,LocalParameter.PASSPORT_ID_EXISTS_MSG));

            } else {
                ServiceFactory.getInstance().getUserService().register(user);

                response.sendRedirect(CommandURL.REGISTRATION_FORM + "&" + RequestAttribute.REGISTRATION_SUCCESS_MSG
                        + "=" +  LocalMessageCoder.getCodedLocalizedMsg(localeStr,LocalParameter.REGISTRATION_SUCCESS_MSG));
            }

        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }

}
