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
            <h2>Error 404</h2>
            <span class="underline center"></span>
            <p class="lead">Proin ac eros pellentesque dolor pharetra tempo.</p>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">Home</a></li>
                <li>404</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->

<!-- Start: 404 Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="error-main">
                <div class="container">
                    <div class="error-view">
                        <div class="company-info">
                            <div class="col-md-5 col-md-offset-1 ">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="error-box bg-dark margin-left text-center">
                                            <img src="resources/images/error-img.png" alt="Error Image">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5  ">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="error-info bg-light margin-right text-center" >
                                            <br><br><br>
                                            <h2>OOPS <small>Page Not Found!</small></h2>
                                            <span>Take a moment and start from our homepage.</span>
                                            <a href="${pageContext.request.contextPath}/Controller" class="btn btn-dark-gray">Home</a>
                                            <br><br><br>
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
<!-- End: 404 Section -->

<!-- Start: Footer -->
<jsp:include page="/WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="/WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>

