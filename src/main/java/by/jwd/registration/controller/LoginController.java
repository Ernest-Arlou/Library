package by.jwd.registration.controller;

import by.jwd.registration.bean.Login;
import by.jwd.registration.dao.LoginDao;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //check button click event not null from login.jsp page button
        if(request.getParameter("btn_register").equals("Register")){
            request.getRequestDispatcher("WEB-INF/jsp/welcome.jsp").forward(request,response);
        }
        if (request.getParameter("btn_login") != null) {
            String username = request.getParameter("txt_username"); //get textbox name "txt_username"
            String password = request.getParameter("txt_password"); //get textbox name "txt_password"

            Login login = new Login(); //this class contain seeting up all received values from index.jsp page to setter and getter method for application require effectively

            login.setUsername(username); //set username through loginBean object
            login.setPassword(password); //set password through loginBean object

            LoginDao loginDao = new LoginDao(); //this class contain main logic to perform function calling and database operation

            //send loginBean object values into authorizeLogin() function in LoginDao class
            String authorize = loginDao.authorizeLogin(login);

            //check calling authorizeLogin() function receive string "SUCCESS LOGIN" message after continue process
            if (authorize.equals("SUCCESS LOGIN")) {
                HttpSession session = request.getSession(); //session is created
                session.setAttribute("login", login.getUsername()); //session name is "login" and  store username in "getUsername()" get through loginBean object
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/welcome.jsp"); //redirect to welcome.jsp page
                rd.forward(request, response);
            } else {
                request.setAttribute("WrongLoginMsg", authorize); //wrong login error message is "WrongLoginMsg"
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //show error same index.jsp page
                rd.include(request, response);
            }
        }
    }

}
