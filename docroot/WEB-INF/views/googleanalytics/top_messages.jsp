<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%@taglib prefix="liferay-util" uri="http://liferay.com/tld/util" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="Language"/>
<portlet:defineObjects />
<div class="alert alert-block alert-error fade in" <c:if test="${errorMessage == null}" >style="display: none;"</c:if>>
    <h4 class="alert-heading"><fmt:message key="com.rcs.general.error.processing.data"/></h4>
    <p id="<portlet:namespace/>alert-content"><c:out value="${errorMessage}" escapeXml="false" /></p>
</div>
<div class="alert alert-success fade in" <c:if test="${infoMessage == null}" >style="display: none;"</c:if>>
    <span id="<portlet:namespace/>info-content"><c:out value="${infoMessage}" escapeXml="false" /></span>
</div>
<script type="text/javascript">
    jQuery(function() {
        <c:if test="${errorMessage == null}" >
            jQuery(".alert-error").hide();
        </c:if>
        <c:if test="${infoMessage == null}" >
            jQuery(".alert-success").hide();
        </c:if>
    });
</script>