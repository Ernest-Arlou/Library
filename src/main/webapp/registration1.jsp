<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Java MVC Login & Register Script Using MySql</title>
    <script language="javascript">
        function validate() {
            var valid_name = /^[a-z A-Z]+$/; //pattern allowed alphabet a-z or A-Z
            var valid_email =/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; //pattern allowed alphabet a-z or A-Z
            var valid_login = /^[a-z A-Z]+$/; //pattern allowed alphabet a-z or A-Z
            var valid_password = /^[A-Z a-z 0-9]{6,12}$/; //pattern password allowed A to Z, a to z, 0-9 and 6 to 12 range

            var name = document.getElementById("name"); //textbox id fname
            var email = document.getElementById("email"); //textbox id lname
            var login = document.getElementById("login"); //textbox id email
            var password = document.getElementById("password"); //textbox id password

            if (!valid_name.test(name.value) || name.value == '') {//apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Enter name alphabet only....!"); //alert message
                name.focus();
                name.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!valid_email.test(email.value) || email.value == '') {//apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Wrong Email....!"); //alert message
                email.focus();
                email.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!valid_login.test(login.value) || login.value == '') { //apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Enter login alphabet only....!"); //alert message
                login.focus();
                login.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!valid_password.test(password.value) || password.value == '') {//apply if condition using test() method match the parameter for pattern passoword allow 6 to 12 range
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

    <form method="post" action="Controller" onsubmit="return validate();">

        Name <input type="text" name="name" id="name"></br></br>
        Email <input type="text" name="email" id="email"></br></br>
        Login <input type="text" name="login" id="login"></br></br>
        Password <input type="password" name="password" id="password"></br></br>

        <input type="hidden" name="command" value="registration">
        <input type="submit" name="btn_register" value="Register">

        <h3>You have a account? <a href="index1.jsp">Login</a></h3>

    </form>

    <h1 style="color: darkred">
        <b>
            <c:if test = "${RegisterErrorMsg != null}">
            <p><c:out value = "${RegisterErrorMsg}"/><p>
            </c:if>
    </h1>

</div>
</body>
</html>
