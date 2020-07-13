<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.language" var="language" />
<fmt:message bundle="${loc}" key="local.price" var="price" />
<fmt:message bundle="${loc}" key="local.restriction" var="restriction" />
<fmt:message bundle="${loc}" key="local.author" var="author" />
<fmt:message bundle="${loc}" key="local.authors" var="authors" />
<fmt:message bundle="${loc}" key="local.genres" var="genres" />
<fmt:message bundle="${loc}" key="local.summary" var="summary" />
<fmt:message bundle="${loc}" key="local.booksNMedia" var="booksNMedia" />
<fmt:message bundle="${loc}" key="local.publisher" var="publisher" />
<fmt:message bundle="${loc}" key="local.format" var="format" />
<fmt:message bundle="${loc}" key="local.addMedia" var="addMedia" />
<fmt:message bundle="${loc}" key="local.addMediaPage" var="addMediaPage" />
<fmt:message bundle="${loc}" key="local.title" var="title" />
<fmt:message bundle="${loc}" key="local.totalCopies" var="totalCopies" />
<fmt:message bundle="${loc}" key="local.pictureUrl" var="pictureUrl" />
<fmt:message bundle="${loc}" key="local.restriction" var="restriction" />
<fmt:message bundle="${loc}" key="local.editMedia" var="editMedia" />
<fmt:message bundle="${loc}" key="local.editMediaPage" var="editMediaPage" />
<fmt:message bundle="${loc}" key="local.mediaDetails" var="mediaDetails" />



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

    <%--    <script>--%>
    <%--        function validate() {--%>
    <%--            let valid_name = /^[a-z A-Zа-яА-Я]{4,20}$/;--%>
    <%--            let valid_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;--%>
    <%--            let valid_login = /^[a-zA-Z0-9_-]{4,20}$/;--%>

    <%--            let name = document.getElementById("name");--%>
    <%--            let email = document.getElementById("email");--%>
    <%--            let login = document.getElementById("login");--%>

    <%--            if (!valid_name.test(name.value) || name.value === '') {--%>
    <%--                alert("${nameTestMSG}");--%>
    <%--                name.focus();--%>
    <%--                name.style.background = '#f08080';--%>
    <%--                return false;--%>
    <%--            }--%>
    <%--            if (!valid_email.test(email.value) || email.value === '') {--%>
    <%--                alert("${emailTestMSG}");--%>
    <%--                email.focus();--%>
    <%--                email.style.background = '#f08080';--%>
    <%--                return false;--%>
    <%--            }--%>
    <%--            if (!valid_login.test(login.value) || login.value === '') {--%>
    <%--                alert("${loginTestMSG}");--%>
    <%--                login.focus();--%>
    <%--                login.style.background = '#f08080';--%>
    <%--                return false;--%>
    <%--            }--%>
    <%--        }--%>
    <%--    </script>--%>

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="parts/header.jsp"/>
<!-- End: Header Section -->

<jsp:useBean id="mediaDetail" scope="request" type="by.jwd.library.bean.MediaDetail"/>

<!-- Start: Page Banner -->
<section class="page-banner services-banner">
    <div class="container">
        <div class="banner-header">
            <h2>${editMediaPage}</h2>
            <span class="underline center"></span>
            <p class="lead"></p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li><a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaId=${mediaDetail.mediaID}">${mediaDetails}</a></li>
                <li>${editMediaPage}</li>
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
                                    <c:if test="${requestScope.addMediaMsg != null}">
                                        <div class="center-content">
                                            <div class="clear"></div>
                                            <br>
                                            <h3 class="section-title"><c:out value="${addMediaMsg}"/></h3>
                                        </div>
                                    </c:if>
                                    <div class="col-md-12">
                                        <div class="contact-form bg-light margin-right">
                                            <h2>${addMediaPage}</h2>
                                            <span class="underline left"></span>
                                            <div class="contact-fields">

                                                <form action="Controller" method="post" >
                                                    <input type="hidden" name="command" value="add_media">
                                                    <input type="hidden" name="mediaId" value="${mediaDetail.mediaID}">
                                                    <div class="row">
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${title}</b>
                                                                <input class="form-control" type="text" id="title"
                                                                       name="title" value="${mediaDetail.title}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>ISBN</b>
                                                                <input class="form-control" type="text" id="ISBN"
                                                                       name="ISBN" value="${mediaDetail.iSBN}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${publisher}</b>
                                                                <input class="form-control" type="text" id="publisher"
                                                                       name="publisher" value="${mediaDetail.publisher}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${format}</b>
                                                                <input class="form-control" type="text" id="format"
                                                                       name="format" value="${mediaDetail.format}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${language}</b>
                                                                <input class="form-control" type="text" id="language"
                                                                       name="language" value="${mediaDetail.language}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${price}</b>
                                                                <input class="form-control" type="text" id="price"
                                                                       name="price" value="${mediaDetail.price}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${authors}</b>
                                                                <input class="form-control" type="text" id="authors"
                                                                       name="authors" value="<c:forEach var="item" items="${mediaDetail.authors}">${item.fullName};</c:forEach>
                                                                 "/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${genres}</b>
                                                                <input class="form-control" type="text" id="genres"
                                                                       name="genres" value="<c:forEach var="item" items="${mediaDetail.genres}">${item.genre};</c:forEach>
                                                                "/>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${totalCopies}</b>
                                                                <input class="form-control" type="text" id="copies"
                                                                       name="copies" value="${mediaDetail.totalCopies}"/>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${pictureUrl}</b>
                                                                <input class="form-control" type="text" id="picture"
                                                                       name="picture" value="${mediaDetail.picture}"/>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-6 col-sm-6">
                                                            <div class="form-group">
                                                                <b>${restriction}</b>
                                                                <input class="form-control" type="text" id="restriction"
                                                                       name="restriction"
                                                                       <c:if test="${not empty mediaDetail.restriction}">
                                                                             value="${mediaDetail.restriction}"
                                                                       </c:if>
                                                                />
                                                            </div>
                                                        </div>

                                                        <div class="col-sm-12">
                                                            <div class="form-group">
                                                                <b>${summary}</b>
                                                                <textarea class="form-control" name="summary" id="summary" style="height: 300px">${mediaDetail.summary}</textarea>
                                                                <div class="clearfix"></div>
                                                            </div>
                                                        </div>

                                                        <div class="col-sm-12">
                                                            <div class="form-group form-submit">
                                                                <input class="btn btn-default" type="submit"
                                                                       name="submit" value="${editMedia}"/>
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