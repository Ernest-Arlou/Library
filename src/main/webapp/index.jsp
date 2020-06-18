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
            <h2>WELCOME!!!</h2>
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

<!-- Start: Welcome Section -->
                    <section class="welcome-section">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="welcome-wrap">
                                        <div class="welcome-text">
                                            <h2 class="section-title">Welcome to the libraria</h2>
                                            <span class="underline left"></span>
                                            <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humor, or randomized words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humor, or non-characteristic words etc.</p>
                                            <a class="btn btn-dark-gray" href="#">Read More</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="welcome-image" style="background-image: url(resources/images/welcome-img-home-v3.jpg);"></div>
                    </section>
<!-- End: Welcome Section -->

<!-- Start: Category Filter -->
                    <section class="category-filter section-padding">
                        <div class="container">
                            <div class="center-content">
                                <div class="row">
                                    <div class="col-md-6 col-md-offset-3">
                                        <h2 class="section-title">Check Out The New Releases</h2>
                                        <br>
                                        <span class="underline center"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="category-filter">
                            <jsp:useBean id="mediapage" scope="request" type="by.jwd.library.bean.MediaPage"/>
                            <c:if test="${not empty mediapage}">
                                <ul class="category-list">
                                     <c:forEach var="item" items="${mediapage.mediaDisplay}">
                                        <li class="category-item adults" style="display: inline-block;" data-bound="">
                                            <figure style=" max-height: 420px;">
                                                <img src="${item.picture}" alt="New Releaase" />
                                                <figcaption class="bg-orange">
                                                    <div class="info-block">
                                                        <h4>${item.title}</h4>
                                                        <span class="author"><strong>Publisher: </strong>${item.publisher}</span>
                                                        <span class="author"><strong>Format: </strong>${item.materialType}</span>

                                                        <p>${item.summary}</p>
                                                        <a href="?command=media_detail&media_type_id=${item.mediaTypeID}">Read More <i class="fa fa-long-arrow-right"></i></a>
                                                    </div>
                                                </figcaption>
                                            </figure>
                                        </li>

                                     </c:forEach>
                                 </ul>
                            </c:if>



                            <div class="center-content">
                                <a href="Controller?command=page&page=1" class="btn btn-primary">View More</a>
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

