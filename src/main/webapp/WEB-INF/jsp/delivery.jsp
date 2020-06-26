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
            <h2>Delivery</h2>
            <span class="underline center"></span>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">Home</a></li>
                <li>Delivery</li>
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
                                        <input class="form-control" placeholder="Search by Passport ID"
                                               id="keywords" name="search" type="text">
                                        <input type="hidden" name="command" value="delivery">
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
                    <c:if test="${requestScope.deliveryMsg != null}">
                        <div class="center-content">
                            <div class="clear"></div>
                            <br>
                            <h3 class="section-title"><c:out value="${deliveryMsg}"/></h3>
                        </div>
                    </c:if>
                    <div class="center-content">
                    </div>
                    <div class="col-md-12">
                        <div class="page type-page status-publish hentry">
                            <div class="entry-content">
                                <div class="table-tabs" id="responsiveTabs">
                                    <div id="sectionA" class="tab-pane fade in active">
                                        <jsp:useBean id="deliveryTypes" scope="request"
                                                     type="java.util.List<by.jwd.library.bean.DeliveryType>"/>
                                                <c:if test="${not empty deliveryTypes}">
                                                    <table class="table table-bordered shop_table cart">
                                                        <thead>
                                                        <tr>
                                                            <th class="product-name"></th>
                                                            <th class="product-name">Media details</th>
                                                            <th class="product-price">Reservation details</th>
                                                            <th class="product-price">User details</th>
                                                            <th class="product-subtotal">Action</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach var="item" items="${deliveryTypes}">
                                                            <tr class="cart_item">
                                                                <td data-title="Product" class="product-name"
                                                                    data-th="Title">
                                                                    <a href="${pageContext.request.contextPath}/Controller?command=media_detail&media_type_id=${item.loanType.mediaDetail.mediaTypeID}"><img
                                                                            style="margin-right: 1px; height: 180px;"
                                                                            src="${item.loanType.mediaDetail.picture}"
                                                                            alt="cart-product-1"></a>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                        <span class="product-detail">
                                                                                <a href="${pageContext.request.contextPath}/Controller?command=media_detail&media_type_id=${item.loanType.mediaDetail.mediaTypeID}">${item.loanType.mediaDetail.title} </a>
                                                                            <div>
                                                                            <span><strong>Format: </strong>${item.loanType.mediaDetail.materialType}</span>
                                                                            </div>
                                                                              <div>
                                                                            <span><strong>ISBN: </strong>${item.loanType.mediaDetail.iSBN}</span>
                                                                             </div>
                                                                        </span>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                 <span class="product-detail">
                                                                     <div>
                                                                         <div><strong>Start Date: </strong></div>
                                                                         <div>${item.loanType.startDate}</div>
                                                                     </div>
                                                                     <div>
                                                                         <div><strong>End Date: </strong></div>
                                                                         <div> ${item.loanType.endDate}</div>
                                                                 </div>
                                                                 </span>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                 <span class="product-detail">
                                                                     <div>
                                                                         <div><strong>Name: </strong></div>
                                                                         <div>${item.user.name}</div>
                                                                         <div><strong>Email: </strong></div>
                                                                         <div>${item.user.email}</div>
                                                                         <div><strong>Passport Id: </strong></div>
                                                                         <div>${item.user.passportId}</div>
                                                                     </div>
                                                                 </span>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                    <div> <a
                                                                             class="btn btn-dark-gray">Give out</a></div>
                                                                    <div><br></div>
                                                                    <div><a onClick="return window.confirm('Delete?');"
                                                                            href="${pageContext.request.contextPath}/Controller?command=delete_reservation&reservation_id=${item.loanType.loanTypeId}&from=delivery"
                                                                            class="btn btn-dark-gray">Delete</a></div>
                                                                </td>
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

