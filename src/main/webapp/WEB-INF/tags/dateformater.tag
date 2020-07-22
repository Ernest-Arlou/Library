<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="date" required="true" type="java.time.LocalDate" %>
<%@ attribute name="local" required="true" type="java.lang.String" %>
<%@ attribute name="pattern" required="false" type="java.lang.String" %>

<c:if test="${empty local}">
    <c:set var="local" value="ru_RU"/>
</c:if>

<c:if test="${local == 'ru_RU'}">
    <c:set var="pattern" value="dd-MM-yyyy"/>
</c:if>

<c:if test="${local == 'en_US'}">
    <c:set var="pattern" value="MM-dd-yyyy"/>
</c:if>

<fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
<fmt:formatDate value="${parsedDate}" type="date" pattern="${pattern}"/>




