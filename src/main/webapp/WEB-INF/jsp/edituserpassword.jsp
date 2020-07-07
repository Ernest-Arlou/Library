<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />
<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.profile" var="profile" />
<fmt:message bundle="${loc}" key="local.passwordTestMSG" var="passwordTestMSG" />
<fmt:message bundle="${loc}" key="local.newPassword" var="newPassword" />
<fmt:message bundle="${loc}" key="local.oldPassword" var="oldPassword" />
<fmt:message bundle="${loc}" key="local.oldPasswordNotMatch" var="oldPasswordNotMatch" />
<fmt:message bundle="${loc}" key="local.editPasswordPage" var="editPasswordPage" />
<fmt:message bundle="${loc}" key="local.edit" var="edit" />


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
            let valid_password = /^[A-Z a-z0-9]{4,12}$/;

            let password = document.getElementById("new_password");
            let oldPassword = document.getElementById("old_password");
            let oldPassword1 = document.getElementById("old_password1");

            if (oldPassword.value !== oldPassword1.value){
                alert("${oldPasswordNotMatch}");
                return false;
            }

            if (!valid_password.test(password.value) || password.value === '') {
                alert("${passwordTestMSG}");
                password.focus();
                password.style.background = '#f08080';
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
            <h2>${editPasswordPage}</h2>
            <span class="underline center"></span>
            <p class="lead"></p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li><a href="${pageContext.request.contextPath}/Controller?command=profile">${profile}</a></li>
                <li>${editPasswordPage}</li>
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
                                    <c:if test="${requestScope.editUserPassMSG != null}">
                                        <div class="center-content">
                                            <div class="clear"></div>
                                            <br>
                                            <h3 class="section-title"><c:out value="${editUserPassMSG}"/></h3>
                                        </div>
                                    </c:if>
                                    <div class="col-md-12">
                                        <div class="contact-form bg-light margin-right">
                                            <h2>${editPasswordPage}</h2>
                                            <span class="underline left"></span>
                                            <div class="contact-fields">
                                                <form action="Controller" method="post" onsubmit="return validate();">
                                                    <input type="hidden" name="command" value="edit_user_password">
                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${oldPassword}</b>
                                                                <input class="form-control" type="password" id="old_password"
                                                                        name="oldPassword" />
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${oldPassword}</b>
                                                                <input class="form-control" type="password" id="old_password1"
                                                                        name="oldPassword1" />
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${newPassword}</b>
                                                                <input class="form-control" type="password" id="new_password"
                                                                        name="newPassword" />
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-submit">
                                                                <input class="btn btn-default" type="submit"
                                                                       name="submit" value="${edit}"/>
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