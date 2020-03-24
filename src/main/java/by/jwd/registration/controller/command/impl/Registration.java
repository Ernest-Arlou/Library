package by.jwd.registration.controller.command.impl;

import by.jwd.registration.bean.RegistrationInfo;
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
        String firstname = request.getParameter("txt_firstname");
        String lastname = request.getParameter("txt_lastname");
        String username = request.getParameter("txt_username");
        String password = request.getParameter("txt_password");

        RegistrationInfo registrationInfo = new RegistrationInfo();

        registrationInfo.setFirstname(firstname);
        registrationInfo.setLastname(lastname);
        registrationInfo.setUsername(username);
        registrationInfo.setPassword(password);

        String registerValidate = null;
        try {
            registerValidate = ServiceFactory.getInstance().getLibraryService().register(registrationInfo);
            if (registerValidate.equals("You are registered")) {
                request.setAttribute("RegisterSuccessMsg", registerValidate);
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.include(request, response);
            } else {
                request.setAttribute("RegisterErrorMsg", registerValidate);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/registration.jsp");
                rd.include(request, response);
            }
        } catch (ServletException | IOException | ServiceException e) {
            throw new CommandException(e);
        }
    }

}
