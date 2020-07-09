<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local/local" var="loc"/>

<fmt:message bundle="${loc}" key="local.yourProfile" var="yourProfile"/>
<fmt:message bundle="${loc}" key="local.registration" var="register"/>
<fmt:message bundle="${loc}" key="local.logIn" var="logIn"/>
<fmt:message bundle="${loc}" key="local.logOut" var="logOut"/>

<fmt:message bundle="${loc}" key="local.home" var="home"/>
<fmt:message bundle="${loc}" key="local.booksNMedia" var="booksNMedia"/>
<fmt:message bundle="${loc}" key="local.delivery" var="delivery"/>
<fmt:message bundle="${loc}" key="local.userVerification" var="userVerification"/>
<fmt:message bundle="${loc}" key="local.navigation" var="navigation"/>
<fmt:message bundle="${loc}" key="local.addMedia" var="addMedia"/>

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
                                        <img src="${pageContext.request.contextPath}/resources/images/libraria-logo-v1.png"
                                             alt="LIBRARIA"/>
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
                                                <a href="${pageContext.request.contextPath}/Controller?command=change_local&local=en_US&lastCommand=${requestScope.lastCommand}&lastPage=${lastPage}"><i
                                                        class="fa"></i>EN</a>
                                            </c:when>

                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/Controller?command=change_local&local=ru_RU&lastCommand=${requestScope.lastCommand}&lastPage=${lastPage}"><i
                                                        class="fa"></i>RU</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <span>|</span>
                                        <c:choose>
                                            <c:when test="${login != null}">
                                                <a href="${pageContext.request.contextPath}/Controller?command=profile"><i
                                                        class="fa"></i>${yourProfile}</a>
                                                <span>|</span>
                                                <a href="${pageContext.request.contextPath}/Controller?command=log_out"><i
                                                        class="fa"></i>${logOut}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/Controller?command=registration_form"><i
                                                        class="fa"></i>${register}</a>
                                                <span>|</span>
                                                <a href="${pageContext.request.contextPath}/Controller?command=login_form"><i
                                                        class="fa"></i>${logIn}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="navbar-collapse hidden-sm hidden-xs">
                            <ul class="nav navbar-nav">
                                <li class="dropdown">
                                    <a data-toggle="dropdown" class="dropdown-toggle disabled"
                                       href="${pageContext.request.contextPath}/Controller">${home}</a>
                                </li>
                                <li class="dropdown">
                                    <a data-toggle="dropdown" class="dropdown-toggle disabled"
                                       href="${pageContext.request.contextPath}/Controller?command=page&page=1">${booksNMedia}</a>
                                </li>

                                <li class="dropdown">
                                    <a data-toggle="dropdown" class="dropdown-toggle disabled"
                                       href="${pageContext.request.contextPath}/Controller?command=add_media_form">${addMedia}</a>
                                </li>

                                <c:if test="${sessionScope.role == 'librarian' || sessionScope.role == 'admin'}">
                                    <li class="dropdown">
                                        <a data-toggle="dropdown" class="dropdown-toggle disabled"
                                           href="${pageContext.request.contextPath}/Controller?command=user_verification">${userVerification}</a>
                                    </li>
                                    <li class="dropdown">
                                        <a data-toggle="dropdown" class="dropdown-toggle disabled"
                                           href="${pageContext.request.contextPath}/Controller?command=delivery">${delivery}</a>
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
                                <h4>${navigation}</h4>
                                <a href="#" class="close"></a>
                            </li>
                            <li>
                                <c:choose>
                                    <c:when test="${sessionScope.local != 'en_US'}">
                                        <a href="${pageContext.request.contextPath}/Controller?command=change_local&local=en_US&lastCommand=${requestScope.lastCommand}&lastPage=${lastPage}"><i
                                                class="fa"></i>EN</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/Controller?command=change_local&local=ru_RU&lastCommand=${requestScope.lastCommand}&lastPage=${lastPage}"><i
                                                class="fa"></i>RU</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>

                            <c:choose>
                                <c:when test="${login != null}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/Controller?command=profile"><i
                                                class="fa"></i>${yourProfile}</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/Controller?command=log_out"><i
                                                class="fa"></i>${logOut}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/Controller?command=registration_form"><i
                                                class="fa"></i>${register}</a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/Controller?command=login_form"><i
                                                class="fa"></i>${logIn}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                            
                            <li>
                                <a href="${pageContext.request.contextPath}/Controller">${home}</a>
                            </li>

                            <li>
                                <a href="${pageContext.request.contextPath}/Controller?command=page&page=1">${booksNMedia}</a>
                            </li>
                            <c:if test="${sessionScope.role == 'librarian' || sessionScope.role == 'admin'}">
                                <li class="dropdown">
                                    <a href="${pageContext.request.contextPath}/Controller?command=user_verification">${userVerification}</a>
                                </li>
                                <li class="dropdown">
                                    <a href="${pageContext.request.contextPath}/Controller?command=delivery">${delivery}</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</header>