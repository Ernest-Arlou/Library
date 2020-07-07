<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local/local" var="loc" />

<fmt:message bundle="${loc}" key="local.yourProfile" var="yourProfile" />
<fmt:message bundle="${loc}" key="local.registration" var="register" />
<fmt:message bundle="${loc}" key="local.logIn" var="logIn" />
<fmt:message bundle="${loc}" key="local.logOut" var="logOut" />

<fmt:message bundle="${loc}" key="local.home" var="home" />
<fmt:message bundle="${loc}" key="local.booksNMedia" var="booksNMedia" />
<fmt:message bundle="${loc}" key="local.delivery" var="delivery" />
<fmt:message bundle="${loc}" key="local.userVerification" var="userVerification" />





<header id="header-v1" class="navbar-wrapper inner-navbar-wrapper">
    <div class="container">
        <div class="row">
            <nav class="navbar navbar-default">
                <div class="row">
                    <div class="col-md-3">
                        <div class="navbar-header">
                            <div class="navbar-brand">
                                <h1>
                                    <a href="${pageContext.request.contextPath}/Controller">
                                        <img src="${pageContext.request.contextPath}/resources/images/libraria-logo-v1.png" alt="LIBRARIA" />
                                    </a>
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <!-- Header Topbar -->
                        <div class="header-topbar hidden-sm hidden-xs">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="topbar-info">
                                        <a href="tel:+61-3-8376-6284"><i class="fa fa-phone"></i>+61-3-8376-6284</a>
                                        <span>/</span>
                                        <a href="mailto:support@libraria.com"><i class="fa fa-envelope"></i>support@libraria.com</a>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="topbar-links">
                                        <c:choose>

                                            <c:when test="${sessionScope.local != 'en_US'}">
                                                <a href="${pageContext.request.contextPath}/Controller?command=change_local&local=en_US&lastCommand=${requestScope.lastCommand}&lastPage=${lastPage}"><i class="fa"></i>EN</a>
                                            </c:when>

                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/Controller?command=change_local&local=ru_RU&lastCommand=${requestScope.lastCommand}&lastPage=${lastPage}"><i class="fa"></i>RU</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <span>|</span>
                                        <c:choose>
                                            <c:when test="${login != null}">
                                                <a href="${pageContext.request.contextPath}/Controller?command=profile"><i class="fa"></i>${yourProfile}</a>
                                                <span>|</span>
                                                <a href="${pageContext.request.contextPath}/Controller?command=log_out"><i class="fa"></i>${logOut}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/Controller?command=registration_form"><i class="fa"></i>${register}</a>
                                                <span>|</span>
                                                <a href="${pageContext.request.contextPath}/Controller?command=login_form"><i class="fa"></i>${logIn}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="navbar-collapse hidden-sm hidden-xs">
                            <ul class="nav navbar-nav">
                                <li class="dropdown">
                                    <a data-toggle="dropdown" class="dropdown-toggle disabled" href="${pageContext.request.contextPath}/Controller">${home}</a>
                                </li>
                                <li class="dropdown">
                                    <a data-toggle="dropdown" class="dropdown-toggle disabled" href="${pageContext.request.contextPath}/Controller?command=page&page=1">${booksNMedia}</a>
                                </li>
                                <c:if test="${sessionScope.role == 'librarian' || sessionScope.role == 'admin'}">
                                    <li class="dropdown">
                                        <a data-toggle="dropdown" class="dropdown-toggle disabled" href="${pageContext.request.contextPath}/Controller?command=user_verification">${userVerification}</a>
                                    </li>
                                    <li class="dropdown">
                                        <a data-toggle="dropdown" class="dropdown-toggle disabled" href="${pageContext.request.contextPath}/Controller?command=delivery">${delivery}</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="mobile-menu hidden-lg hidden-md">
                    <a href="#mobile-menu"><i class="fa fa-navicon"></i></a>
                    <div id="mobile-menu">
                        <ul>
                            <li class="mobile-title">
                                <h4>Navigation</h4>
                                <a href="#" class="close"></a>
                            </li>
                            <li>
                                <a href="index-2.html">Home</a>
                                <ul>
                                    <li><a href="index-2.html">Home V1</a></li>
                                    <li><a href="home-v2.html">Home V2</a></li>
                                    <li><a href="home-v3.html">Home V3</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="books-media-list-view.html">Books &amp; Media</a>
                                <ul>
                                    <li><a href="books-media-list-view.html">Books &amp; Media List View</a></li>
                                    <li><a href="books-media-gird-view-v1.html">Books &amp; Media Grid View V1</a></li>
                                    <li><a href="books-media-gird-view-v2.html">Books &amp; Media Grid View V2</a></li>
                                    <li><a href="books-media-detail-v1.html">Books &amp; Media Detail V1</a></li>
                                    <li><a href="books-media-detail-v2.html">Books &amp; Media Detail V2</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="news-events-list-view.html">News &amp; Events</a>
                                <ul>
                                    <li><a href="news-events-list-view.html">News &amp; Events List View</a></li>
                                    <li><a href="news-events-detail.html">News &amp; Events Detail</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="#">Pages</a>
                                <ul>
                                    <li><a href="cart.html">Cart</a></li>
                                    <li><a href="checkout.html">Checkout</a></li>
                                    <li><a href="signin.html">Signin/Register</a></li>
                                    <li><a href="404.html">404/Error</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="blog.html">Blog</a>
                                <ul>
                                    <li><a href="blog.html">Blog Grid View</a></li>
                                    <li><a href="blog-detail.html">Blog Detail</a></li>
                                </ul>
                            </li>
                            <li><a href="services.html">Services</a></li>
                            <li><a href="contact.html">Contact</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</header>