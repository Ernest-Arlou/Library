package by.jwd.registration.controller.command.impl;

import by.jwd.registration.controller.command.Command;
import by.jwd.registration.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogIn implements Command {

    @Override
    public void execute (HttpServletRequest request, HttpServletResponse response){
        System.out.println("Login");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String authorize = ServiceFactory.getInstance().getLibraryService().login(login,password);

        if (authorize.equals("SUCCESS LOGIN")) {
            HttpSession session = request.getSession(); //session is created
            session.setAttribute("login", login); //session name is "login" and  store username in "getUsername()" get through loginBean object
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/welcome.jsp"); //redirect to welcome.jsp page
            try {
                requestDispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("WrongLoginMsg", authorize); //wrong login error message is "WrongLoginMsg"
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp"); //show error same index.jsp page
            try {
                requestDispatcher.include(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
