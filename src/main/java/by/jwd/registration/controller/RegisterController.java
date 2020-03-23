package by.jwd.registration.controller;

import by.jwd.registration.bean.Register;
import by.jwd.registration.dao.RegisterDao;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //check button click event not null from registration.jsp page button
        if (request.getParameter("btn_register") != null) {
            String firstname = request.getParameter("txt_firstname");
            String lastname = request.getParameter("txt_lastname");
            String username = request.getParameter("txt_username");  //get all textbox name from registration.jsp page
            String password = request.getParameter("txt_password");

            //this class contain  seeting up all received values from registration.jsp page to setter and getter method for application require effectively
            Register register = new Register();

            register.setFirstname(firstname);
            register.setLastname(lastname);
            register.setUsername(username);  //set the all value through registerBean object
            register.setPassword(password);

            RegisterDao registerdao = new RegisterDao(); //this class contain main logic to perform function calling and database operation

            String registerValidate = registerdao.authorizeRegister(register); //send registerBean object values into authorizeRegister() function in RegisterDao class

            //check calling authorizeRegister() function receive "SUCCESS REGISTER" string message after redirect to index.jsp page
            if (registerValidate.equals("SUCCESS REGISTER")) {
                request.setAttribute("RegisterSuccessMsg", registerValidate); //apply register successfully message "RegiseterSuccessMsg"
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //redirect to index.jsp page
                rd.forward(request, response);
            } else {
                request.setAttribute("RegisterErrorMsg", registerValidate); // apply register error message "RegiseterErrorMsg"
                RequestDispatcher rd = request.getRequestDispatcher("registration.jsp"); //show error same page registration.jsp page
                rd.include(request, response);
            }
        }
    }

}
