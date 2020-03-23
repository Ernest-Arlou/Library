<%
    if (session.getAttribute("login") != null) {
        response.sendRedirect("/WEB-INF/jsp/welcome.jsp"); //session login user not back to index.jsp page or not type direct in url
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Java MVC Login & Register Script Using MySql</title>
    <script language="javascript">
        function validate() {
            var username = document.LoginForm.txt_username; //get form name "LoginForm" and textbox name "txt_username" store in variable username
            var password = document.LoginForm.txt_password; //get form name "LoginForm" and textbox name "txt_password" store in variable password

            if (username.value == null || username.value == "") {//check username textbox not blank
                window.alert("please enter username ?"); //alert message
                username.style.background = '#f08080'; //set textbox color
                username.focus();
                return false;
            }
            if (password.value == null || password.value == "") {//check password textbox not blank
                window.alert("please enter password ?"); //alert message
                password.style.background = '#f08080'; //set textbox color
                password.focus();
                return false;
            }
        }
    </script>
</head>
<body>

<div style="text-align: center;">

    <h2>Login</h2>

    <form method="post" action="Controller" name="login_form" onsubmit="return validate();">

        Login :<input type="text" name="login">
        Password :<input type="password" name="password">

        <input type="submit" name="command" value="log_in">


    </form>

    <h3>Your don't have an account? <a href="WEB-INF/jsp/registration.jsp">Registration</a></h3>

    <form method="post" action="LoginController" name="LoginForm" onsubmit="return validate();">
    <input type="submit" name="btn_register" value="Register">
    </form>

    <h3 style="color:green">
        <%
            if (request.getAttribute("RegiseterSuccessMsg") != null) {
                out.println(request.getAttribute("RegiseterSuccessMsg")); //get register success message from RegisterController
            }
        %>
    </h3>

    <h3 style="color:red">
        <%
            if (request.getAttribute("WrongLoginMsg") != null) {
                out.println(request.getAttribute("WrongLoginMsg")); //get login error message from LoginController
            }
        %>
    </h3>

</div>
</body>
</html>

