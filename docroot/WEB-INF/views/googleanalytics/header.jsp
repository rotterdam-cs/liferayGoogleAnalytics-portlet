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

<div class="taglib-header "> 
    <span class="header-back-to"> 
        <a data-toggle="modal" href="#<portlet:namespace/>helpWindow"><i class="icon-question-sign"></i></a> 
    </span> 
    <h1 class="header-title">
        <span>            
            <span class="hidden" id="<portlet:namespace/>admin-menu-configuration"><i class="icon-share"></i> <fmt:message key="com.rcs.admin.configuration"/></span>
            <span class="hidden" id="<portlet:namespace/>admin-menu-analytics"><i class="icon-signal"></i> <fmt:message key="com.rcs.admin.view.reports"/></span>
        </span>
    </h1> 
</div>