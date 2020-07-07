<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.registration" var="registration" />
<fmt:message bundle="${loc}" key="local.name" var="name" />
<fmt:message bundle="${loc}" key="local.loginField" var="loginField" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.passportId" var="passportId" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.button.register" var="register" />
<fmt:message bundle="${loc}" key="local.nameTestMSG" var="nameTestMSG" />
<fmt:message bundle="${loc}" key="local.emailTestMSG" var="emailTestMSG" />
<fmt:message bundle="${loc}" key="local.loginTestMSG" var="loginTestMSG" />
<fmt:message bundle="${loc}" key="local.passwordTestMSG" var="passwordTestMSG" />
<fmt:message bundle="${loc}" key="local.passportIdTestMSG" var="passportIdTestMSG" />
<!DOCTYPE html>
<html lang="zxx">


<head>

    <!-- Title -->
    <title>..:: LIBRARIA ::..</title>

    <!-- Meta -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1">

    <!-- Start: Css Section -->
    <jsp:include page="WEB-INF/jsp/parts/css.jsp"/>
    <!-- End: Css Section -->

    <script language="javascript">
        function validate() {
            let valid_name = /^[a-z A-Zа-яА-Я]{4,20}$/;
            let valid_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            let valid_login = /^[a-z A-Zа-яА-Я0-9_]{4,20}$/;
            let valid_password = /^[A-Z a-z0-9]{4,20}$/;
            let valid_passport = /^(?!^0+$)[a-zA-Z0-9]{14,20}$/;


            let name = document.getElementById("name");
            let email = document.getElementById("email");
            let login = document.getElementById("login");
            let password = document.getElementById("password");
            let passport = document.getElementById("passport");


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
            if (!valid_password.test(password.value) || password.value === '') {
                alert("${passwordTestMSG}");
                password.focus();
                password.style.background = '#f08080';
                return false;
            }
            if (!valid_passport.test(passport.value) || passport.value === '') {
                alert("${passportIdTestMSG}");
                passport.focus();
                passport.style.background = '#f08080';
                return false;
            }
        }
    </script>

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="WEB-INF/jsp/parts/header.jsp"/>
<!-- End: Header Section -->

<!-- Start: Page Banner -->
<section class="page-banner services-banner">
    <div class="container">
        <div class="banner-header">
            <h2>${registration}</h2>
            <span class="underline center"></span>
            <p class="lead"></p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li>${registration}</li>
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
                                    <c:if test="${RegistrationSuccessMsg != null}">
                                        <div class="container">
                                            <div class="center-content">
                                                <h1>
                                                    <c:out value="${RegistrationSuccessMsg}"/>
                                                </h1>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${RegisterErrorMsg != null}">
                                        <div class="container">
                                            <div class="center-content">
                                                <h1><c:out value="${RegisterErrorMsg}"/></h1>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="col-md-12">
                                        <div class="contact-form bg-light margin-right">
                                            <h2>${registration}</h2>
                                            <span class="underline left"></span>
                                            <div class="contact-fields">
                                                <form action="Controller" method="post" onsubmit="return validate();">
                                                    <input type="hidden" name="command" value="registration">
                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${name}</b>
                                                                <input class="form-control" type="text" id="name"
                                                                       name="name"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${email}</b>
                                                                <input class="form-control" type="text" id="email"
                                                                       name="email"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${loginField}</b>
                                                                <input class="form-control" type="text" id="login"
                                                                       name="login"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${password}</b>
                                                                <input class="form-control" type="text" id="password"
                                                                       name="password"/>
                                                            </div>
                                                        </div>

                                                        <div class="col-md12 col-sm-12">
                                                            <div class="form-group">
                                                                <b>${passportId}</b>
                                                                <input class="form-control" type="text" id="passport"
                                                                       name="passportId"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-submit">
                                                                <input class="btn btn-default" type="submit"
                                                                       name="submit" value="${register}"/>
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
<jsp:include page="WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>