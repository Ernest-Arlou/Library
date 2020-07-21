<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.booksNMedia" var="booksNMedia" />
<fmt:message bundle="${loc}" key="local.button.search" var="searchButton" />
<fmt:message bundle="${loc}" key="local.searchQuestion" var="searchQuestion" />
<fmt:message bundle="${loc}" key="local.noResults" var="noResults" />
<fmt:message bundle="${loc}" key="local.publisher" var="publisher" />




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
            <h2>${booksNMedia}</h2>
            <span class="underline center"></span>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li>${booksNMedia}</li>
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
                            <h3>${searchQuestion}</h3>
                            <jsp:useBean id="mediaPage" scope="request" type="by.jwd.library.bean.MediaPage"/>
                            <form action="Controller" method="get">
                                <div class="col-md-10 col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" value="${mediaPage.search}" id="keywords" name="search" type="text">
                                        <input type="hidden" name="command" value="page">
                                        <input type="hidden" name="page" value="1">
                                    </div>
                                </div>

                                <div class="col-md-2 col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" type="submit" value="${searchButton}">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="clear"></div>
                    </section>
<!-- End: Search Section -->


<!-- Start: Books Media Section -->
                    <div class="booksmedia-fullwidth">


                        <c:if test="${mediaPage.totalItems == 0}">
                            <div class="container">
                                <div class="center-content">
                                    <h3>${noResults}</h3>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${not empty mediaPage}">
                            <ul>
                                <c:forEach var="item" items="${mediaPage.mediaDisplay}">
                                    <li>
                                        <c:choose>
                                            <c:when test="${item.materialType=='Hardcover' || item.materialType == 'Твердый переплет'}">
                                                <div class="book-list-icon yellow-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='Magasin' || item.materialType == 'Журнал'}">
                                                <div class="book-list-icon red-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='Audio CD' || item.materialType == 'Аудио CD'}">
                                                <div class="book-list-icon green-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='E Audio' || item.materialType == 'Цифровое аудио'}">
                                                <div class="book-list-icon green-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='E Book' || item.materialType == 'Цифровая книга'}">
                                                <div class="book-list-icon light-green-icon"></div>
                                            </c:when>
                                            <c:when test="${item.materialType=='DVD'}">
                                                <div class="book-list-icon blue-icon"></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="book-list-icon yellow-icon"></div>
                                            </c:otherwise>
                                        </c:choose>

                                        <figure style="max-height: 500px;">
                                            <a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaId=${item.mediaID}&lastPage=${requestScope.lastCommand}"><img src="${item.picture}" alt="Book"></a>
                                            <figcaption>
                                                <header>
                                                    <h4><a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaId=${item.mediaID}&lastPage=${requestScope.lastCommand}">${item.title}</a></h4>
                                                    <p><strong>${publisher}:</strong>  ${item.publisher}</p>
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

                    <c:if test="${mediaPage.totalPages > 1}">
                    <nav class="navigation pagination text-center">
                        <h2 class="screen-reader-text">Posts navigation</h2>
                        <div class="nav-links">
                            <c:forEach items="${mediaPage.navigationPages}" var = "page">


                                <c:if test="${page != -1 }">

                                    <c:if test="${page == mediaPage.page}">
                                        <span class="page-numbers current">${page}</span>
                                    </c:if>

                                    <c:if test="${page != mediaPage.page}">
                                    <a class="page-numbers" href="${pageContext.request.contextPath}/Controller?command=page&page=${page}&search=${mediaPage.search}">${page}</a>
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
<jsp:include page="WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>

