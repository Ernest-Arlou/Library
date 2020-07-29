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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePassportId implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ChangePassportId.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.librarianOrAdmin(request);

        int userId = Integer.parseInt(request.getParameter(RequestParameter.USER_ID));
        String passportId = request.getParameter(RequestParameter.PASSPORT_ID);
        String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (userService.passportIdExists(passportId)) {

                response.sendRedirect(CommandURL.USER_VERIFICATION + "&" + RequestAttribute.VERIFICATION_MSG
                        + "=" + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.PASSPORT_ID_EXISTS_MSG));

            } else {

                userService.changePassportId(userId, passportId);

                response.sendRedirect(CommandURL.USER_VERIFICATION + "&" + RequestAttribute.VERIFICATION_MSG
                        + "=" + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.PASSPORT_ID_CHANGED_MSG));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in ChangePassportId", e);
            throw new CommandException(e);
        }
    }
}
