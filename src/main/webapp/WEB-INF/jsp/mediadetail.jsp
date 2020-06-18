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
    <jsp:include page="parts/css.jsp"/>
    <!-- End: Css Section -->

</head>

<body>

<!-- Start: Header Section -->
<jsp:include page="parts/header.jsp"/>
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

                                        <a href="#." class="btn btn-dark-gray">Place a Hold</a>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                            <p><strong>Summary: </strong>${mediaDetail.summary}</p>
                        </c:if>


























                        <div class="table-tabs" id="responsiveTabs">
                            <ul class="nav nav-tabs">
                                <li class="active"><b class="arrow-up"></b><a data-toggle="tab" href="#sectionA">Copies: 05</a></li>
                                <li><b class="arrow-up"></b><a data-toggle="tab" href="#sectionB">Reviews (12)</a></li>
                                <li><b class="arrow-up"></b><a data-toggle="tab" href="#sectionC">Table of Contents</a></li>
                                <li><b class="arrow-up"></b><a data-toggle="tab" href="#sectionD">Novelist Recommendations</a></li>
                            </ul>
                            <div class="tab-content">
                                <div id="sectionA" class="tab-pane fade in active">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>Library Name</th>
                                            <th>Shelf Number</th>
                                            <th>Material Type</th>
                                            <th>Status </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>Oak Park Public Library Main Branch</td>
                                            <td>B PURMORT</td>
                                            <td>Book</td>
                                            <td>In Processing</td>
                                        </tr>
                                        <tr>
                                            <td>Bedford Park Public Library District</td>
                                            <td>616.99 PUR</td>
                                            <td>Book</td>
                                            <td>Due 8/24/16</td>
                                        </tr>
                                        <tr>
                                            <td>Blue Island Public Library</td>
                                            <td>BIO PUR</td>
                                            <td>eBook</td>
                                            <td>Due 8/24/16</td>
                                        </tr>
                                        <tr>
                                            <td>Bridgeview Public Library</td>
                                            <td>B PUR</td>
                                            <td>DVD</td>
                                            <td>In Processing</td>
                                        </tr>
                                        <tr>
                                            <td>Eisenhower Public Library District</td>
                                            <td>616.994 PUR</td>
                                            <td>Magazine</td>
                                            <td>Checked In</td>
                                        </tr>
                                        <tr>
                                            <td>Forest Park Public Library</td>
                                            <td>BIO PURMORT</td>
                                            <td>Magazine</td>
                                            <td>Checked In</td>
                                        </tr>
                                        <tr>
                                            <td>Hinsdale Public Library</td>
                                            <td>B PUR</td>
                                            <td>Audio</td>
                                            <td>Checked In</td>
                                        </tr>
                                        <tr>
                                            <td>Oak Park Public Library Maze Branch</td>
                                            <td>616.99 PUR</td>
                                            <td>Audio</td>
                                            <td>Due 9/10/16</td>
                                        </tr>
                                        <tr>
                                            <td>River Grove Public Library District</td>
                                            <td>616.994 PURMORT</td>
                                            <td>Book</td>
                                            <td>Due 9/10/16</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div id="sectionB" class="tab-pane fade in">
                                    <h5>Lorem Ipsum Dolor</h5>
                                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.</p>
                                </div>
                                <div id="sectionC" class="tab-pane fade in">
                                    <h5>Lorem Ipsum Dolor</h5>
                                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.</p>
                                </div>
                                <div id="sectionD" class="tab-pane fade in">
                                    <h5>Lorem Ipsum Dolor</h5>
                                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.</p>
                                </div>
                                <div id="sectionE" class="tab-pane fade in">
                                    <h5>Lorem Ipsum Dolor</h5>
                                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.</p>
                                </div>
                                <div id="sectionF" class="tab-pane fade in">
                                    <h5>Lorem Ipsum Dolor</h5>
                                    <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.</p>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<!-- End: Products Section -->

<!-- Start: Footer -->
<jsp:include page="parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>