<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


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
            <h2>Books & Media Listing</h2>
            <span class="underline center"></span>
            <p class="lead">Proin ac eros pellentesque dolor pharetra tempo.</p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">Home</a></li>
                <li>Books & Media</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->



<!-- Start: Products Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="books-full-width">
                <div class="container">
<!-- Start: Search Section -->
                    <section class="search-filters">
                        <div class="filter-box">
                            <h3>What are you looking for at the library?</h3>

                            <form action="Controller" method="get">
                                <div class="col-md-10 col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" placeholder="Search by Keyword" id="keywords" name="search" type="text">
                                        <input type="hidden" name="command" value="page">
                                        <input type="hidden" name="page" value="1">
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
<!-- End: Search Section -->


<!-- Start: Books Media Section -->
                    <div class="booksmedia-fullwidth">

                        <jsp:useBean id="mediapage" scope="request" type="by.jwd.library.bean.MediaPage"/>
                        <c:if test="${not empty mediapage}">
                            <ul>
                                <c:forEach var="item" items="${mediapage.mediaDisplay}">
                                    <li>
                                        <c:choose>
                                            <c:when test="${item.materialType=='Hardcover'}">
                                                <div class="book-list-icon yellow-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='Magasin'}">
                                                <div class="book-list-icon red-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='Audio CD'}">
                                                <div class="book-list-icon green-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='E Audio'}">
                                                <div class="book-list-icon green-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='E Book'}">
                                                <div class="book-list-icon light-green-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='DVD'}">
                                                <div class="book-list-icon blue-icon"></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="book-list-icon yellow-icon"></div>
                                            </c:otherwise>
                                        </c:choose>

                                        <figure>
                                            <a href="${pageContext.request.contextPath}/Controller?command=media_detail&media_type_id=${item.mediaTypeID}"><img src="${item.picture}" alt="Book"></a>
                                            <figcaption>
                                                <header>
                                                    <h4><a href="${pageContext.request.contextPath}/Controller?command=media_detail&media_type_id=${item.mediaTypeID}">${item.title}</a></h4>
                                                    <p><strong>Publisher:</strong>  ${item.publisher}</p>
                                                    <br>
                                                </header>
                                                <p>${item.summary}</p>
                                            </figcaption>
                                        </figure>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>
<!-- End: Books Media Section -->



<!-- Start: Pagination Section -->

                    <c:if test="${mediapage.totalPages > 1}">
                    <nav class="navigation pagination text-center">
                        <h2 class="screen-reader-text">Posts navigation</h2>
                        <div class="nav-links">
                            <c:forEach items="${mediapage.navigationPages}" var = "page">


                                <c:if test="${page != -1 }">

                                    <c:if test="${page == mediapage.page}">
                                        <span class="page-numbers current">${page}</span>
                                    </c:if>

                                    <c:if test="${page != mediapage.page}">
                                    <a class="page-numbers" href="${pageContext.request.contextPath}/Controller?command=page&page=${page}&search=${mediapage.search}">${page}</a>
                                    </c:if>

                                </c:if>

                                <c:if test="${page == -1 }">
                                    <span class="page-numbers current"> ... </span>
                                </c:if>

                            </c:forEach>
                        </div>
                    </c:if>
                    <br>
<!-- End: Pagination Section -->
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

