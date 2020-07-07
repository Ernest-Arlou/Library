<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.logIn" var="logIn" />
<fmt:message bundle="${loc}" key="local.logInProcedure" var="logInProcedure" />
<fmt:message bundle="${loc}" key="local.loginField" var="loginField" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.enterLogin" var="enterLogin" />
<fmt:message bundle="${loc}" key="local.enterPassword" var="enterPassword" />
<html lang="zxx">

<head>
    <!-- Title -->
    <title>..:: LIBRARIA ::..</title>

    <!-- Meta -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1">

    <!-- Start: Css Section -->
    <jsp:include page="/WEB-INF/jsp/parts/css.jsp"/>
    <!-- End: Css Section -->

    <script>
        function validate() {
            let username = document.login_form.login;
            let password = document.login_form.password;

            if (username.value == null || username.value === "") {
                window.alert("${enterLogin}");
                return false;
            }
            if (password.value == null || password.value === "") {
                window.alert("${enterPassword}");
                return false;
            }
        }
    </script>

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="/WEB-INF/jsp/parts/header.jsp"/>
<!-- End: Header Section -->

<!-- Start: Page Banner -->
<section class="page-banner services-banner">
    <div class="container">
        <div class="banner-header">
            <h2>${logInProcedure}</h2>
            <span class="underline center"></span>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li>${logInProcedure}</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->

<!-- Start: Login Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="signin-main">
                <div class="container">
                    <div class="woocommerce">
                        <div class="woocommerce-login">
                            <div class="company-info signin-register">
                                <c:if test = "${LoginFailMsg != null}">
                                <div class="center-content">
                                    <h3 class="section-title"><c:out value = "${LoginFailMsg}"/></h3>
                                </div>
                                </c:if>
                                <div class="col-md-5 col-md-offset-4">
                                    <div class="row">
                                        <div class="company-detail bg-dark margin-left">
                                            <div class="signin-head">
                                                <h2>${logInProcedure}</h2>
                                                <span class="underline left"></span>
                                            </div>
                                            <form class="login" method="post" action="Controller" name="login_form" onsubmit="return validate();">
                                                <p class="form-row form-row-first input-required">
                                                <label>
                                                    <span class="first-letter">${loginField}</span>
                                                    <span class="second-letter">*</span>
                                                </label>
                                                <input type="text" name="login">
                                                </p>
                                                <p class="form-row form-row-last input-required">
                                                <label>
                                                    <span class="first-letter">${password}</span>
                                                    <span class="second-letter">*</span>
                                                </label>
                                                <input type="password" name="password">
                                                </p>
                                                <input type="hidden" name="command" value="login">
                                                <div class="clear"></div>
                                                <input type="submit" name="log_button" value="${logIn}" class="button btn btn-default">
                                                <div class="clear"></div>
                                            </form>
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
<jsp:include page="/WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="/WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>

