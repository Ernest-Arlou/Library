package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.CommandURL;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyUser implements Command {
    private static final String REQUEST_PARAM_USER_ID = "user_id";
    private final static String REQUEST_ATTR_VERIFICATION_MSG = "verificationMsg";
    private final static String VERIFICATION_SUCCESS_MSG = "User Verified!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String userId = request.getParameter(REQUEST_PARAM_USER_ID);
        try {
            ServiceFactory.getInstance().getUserService().verifyUser(Integer.parseInt(userId));
            response.sendRedirect(CommandURL.USER_VERIFICATION + "&" + REQUEST_ATTR_VERIFICATION_MSG + "=" + VERIFICATION_SUCCESS_MSG);
        } catch (IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }
}
