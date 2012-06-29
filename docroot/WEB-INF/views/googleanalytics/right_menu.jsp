<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" import="com.rcs.common.Constants"%>
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

<ul class="nav nav-list">       
   <c:choose>
       <c:when test="${googleAnalyticsAccounts == null || googleAnalyticsAccounts.success != 'TRUE' || configuration.profile_id == '' }" >
	       <li>
	           <a href="#<%=Constants.ADMIN_SECTION_CONFIGURATION%>" data-toggle="tab" rel="admin-menu-configuration">
	               <i class="icon-share"></i><fmt:message key="com.rcs.admin.configuration"/>                            
	           </a>
	       </li>
	
	       <li class="disabled">
	           <a href="#<%=Constants.ADMIN_SECTION_VIEW_REPORTS%>" data-toggle="tab" rel="admin-menu-analytics">
	               <i class="icon-signal"></i><fmt:message key="com.rcs.admin.view.reports"/>                           
	           </a>
	       </li>
       </c:when>
       <c:otherwise>
	       <li>
	           <a href="#<%=Constants.ADMIN_SECTION_VIEW_REPORTS%>" data-toggle="tab" rel="admin-menu-analytics">
	               <i class="icon-signal"></i><fmt:message key="com.rcs.admin.view.reports"/>                           
	           </a>
	       </li>
	       <li>
	           <a href="#<%=Constants.ADMIN_SECTION_CONFIGURATION%>" data-toggle="tab" rel="admin-menu-configuration">
	               <i class="icon-share"></i><fmt:message key="com.rcs.admin.configuration"/>                            
	           </a>
	       </li>
       </c:otherwise>
   </c:choose>       
</ul>