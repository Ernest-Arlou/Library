package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constant.CommandURL;
import by.jwd.library.controller.constant.RequestAttribute;
import by.jwd.library.controller.constant.RequestParameter;
import by.jwd.library.controller.constant.SessionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocale implements Command {

    private static final Logger logger = LoggerFactory.getLogger(ChangeLocale.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.getSession().setAttribute(SessionAttributes.LOCAL, request.getParameter(RequestParameter.LOCAL));

        String lastCommand = request.getParameter(RequestParameter.LAST_COMMAND);


        try {
            if (lastCommand == null || lastCommand.isEmpty()) {
                response.sendRedirect(CommandURL.CONTROLLER);
                return;
            }

            String lastPage = request.getParameter(RequestParameter.LAST_PAGE);

            if (lastPage == null || lastPage.isEmpty()) {
                response.sendRedirect(CommandURL.CONTROLLER + "?" + lastCommand);
            } else {
                lastCommand = lastCommand.substring(0, lastCommand.indexOf(RequestParameter.LAST_PAGE));
                response.sendRedirect(CommandURL.CONTROLLER + "?" + lastCommand
                        + "&" + RequestAttribute.LAST_PAGE + "="
                        + QueryCoder.code(
                        request.getParameter(RequestParameter.LAST_PAGE) + "&" + RequestParameter.PAGE + "=" + request.getParameter(RequestParameter.PAGE) +
                                "&" + RequestParameter.SEARCH + "=" + request.getParameter(RequestParameter.SEARCH)));

            }

        } catch (IOException e) {
            logger.error("ServletException in ChangeLocale", e);
            throw new CommandException(e);
        }

    }
}
