<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.profile" var="profile" />
<fmt:message bundle="${loc}" key="local.editProfile" var="editProfile" />
<fmt:message bundle="${loc}" key="local.editPassword" var="editPassword" />
<fmt:message bundle="${loc}" key="local.startDate" var="startDate" />
<fmt:message bundle="${loc}" key="local.endDate" var="endDate" />
<fmt:message bundle="${loc}" key="local.action" var="action" />
<fmt:message bundle="${loc}" key="local.deleteConfirmation" var="deleteConfirmation" />
<fmt:message bundle="${loc}" key="local.button.delete" var="deleteBtn" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.reserved" var="reserved" />
<fmt:message bundle="${loc}" key="local.loaned" var="loaned" />
<fmt:message bundle="${loc}" key="local.mediaDetails" var="mediaDetails" />
<fmt:message bundle="${loc}" key="local.format" var="format" />


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
            <h2>${profile}</h2>
            <span class="underline center"></span>
        </div>
        <div class="breadcrumb">
            <ul>
                <li><a href="${pageContext.request.contextPath}/Controller">${home}</a></li>
                <li>${profile}</li>
            </ul>
        </div>
    </div>
</section>
<!-- End: Page Banner -->

<!-- Start: Account Section -->
<div id="content" class="site-content">
    <div id="primary" class="content-area">
        <main id="main" class="site-main">
            <div class="cart-main">
                <div class="container">
                    <div class="row">
                        <div class="cart-head">
                            <div class="col-xs-12 col-sm-6 account-option">
                                <jsp:useBean id="user" scope="request" type="by.jwd.library.bean.User"/>

                                <c:choose>
                                    <c:when test="${user.role.equalsIgnoreCase('user')}">
                                        <strong>${user.name}</strong>
                                    </c:when>
                                    <c:otherwise>
                                        <strong>${user.login}</strong>
                                    </c:otherwise>
                                </c:choose>
                                <ul>
                                    <li><a href="${pageContext.request.contextPath}/Controller?command=edit_user_info_form">${editProfile}</a></li>
                                    <li class="divider">|</li>
                                    <li><a href="${pageContext.request.contextPath}/Controller?command=edit_user_password_form">${editPassword}</a></li>
                                </ul>
                            </div>
                            <div class="col-xs-12 col-sm-6 library-info">
                                <c:if test="${user.role.equalsIgnoreCase('user')}">
                                    <ul>
                                        <li>
                                            <strong>${email}:</strong>
                                            <a>${user.email}</a>
                                        </li>
                                    </ul>
                                </c:if>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <c:if test="${sessionScope.role.equalsIgnoreCase('user')}">
                        <jsp:useBean id="reservations" scope="request"
                                     type="java.util.List<by.jwd.library.bean.LoanType>"/>
                        <jsp:useBean id="loans" scope="request"
                                     type="java.util.List<by.jwd.library.bean.LoanType>"/>
                        <div class="col-md-12">
                            <div class="page type-page status-publish hentry">
                                <div class="entry-content">
                                    <div class="woocommerce table-tabs r-tabs" id="responsiveTabs">
                                        <ul class="nav nav-tabs r-tabs-nav">
                                            <li class="r-tabs-tab r-tabs-state-active active"><b class="arrow-up"></b><a
                                                    data-toggle="tab" href="#sectionA" class="r-tabs-anchor"
                                                    aria-expanded="true">${reserved} (${reservations.size()})</a></li>
                                            <li class="r-tabs-tab r-tabs-state-default"><b class="arrow-up"></b><a
                                                    data-toggle="tab" href="#sectionB" class="r-tabs-anchor"
                                                    aria-expanded="false">${loaned} (${loans.size()})</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="r-tabs-accordion-title r-tabs-state-active"><a href="#sectionA"
                                                                                                       class="r-tabs-anchor"></a>
                                            </div>
                                            <div id="sectionA"
                                                 class="tab-pane fade r-tabs-panel r-tabs-state-active active in"
                                                 style="display: block;">
                                                <form method="post"
                                                      action="${pageContext.request.contextPath}/Controller">
                                                    <c:if test="${reservations.size()>0}">
                                                        <table class="table table-bordered shop_table cart">
                                                            <thead>
                                                            <tr>
                                                                <th class="product-name"></th>
                                                                <th class="product-name">${mediaDetails}</th>
                                                                <th class="product-price">${startDate}</th>
                                                                <th class="product-price">${endDate}</th>
                                                                <th class="product-subtotal">${action}</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="reservation" items="${reservations}">
                                                                <tr class="cart_item">
                                                                    <td data-title="Product" class="product-name"
                                                                        data-th="Title">
                                                                        <a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaTypeId=${reservation.mediaDetail.mediaTypeID}"><img
                                                                                style="margin-right: 110px;"
                                                                                src="${reservation.mediaDetail.picture}"
                                                                                alt="cart-product-1"></a>
                                                                    </td>
                                                                    <td data-title="Price" class="product-price"
                                                                        data-th="Pickup Location ">
                                                                        <span class="product-detail">
                                                                                <a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaTypeId=${reservation.mediaDetail.mediaTypeID}">${reservation.mediaDetail.title}</a>
                                                                                <span><strong>${format}: </strong>${reservation.mediaDetail.materialType}</span>
                                                                                <span><strong>ISBN: </strong>${reservation.mediaDetail.iSBN}</span>
                                                                        </span>
                                                                    </td>
                                                                    <td data-title="Price" class="product-price"
                                                                        data-th="Pickup Location ">
                                                                <span class="bt-content">
                                                                            <p>${reservation.startDate}</p>
                                                                </span>
                                                                    </td>
                                                                    <td data-title="Price" class="product-price"
                                                                        data-th="Pickup Location ">
                                                                <span class="bt-content">
                                                                            <p>${reservation.endDate}</p>
                                                                </span>
                                                                    </td>
                                                                    <td data-title="Price" class="product-price"
                                                                        data-th="Pickup Location ">
                                                                <span class="bt-content">
                                                                          <div><a onClick="return window.confirm('${deleteConfirmation}');"
                                                                                  href="${pageContext.request.contextPath}/Controller?command=delete_reservation&reservationId=${reservation.loanTypeId}&from=profile"
                                                                                  class="btn btn-dark-gray">${deleteBtn}</a></div>
                                                                </span>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </c:if>
                                                </form>
                                            </div>

                                            <div class="r-tabs-accordion-title r-tabs-state-default"><a href="#sectionB"
                                                                                                        class="r-tabs-anchor"></a>
                                            </div>
                                            <div id="sectionB" class="tab-pane fade r-tabs-panel r-tabs-state-default"
                                                 style="display: none;">

                                                <c:if test="${loans.size()>0}">
                                                    <table class="table table-bordered shop_table cart">
                                                        <thead>
                                                        <tr>
                                                            <th class="product-name"></th>
                                                            <th class="product-name">${mediaDetails}</th>
                                                            <th class="product-price">${startDate}</th>
                                                            <th class="product-price">${endDate}</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach var="loan" items="${loans}">
                                                            <tr class="cart_item">
                                                                <td data-title="Product" class="product-name"
                                                                    data-th="Title">
                                                                    <a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaTypeId=${loan.mediaDetail.mediaTypeID}"><img
                                                                            style="margin-right: 110px;"
                                                                            src="${loan.mediaDetail.picture}"
                                                                            alt="cart-product-1"></a>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                        <span class="product-detail">
                                                                                <a href="${pageContext.request.contextPath}/Controller?command=media_detail&mediaTypeId=${loan.mediaDetail.mediaTypeID}">${loan.mediaDetail.title}</a>
                                                                                <span><strong>${format}: </strong>${loan.mediaDetail.materialType}</span>
                                                                                <span><strong>ISBN: </strong>${loan.mediaDetail.iSBN}</span>
                                                                        </span>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                <span class="bt-content">
                                                                            <p>${loan.startDate}</p>
                                                                </span>
                                                                </td>
                                                                <td data-title="Price" class="product-price"
                                                                    data-th="Pickup Location ">
                                                                <span class="bt-content">
                                                                            <p>${loan.endDate}</p>
                                                                </span>
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
                        </c:if>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<!-- End: Account Section -->

<!-- Start: Footer -->
<jsp:include page="/WEB-INF/jsp/parts/footer.jsp"/>
<!-- End: Footer -->

<!-- Start: Scripts -->
<jsp:include page="/WEB-INF/jsp/parts/scripts.jsp"/>
<!-- End: Scripts -->

</body>


</html>

