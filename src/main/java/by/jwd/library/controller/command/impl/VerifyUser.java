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
import by.jwd.library.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyUser implements Command {

    private static final Logger logger = LoggerFactory.getLogger(VerifyUser.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        SessionCheck.librarianOrAdmin(request);

        String userId = request.getParameter(RequestParameter.USER_ID);
        String localeStr = (String) request.getSession().getAttribute(SessionAttributes.LOCAL);

        try {
            ServiceFactory.getInstance().getUserService().verifyUser(Integer.parseInt(userId));

            response.sendRedirect(CommandURL.USER_VERIFICATION + "&" + RequestAttribute.VERIFICATION_MSG
                    + "=" + LocalMessageCoder.getCodedLocalizedMsg(localeStr, LocalParameter.VERIFICATION_SUCCESS_MSG));

        } catch (IOException e) {
            logger.error("IOException in VerifyUser", e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

    }
}
