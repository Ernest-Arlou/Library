package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.CommandURL;
import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.service.ServiceException;
import by.jwd.library.service.UserService;
import by.jwd.library.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePassportId implements Command {
    private final static String REQUEST_PARAM_USER_ID = "userId";
    private static final String REQUEST_PARAM_PASSPORT_ID = "passport-id";
    private final static String REQUEST_ATTR_VERIFICATION_MSG = "verificationMsg";
    private final static String CHANGE_SUCCESS_MSG = "Passport id changed!";
    private final static String PASSPORT_ID_EXISTS_MSG = "User with this passport id already exists!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int userId = Integer.parseInt(request.getParameter(REQUEST_PARAM_USER_ID));
        String passportId = request.getParameter(REQUEST_PARAM_PASSPORT_ID);

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (userService.passportIdExists(passportId)){
                response.sendRedirect(CommandURL.USER_VERIFICATION + "&" + REQUEST_ATTR_VERIFICATION_MSG + "=" + PASSPORT_ID_EXISTS_MSG);
            }else {
                userService.changePassportId(userId,passportId);
                response.sendRedirect(CommandURL.USER_VERIFICATION + "&" + REQUEST_ATTR_VERIFICATION_MSG + "=" + CHANGE_SUCCESS_MSG);
            }
        } catch (ServiceException | IOException e) {
           throw new CommandException(e);
        }

    }
}
