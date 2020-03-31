package by.jwd.registration.controller.command.impl;

import by.jwd.registration.bean.User;
import by.jwd.registration.controller.JSPPagePath;
import by.jwd.registration.controller.command.Command;
import by.jwd.registration.controller.command.CommandException;
import by.jwd.registration.service.ServiceException;
import by.jwd.registration.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration implements Command {

    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException{
        String name = request.getParameter("txt_name");
        String email = request.getParameter("txt_email");
        String login = request.getParameter("txt_login");
        String password = request.getParameter("txt_password");

        User user = new User(name, email, login, password, false);

        String registerValidate = null;
        try {
            registerValidate = ServiceFactory.getInstance().getLibraryService().register(user);
            if (registerValidate.equals("You are registered")) {
                request.setAttribute("RegisterSuccessMsg", registerValidate);
                RequestDispatcher rd = request.getRequestDispatcher(JSPPagePath.INDEX);
                rd.include(request, response);

            } else {
                request.setAttribute("RegisterErrorMsg", registerValidate);
                RequestDispatcher rd = request.getRequestDispatcher(JSPPagePath.REGISTRATION);
                rd.include(request, response);
            }
        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }

}
