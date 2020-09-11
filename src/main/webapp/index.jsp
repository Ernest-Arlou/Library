<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local/local" var="loc"/>

<fmt:message bundle="${loc}" key="local.welcome" var="welcome"/>
<fmt:message bundle="${loc}" key="local.searchQuestion" var="searchQuestion"/>
<fmt:message bundle="${loc}" key="local.button.search" var="searchButton"/>

<fmt:message bundle="${loc}" key="local.welcomeTo" var="welcomeTo"/>
<fmt:message bundle="${loc}" key="local.aboutMessage" var="aboutMessage"/>
<fmt:message bundle="${loc}" key="local.button.viewMore" var="viewMore"/>
<fmt:message bundle="${loc}" key="local.button.readMore" var="readMore"/>
<fmt:message bundle="${loc}" key="local.newReleases" var="newReleases"/>
<fmt:message bundle="${loc}" key="local.publisher" var="publisher"/>
<fmt:message bundle="${loc}" key="local.format" var="format"/>
<fmt:message bundle="${loc}" key="local.language" var="language"/>


<html lang="zxx">

<head>
    <!-- Title -->
    <title>..:: LIBRARIA ::..</title>

    <!-- Meta -->
    <meta charset="utf-8"/>
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
            <h2>${welcome}</h2>
            <span class="underline center"></span>
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

                            <form action="Controller" method="get">
                                <div class="col-md-10 col-sm-6">
                                    <div class="form-group">
                                        <input class="form-control" id="keywords" name="search" type="text">
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

                    <!-- Start: Welcome Section -->
                    <section class="welcome-section">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="welcome-wrap">
                                        <div class="welcome-text">
                                            <h2 class="section-title">${welcomeTo} libraria</h2>
                                            <span class="underline left"></span>
                                            <p>${aboutMessage}</p>
<%--                                            <a class="btn btn-dark-gray" href="#">${readMore}</a>--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="welcome-image"
                             style="background-image: url(resources/images/welcome-img-home-v3.jpg);"></div>
                    </section>
                    <!-- End: Welcome Section -->

                    <!-- Start: Category Filter -->
                    <section class="category-filter section-padding">
                        <div class="container">
                            <div class="center-content">
                                <div class="row">
                                    <div class="col-md-6 col-md-offset-3">
                                        <h2 class="section-title">${newReleases}</h2>
                                        <br>
                                        <span class="underline center"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="category-filter">
                            <jsp:useBean id="mediaPage" scope="request" type="by.jwd.library.bean.MediaPage"/>
                            <c:if test="${not empty mediaPage}">
                                <ul class="category-list">
                                    <c:forEach var="item" items="${mediaPage.mediaDisplay}">
                                        <li class="category-item adults" style="display: inline-block;" data-bound="">
                                            <figure style="max-height: 430px;">
                                                <img src="${item.picture}" alt="New Releaase"/>
                                                <figcaption class="bg-orange">
                                                    <div class="info-block">
                                                        <h4>${item.title}</h4>
                                                        <span class="author"><strong>${publisher}: </strong>${item.publisher}</span>
                                                        <span class="author"><strong>${format}: </strong>${item.materialType}</span>
                                                        <span class="author"><strong>${language}: </strong>${item.language}</span>

                                                        <a href="?command=media_detail&mediaId=${item.mediaID}">${readMore}
                                                            <i class="fa fa-long-arrow-right"></i></a>
                                                    </div>
                                                </figcaption>
                                            </figure>
                                        </li>

                                    </c:forEach>
                                </ul>
                            </c:if>


                            <div class="center-content">
                                <a href="Controller?command=page&page=1" class="btn btn-primary">${viewMore}</a>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </section>
                    <!-- End: Category Filter -->
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

