<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>

    <h2>
    <c:out value="${login}, Hello!"/>
    </h2>



</head>
<body>

    <jsp:useBean id="user" scope="request" type="by.jwd.registration.bean.User"/>
    Your name: <c:out value="${user.name}"/> <br>
    Your email: <c:out value="${user.email}"/> <br>
    Your login: <c:out value="${user.login}"/> <br>

</body>

<h3>
    <form method="post" action="Controller" name="welcome">
        <input type="hidden" name="command" value="log_out">
        <input type="submit" name="logout_button" value="Logout">
    </form>
</h3>
</html>
