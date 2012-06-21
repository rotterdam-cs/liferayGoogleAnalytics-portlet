<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
<portlet:resourceURL var="adminSectionsURL" id="adminSections" />
<portlet:resourceURL var="getLocallizedKeyURL" id="getLocallizedKey" />

<%@include file="header.jsp" %>

<div class="container-fluid">
    <div class="row-fluid admin-mask" id="<portlet:namespace/>administration-container-mask">
        <div class="span9 admin-left-container">
            <%@include file="top_messages.jsp" %>
            <div id="<portlet:namespace/>administration-container" ></div>
        </div>
        <div class="span2 sense-admin-right-menu">
            <%@include file="right_menu.jsp" %>
        </div>
    </div>
</div>

<c:if test="${authURL != '' && authURL != null}" ><a href="${authURL}">Authorize -> ${authURL}</a></c:if>

<c:if test="${googleAnalyticsAccounts != null}" >
	<c:forEach items="${googleAnalyticsAccounts.accounts}" var="row" varStatus="rowCounter">  
		Account ${row.value} - ${row.html}<br />
		<c:forEach items="${row.webProperties}" var="row2" varStatus="rowCounter2">  
			--------WebProperties ${row2.value} - ${row2.html}<br />
			<c:forEach items="${row2.profiles}" var="row3" varStatus="rowCounter3">  
				-------------------Profiles ${row3.value} - ${row3.html}<br />
			</c:forEach>			
		</c:forEach>		
	</c:forEach>
</c:if>


<script type="text/javascript">
	<%--//Retreive Google Analytics Account Information--%>
	function retreiveGoogleAnalyticsAccountInfo(apiKey, clientId) {
		<%--//Initial or manual autentication--%>
		if (apiKey != null && apiKey != "" && clientId != null && clientId != "") {
			jQuery("#<portlet:namespace/>detailed-configuration").mask('Retrieving Google Analytics account information...');
        	handleClientLoadConfiguration(apiKey, clientId);        
		}
		<%--//Automatic autentication based on the token if it exists--%>
		<c:if test="${configuration.token != ''}" >
			else {
				jQuery("#<portlet:namespace/>detailed-configuration").mask('Retrieving Google Analytics account information...');
				//setTimeout("handleClientLoadTokenConfiguration('${configuration.token}')", 1000);
			}
		</c:if>
	}
	
    Liferay.on('portletReady', function(event) {            
        if('_' + event.portletId + '_' == '<portlet:namespace/>') {
        	defaultErrorMessage = '<fmt:message key="com.rcs.general.error"/>';
         	<c:if test="${errors != ''}" >messages = ${messages};</c:if>
        	namespace = '<portlet:namespace/>';
    		current_account_id = 0;
        	current_property_id = 0;
        	current_profile_id = 0;
    		var maxValue = 1;
    		var previous = 'Previous';
    		var dateFormat = 'mm/dd/yy';
    		var dateFormatLong = 'dddd, mmmm dd, yyyy';
    		var selProfileId;
    		var dataType;
    		var currentKPI;
    		var plot;
    		var data;  
            <%--//Load the first section (Account)--%>
            jQuery(function () {
                jQuery('a[data-toggle="tab"]:first').tab('show');
            });
            
            <%--//Actions to perform when change section --%>
            jQuery('a[data-toggle="tab"]').on('shown', function () {
                jQuery("#<portlet:namespace/>administration-container-mask").mask('<fmt:message key="com.rcs.general.mask.loading.text"/>');
                jQuery(".alert").hide();
                var link = jQuery(this).attr("href").replace("#","");                
                jQuery(".toHide").addClass("hidden");
                jQuery("#<portlet:namespace/>" + jQuery(this).attr("rel")).addClass("toHide").removeClass("hidden");                              
                
                jQuery("#<portlet:namespace/>administration-container").load("${adminSectionsURL}"
                    ,{
                        "section" : link
                    }
                    ,function() {
                        jQuery("#<portlet:namespace/>administration-container-mask").unmask();
                    }
                );
            });            
            
        }
    });
</script>