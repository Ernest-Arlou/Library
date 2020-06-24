<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="zxx">

<head>
    <!-- Title -->
    <title>..:: LIBRARIA ::..</title>

    <!-- Meta -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1">

    <!-- Start: Css Section -->
    <jsp:include page="/WEB-INF/jsp/parts/css.jsp"/>
    <!-- End: Css Section -->

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="/WEB-INF/jsp/parts/header.jsp"/>
<!-- End: Header Section -->

<!-- Start: Page Banner -->
<section class="page-banner services-banner">
    <div class="container">
        <div class="banner-header">
            <h2>User verification</h2>
            <span class="underline center"></span>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">Home</a></li>
                <li>User verification</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->

<!-- Start: User verification Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="cart-main">
                <div class="container">
                    <section class="search-filters">
                        <div class="filter-box">
                            <h3>User search</h3>
                            <form action="Controller" method="get">
                                <div class="col-md-10 col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Search by Email, Login or Passport ID"
                                               id="keywords" name="search" type="text">
                                        <input type="hidden" name="command" value="user_verification">
                                    </div>
                                </div>

                                <div class="col-md-2 col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" type="submit" value="Search">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="clear"></div>
                    </section>
                    <c:if test="${requestScope.verificationMsg != null}">
                        <div class="center-content">
                            <div class="clear"></div>
                            <br>
                            <h3 class="section-title"><c:out value="${verificationMsg}"/></h3>
                        </div>
                    </c:if>
                    <div class="center-content">
                    </div>
                    <div class="col-md-12">
                        <div class="page type-page status-publish hentry">
                            <div class="entry-content">
                                <div class="table-tabs" id="responsiveTabs">
                                    <div id="sectionA" class="tab-pane fade in active">
                                        <jsp:useBean id="unverified_users" scope="request"
                                                     type="java.util.List<by.jwd.library.bean.User>"/>
                                        <c:if test="${not empty unverified_users}">
                                            <table class="table table-bordered">
                                                <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>Email</th>
                                                    <th>Login</th>
                                                    <th>Passport Id</th>
                                                    <th>Action</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="item" items="${unverified_users}">
                                                    <tr class="cart_item">
                                                        <form action="Controller" method="post">
                                                            <input type="hidden" name="userId"
                                                                   value="${item.userId}">
                                                            <input type="hidden" name="command"
                                                                   value="change_passport_id">
                                                            <td>${item.name}</td>
                                                            <td>${item.email}</td>
                                                            <td>${item.login}</td>
                                                            <td><input style="border: 0;" value="${item.passportId}"
                                                                       name="passport-id" type="text">
                                                            </td>
                                                            <td>
                                                                <div class="dropdown">
                                                                    <a href="${pageContext.request.contextPath}/Controller?command=verify_user&user_id=${item.userId}"
                                                                       data-toggle="dropdown" class="dropdown-toggle"><b
                                                                            class="caret"></b>Actions</a>
                                                                    <ul class="dropdown-menu" style="display: none;">
                                                                        <li>
                                                                            <a href="${pageContext.request.contextPath}/Controller?command=verify_user&user_id=${item.userId}">Verify</a>
                                                                        </li>
                                                                        <li>
                                                                        <li><input class="form-control" type="submit"
                                                                                   value="Edit Passport Id"></li>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </td>
                                                        </form>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
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
<!-- End: User Verification Section -->

<!-- Start: Footer -->
<jsp:include page="/WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="/WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>

