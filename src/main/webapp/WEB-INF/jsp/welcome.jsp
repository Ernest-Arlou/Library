<div style="text-align: center;">

    <h2>
<%--        <%--%>
<%--            if (session.getAttribute("login") == null || session.getAttribute("login") == "") {//check if condition for unauthorize user not direct access welcome.jsp page--%>
<%--                response.sendRedirect("index.jsp");--%>
<%--            }--%>
<%--        %>--%>

        Welcome, <%=session.getAttribute("login")%>
    </h2>

    <h3>
        <a>Logout</a>
        <form method="post" action="Controller" name="welcome">
            <input type="hidden" name="command" value="redirect">
            <input type="hidden" name="redirect" value="logout">
            <input type="submit" name="logout_button" value="Logout">
        </form>
    </h3>


</div>

