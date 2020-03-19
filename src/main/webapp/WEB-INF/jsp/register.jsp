<%
    if (session.getAttribute("login") != null) {
        response.sendRedirect("index.jsp");
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
            var first_name = /^[a-z A-Z]+$/; //pattern allowed alphabet a-z or A-Z
            var last_name = /^[a-z A-Z]+$/; //pattern allowed alphabet a-z or A-Z
            var user_name = /^[a-z A-Z]+$/; //pattern allowed alphabet a-z or A-Z
            var password_valid = /^[A-Z a-z 0-9]{6,12}$/; //pattern password allowed A to Z, a to z, 0-9 and 6 to 12 range

            var fname = document.getElementById("fname"); //textbox id fname
            var lname = document.getElementById("lname"); //textbox id lname
            var uname = document.getElementById("uname"); //textbox id email
            var password = document.getElementById("password"); //textbox id password

            if (!first_name.test(fname.value) || fname.value == '') {//apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Enter Firstname Alphabet Only....!"); //alert message
                fname.focus();
                fname.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!last_name.test(lname.value) || lname.value == '') {//apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Enter Lastname Alphabet Only....!"); //alert message
                lname.focus();
                lname.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!user_name.test(uname.value) || uname.value == '') { //apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Enter Username Alphabet Only....!"); //alert message
                uname.focus();
                uname.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!password_valid.test(password.value) || password.value == '') {//apply if condition using test() method match the parameter for pattern passoword allow 6 to 12 range
                alert("Password Must Be 6 to 12 character"); //alert message
                password.focus();
                password.style.background = '#f08080'; //set textbox color
                return false;
            }
        }
    </script>
</head>
<body>

<div style="text-align: center;">

    <h2>Register</h2>

    <form method="post" action="RegisterController" onsubmit="return validate();">

        Firstname <input type="text" name="txt_firstname" id="fname"></br></br>
        Lastname <input type="text" name="txt_lastname" id="lname"></br></br>
        Username <input type="text" name="txt_username" id="uname"></br></br>
        Password <input type="password" name="txt_password" id="password"></br></br>

        <input type="submit" name="btn_register" value="Register">

        <h3>You have a account? <a href="index.jsp">Login</a></h3>

    </form>

    <h3 style="color:red">
        <%
            if (request.getAttribute("RegisterErrorMsg") != null) {
                out.println(request.getAttribute("RegisterErrorMsg")); //get register fail error message from "RegisterController"
            }
        %>
    </h3>

</div>
</body>
</html>
