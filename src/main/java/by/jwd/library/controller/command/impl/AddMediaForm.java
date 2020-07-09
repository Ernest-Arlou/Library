package by.jwd.library.controller.command.impl;

import by.jwd.library.controller.command.Command;
import by.jwd.library.controller.command.CommandException;
import by.jwd.library.controller.command.impl.util.QueryCoder;
import by.jwd.library.controller.constants.JSPPath;
import by.jwd.library.controller.constants.RequestAttribute;
import by.jwd.library.controller.constants.RequestParameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddMediaForm implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {

            request.setAttribute(RequestAttribute.LAST_COMMAND, QueryCoder.code(request.getQueryString()));

            if (request.getParameter(RequestParameter.ADD_MEDIA_MSG) != null){
                request.setAttribute(RequestAttribute.ADD_MEDIA_MSG,request.getParameter(RequestParameter.ADD_MEDIA_MSG));
            }

            request.getRequestDispatcher(JSPPath.ADD_MEDIA).forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }
    }
}
