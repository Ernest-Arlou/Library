<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.profile" var="profile" />
<fmt:message bundle="${loc}" key="local.editProfilePage" var="editProfilePage" />
<fmt:message bundle="${loc}" key="local.nameTestMSG" var="nameTestMSG" />
<fmt:message bundle="${loc}" key="local.emailTestMSG" var="emailTestMSG" />
<fmt:message bundle="${loc}" key="local.loginTestMSG" var="loginTestMSG" />
<fmt:message bundle="${loc}" key="local.name" var="name" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.loginField" var="loginField" />
<fmt:message bundle="${loc}" key="local.editProfile" var="editProfile" />


<!DOCTYPE html>
<html lang="zxx">


<head>

    <!-- Title -->
    <title>..:: LIBRARIA ::..</title>

    <!-- Meta -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1">

    <!-- Start: Css Section -->
    <jsp:include page="parts/css.jsp"/>
    <!-- End: Css Section -->

    <script>
        function validate() {
            let valid_name = /^[a-z A-Zа-яА-Я]{4,20}$/;
            let valid_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            let valid_login = /^[a-zA-Z0-9_-]{4,20}$/;

            let name = document.getElementById("name");
            let email = document.getElementById("email");
            let login = document.getElementById("login");

            if (!valid_name.test(name.value) || name.value === '') {
                alert("${nameTestMSG}");
                name.focus();
                name.style.background = '#f08080';
                return false;
            }
            if (!valid_email.test(email.value) || email.value === '') {
                alert("${emailTestMSG}");
                email.focus();
                email.style.background = '#f08080';
                return false;
            }
            if (!valid_login.test(login.value) || login.value === '') {
                alert("${loginTestMSG}");
                login.focus();
                login.style.background = '#f08080';
                return false;
            }
        }
    </script>

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="parts/header.jsp"/>
<!-- End: Header Section -->

<!-- Start: Page Banner -->
<section class="page-banner services-banner">
    <div class="container">
        <div class="banner-header">
            <h2>${editProfilePage}</h2>
            <span class="underline center"></span>
            <p class="lead"></p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li><a href="${pageContext.request.contextPath}/Controller?command=profile">${profile}</a></li>
                <li>${editProfilePage}</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->

<!-- Start: Login Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="contact-main">
                <div class="contact-us">
                    <div class="container">
                        <div class="row">
                            <div class="contact-area">
                                <div class="container">
                                    <c:if test="${requestScope.userInfoEditMSG != null}">
                                        <div class="center-content">
                                            <div class="clear"></div>
                                            <br>
                                            <h3 class="section-title"><c:out value="${userInfoEditMSG}"/></h3>
                                        </div>
                                    </c:if>
                                    <jsp:useBean id="user" scope="request" type="by.jwd.library.bean.User"/>
                                    <div class="col-md-12">
                                        <div class="contact-form bg-light margin-right">
                                            <h2>${editProfilePage}</h2>
                                            <span class="underline left"></span>
                                            <div class="contact-fields">
                                                <form action="Controller" method="post" onsubmit="return validate();">
                                                    <input type="hidden" name="command" value="edit_user_info">
                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${name}</b>
                                                                <input class="form-control" type="text" id="name"
                                                                       name="name" value="${user.name}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${email}</b>
                                                                <input class="form-control" type="text" id="email"
                                                                       name="email" value="${user.email}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${loginField}</b>
                                                                <input class="form-control" type="text" id="login"
                                                                        name="login" value="${user.login}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-submit">
                                                                <input class="btn btn-default" type="submit"
                                                                       name="submit" value="${editProfile}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<!-- End: Login Section -->

<!-- Start: Footer -->
<jsp:include page="parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>