<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

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
            <h2>Media detail</h2>
            <span class="underline center"></span>
            <p class="lead"></p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">Home</a></li>
                <li><a href="index-2.html">Books & Media</a></li>
                <li>Book Detail</li>
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
                                        <p><strong>Publisher:</strong> ${mediaDetail.publisher}</p>
                                        <p><strong>Format:</strong> ${mediaDetail.materialType}</p>
                                        <p><strong>Language:</strong> ${mediaDetail.language}</p>
                                        <p><strong>Price:</strong> ${mediaDetail.price}</p>
                                        <c:if test="${not empty mediaDetail.restriction}">
                                            <p style="color: #ff0000"><strong style="color: red">Restriction!!!:</strong> ${mediaDetail.restriction}</p>
                                        </c:if>



                                        <c:choose>
                                            <c:when test="${mediaDetail.authors.size() == 1}">
                                                <p><strong>Author: </strong>
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
                                                <p><strong>Authors: </strong>
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

                                        <p><strong>Genres: </strong>
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
                                            <h4>Available now</h4>
                                        </c:when>
                                        <c:otherwise>
                                            <h4>Not available</h4>
                                        </c:otherwise>
                                        </c:choose>
                                        <p><strong>Total Copies: </strong>${mediaDetail.totalCopies}</p>
                                        <p><strong>Available: </strong>${mediaDetail.availableCopies}</p>
                                        <p><strong>Reserved: </strong>${mediaDetail.reservedCopies}</p>
                                        <p><strong>Loaned: </strong>${mediaDetail.loanedCopies}</p>

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
                                                        <a href="${pageContext.request.contextPath}/Controller?command=reserve&media_type_id=${mediaDetail.mediaTypeID}" class="btn btn-dark-gray">Place a Hold</a>
                                                        </c:if>
                                                       </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <h4>Staff can't place holds</h4>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <h4>You need to login in order to place a hold</h4>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                            <p><strong>Summary: </strong>${mediaDetail.summary}</p>
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