<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <script language="javascript">
        function validate() {
            let valid_name = /^[a-z A-Z]{4,12}$/;
            let valid_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            let valid_login = /^[a-z A-Z0-9_]{4,12}$/;
            let valid_password = /^[A-Z a-z0-9]{4,12}$/;
            let valid_passport = /^(?!^0+$)[a-zA-Z0-9]{9,20}$/;


            let name = document.getElementById("name");
            let email = document.getElementById("email");
            let login = document.getElementById("login");
            let password = document.getElementById("password");
            let passport = document.getElementById("passport");


            if (!valid_name.test(name.value) || name.value == '') {//apply if condition using test() method match the parameter for pattern allow alphabet only
                alert("Enter name alphabet only 4 to 12 characters!"); //alert message
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
                alert("Login must be 4 to 12 characters!"); //alert message
                login.focus();
                login.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!valid_password.test(password.value) || password.value == '') {//apply if condition using test() method match the parameter for pattern passoword allow 6 to 12 range
                alert("Password Must Be 4 to 12 characters"); //alert message
                password.focus();
                password.style.background = '#f08080'; //set textbox color
                return false;
            }
            if (!valid_passport.test(passport.value) || passport.value == '') {
                alert("Passport ID is incorrect");
                passport.focus();
                passport.style.background = '#f08080';
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
            <h2>User registration</h2>
            <span class="underline center"></span>
            <p class="lead"></p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">Home</a></li>
                <li>User registration</li>
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
                                            <h2>Library user registration</h2>
                                            <span class="underline left"></span>
                                            <div class="contact-fields">
                                                <form action="Controller" method="post" onsubmit="return validate();">
                                                    <input type="hidden" name="command" value="USER_REGISTRATION">
                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <input class="form-control" type="text" id="name"
                                                                       placeholder="Name" name="name"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <input class="form-control" type="text" id="email"
                                                                       placeholder="Email" name="email"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <input class="form-control" type="text" id="login"
                                                                       placeholder="Login" name="login"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <input class="form-control" type="text" id="password"
                                                                       placeholder="Password" name="password"/>
                                                            </div>
                                                        </div>

                                                        <div class="col-md12 col-sm-12">
                                                            <div class="form-group">
                                                                <input class="form-control" type="text" id="passport"
                                                                       placeholder="PassportID" name="passport-id"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-12">
                                                            <div class="form-group form-submit">
                                                                <input class="btn btn-default" type="submit"
                                                                       name="submit" value="Register user"/>
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