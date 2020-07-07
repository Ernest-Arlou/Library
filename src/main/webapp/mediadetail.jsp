<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.mediaDetails" var="mediaDetails" />
<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.language" var="language" />
<fmt:message bundle="${loc}" key="local.price" var="price" />
<fmt:message bundle="${loc}" key="local.restriction" var="restriction" />
<fmt:message bundle="${loc}" key="local.author" var="author" />
<fmt:message bundle="${loc}" key="local.authors" var="authors" />
<fmt:message bundle="${loc}" key="local.genres" var="genres" />
<fmt:message bundle="${loc}" key="local.availableNow" var="availableNow" />
<fmt:message bundle="${loc}" key="local.notAvailable" var="notAvailable" />
<fmt:message bundle="${loc}" key="local.totalCopies" var="totalCopies" />
<fmt:message bundle="${loc}" key="local.reserved" var="reserved" />
<fmt:message bundle="${loc}" key="local.loaned" var="loaned" />
<fmt:message bundle="${loc}" key="local.staffCanPlaceHolds" var="staffCanPlaceHolds" />
<fmt:message bundle="${loc}" key="local.loginToPlaceHold" var="loginToPlaceHold" />
<fmt:message bundle="${loc}" key="local.summary" var="summary" />
<fmt:message bundle="${loc}" key="local.hold" var="hold" />
<fmt:message bundle="${loc}" key="local.booksNMedia" var="booksNMedia" />
<fmt:message bundle="${loc}" key="local.publisher" var="publisher" />
<fmt:message bundle="${loc}" key="local.format" var="format" />
<fmt:message bundle="${loc}" key="local.available" var="available" />

<!DOCTYPE html>
<html lang="zxx">


<head>

    <!-- Title -->
    <title>..:: LIBRARIA ::..</title>

    <!-- Meta -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1">

    <!-- Start: Css Section -->
    <jsp:include page="WEB-INF/jsp/parts/css.jsp"/>
    <!-- End: Css Section -->

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="WEB-INF/jsp/parts/header.jsp"/>
<!-- End: Header Section -->

<!-- Start: Page Banner -->
<section class="page-banner services-banner">
    <div class="container">
        <div class="banner-header">
            <h2>${mediaDetails}</h2>
            <span class="underline center"></span>
            <p class="lead">
                ${requestScope.lastPage}
            </p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <c:if test="${not empty requestScope.lastPage}">
                    <li><a href="${pageContext.request.contextPath}/Controller?${requestScope.lastPage}">${booksNMedia}</a></li>
                </c:if>
                <c:if test="${empty requestScope.lastPage}">
                    <li><a href="${pageContext.request.contextPath}/Controller?command=page&page=1">${booksNMedia}</a></li>
                </c:if>
                <li>${mediaDetails}</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->

<!-- Start: Products Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="booksmedia-detail-main">
                <div class="container">
                    <div class="booksmedia-detail-box">
                        <div class="detailed-box">
                            <jsp:useBean id="mediaDetail" scope="request" type="by.jwd.library.bean.MediaDetail"/>
                            <c:if test="${not empty mediaDetail}">
                                <div class="col-xs-12 col-sm-5 col-md-3">
                                    <div class="post-thumbnail">
                                                <c:choose>

                                                <c:when test="${mediaDetail.materialType=='Hardcover'}">
                                                <div class="book-list-icon yellow-icon"></div>
                                                </c:when>
                                                <c:when test="${mediaDetail.materialType=='Magasin'}">
                                                <div class="book-list-icon red-icon"></div>
                                                </c:when>
                                                <c:when test="${mediaDetail.materialType=='Audio CD'}">
                                                <div class="book-list-icon green-icon"></div>
                                                </c:when>
                                                <c:when test="${mediaDetail.materialType=='E Audio'}">
                                                <div class="book-list-icon green-icon"></div>
                                                </c:when>
                                                <c:when test="${mediaDetail.materialType=='E Book'}">
                                                <div class="book-list-icon light-green-icon"></div>
                                                </c:when>
                                                <c:when test="${mediaDetail.materialType=='DVD'}">
                                                <div class="book-list-icon blue-icon"></div>
                                                </c:when>
                                                <c:otherwise>
                                                <div class="book-list-icon yellow-icon"></div>
                                                </c:otherwise>
                                                </c:choose>

                                        <img src="${mediaDetail.picture}" alt="Book Image">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-7 col-md-6">
                                    <div class="post-center-content">
                                        <h2>${mediaDetail.title}</h2>
                                        <p><strong>ISBN:</strong> ${mediaDetail.iSBN}</p>
                                        <p><strong>${publisher}:</strong> ${mediaDetail.publisher}</p>
                                        <p><strong>${format}:</strong> ${mediaDetail.materialType}</p>
                                        <p><strong>${language}:</strong> ${mediaDetail.language}</p>
                                        <p><strong>${price}:</strong> ${mediaDetail.price}</p>
                                        <c:if test="${not empty mediaDetail.restriction}">
                                            <p style="color: #ff0000"><strong style="color: red">${restriction}:</strong> ${mediaDetail.restriction}</p>
                                        </c:if>



                                        <c:choose>
                                            <c:when test="${mediaDetail.authors.size() == 1}">
                                                <p><strong>${author}: </strong>
                                                    <c:choose>
                                                        <c:when test="${not empty mediaDetail.authors.get(0).penName}">
                                                            ${mediaDetail.authors.get(0).penName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${mediaDetail.authors.get(0).fullName}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </c:when>
                                            <c:otherwise>
                                                <p><strong>${authors}: </strong>
                                                <c:forEach var="item" items="${mediaDetail.authors}">
                                                    <c:choose>
                                                        <c:when test="${not empty item.penName}">
                                                            ${item.penName}.
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${item.fullName}.
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                                </p>
                                            </c:otherwise>
                                        </c:choose>

                                        <p><strong>${genres}: </strong>
                                            <c:forEach var="item" items="${mediaDetail.genres}">
                                                ${item.genre}
                                            </c:forEach>
                                        </p>
                                        <br><br><br><br><br><br><br><br>



                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 ">
                                    <div class="post-right-content">
                                        <c:choose>
                                        <c:when test="${mediaDetail.availableCopies >= 1}">
                                            <h4>${availableNow}</h4>
                                        </c:when>
                                        <c:otherwise>
                                            <h4>${notAvailable}</h4>
                                        </c:otherwise>
                                        </c:choose>
                                        <p><strong>${totalCopies}: </strong>${mediaDetail.totalCopies}</p>
                                        <p><strong>${available}: </strong>${mediaDetail.availableCopies}</p>
                                        <p><strong>${reserved}: </strong>${mediaDetail.reservedCopies}</p>
                                        <p><strong>${loaned}: </strong>${mediaDetail.loanedCopies}</p>

                                        <c:if test="${not empty requestScope.reservationMsg}">
                                            <div class="center-content">
                                                <br>
                                                <h3 class="section-title"><c:out value="${reservationMsg}"/></h3>
                                            </div>
                                        </c:if>

                                        <c:choose>
                                            <c:when test="${not empty sessionScope.userId}">
                                                <c:choose>
                                                    <c:when test="${sessionScope.role.equalsIgnoreCase('user')}">
                                                       <c:if test="${mediaDetail.availableCopies > 0}">
                                                        <c:if test="${empty requestScope.reservationMsg}">
                                                        <a href="${pageContext.request.contextPath}/Controller?command=reserve&mediaTypeId=${mediaDetail.mediaTypeID}" class="btn btn-dark-gray">${hold}</a>
                                                        </c:if>
                                                       </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <h4>${staffCanPlaceHolds}</h4>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <h4>${loginToPlaceHold}</h4>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                            <p><strong>${summary}: </strong>${mediaDetail.summary}</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<!-- End: Products Section -->

<!-- Start: Footer -->
<jsp:include page="WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>