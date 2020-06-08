<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local/local" var="loc" />
    <fmt:message bundle="${loc}" key="local.message" var="message" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru"
                 var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en"
                 var="en_button" />

<%--    <c:out value="${message}" />--%>
<%--    <c:out value="${sessionScope.local}" />--%>

    <script language="javascript">
        function validate() {
            var username = document.login_form.login; //get form name "LoginForm" and textbox name "txt_username" store in variable username
            var password = document.login_form.password; //get form name "LoginForm" and textbox name "txt_password" store in variable password

            if (username.value == null || username.value == "") {//check username textbox not blank
                window.alert("please enter login !"); //alert message
                username.style.background = '#f08080'; //set textbox color
                username.focus();
                return false;
            }
            if (password.value == null || password.value == "") {//check password textbox not blank
                window.alert("please enter password !"); //alert message
                password.style.background = '#f08080'; //set textbox color
                password.focus();
                return false;
            }
        }
    </script>
</head>
<body>



<div style="text-align: center;">

    <form action="Controller" method="post">
        <input type="hidden" name="command" value="switch_locale" />
        <input type="hidden" name="local" value="ru_RU" />
        <input type="submit" value="${ru_button}" /><br />
    </form>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="switch_locale" />
        <input type="hidden" name="local" value="en_US" />
        <input type="submit" value="${en_button}" /><br />
    </form>

    <h2>Login</h2>

    <form method="post" action="Controller" name="login_form" onsubmit="return validate();">
        Login :<input type="text" name="login">
        Password :<input type="password" name="password">
        <input type="hidden" name="command" value="login">
        <input type="submit" name="log_button" value="Login">
    </form>

    <h3>Your don't have an account?</h3>
    <a href="registration.jsp"> Registration</a>

    <h1 style="color:aquamarine">
        <b>
    <c:if test = "${RegisterSuccessMsg != null}">
    <p><c:out value = "${RegisterSuccessMsg}"/><p>
    </c:if>
    </h1>

    <h1 style="color: darkred">
    <c:if test = "${LoginFailMsg != null}">
    <p><c:out value = "${LoginFailMsg}"/><p>
    </c:if>
        </b>
    </h1>

</div>
</body>
</html>

