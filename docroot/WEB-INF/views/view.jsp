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

<div class="container-fluid" sytle="width: 800px; height: 600px;">
    <div class="row-fluid admin-mask" id="<portlet:namespace/>report-container-mask">
        <div class="span9">
            <%@include file="googleanalytics/top_messages.jsp" %>
            <div id="<portlet:namespace/>report-container" ></div>
        </div>        
    </div>
</div>

<script type="text/javascript">
		
    Liferay.on('portletReady', function(event) {            
        if('_' + event.portletId + '_' == '<portlet:namespace/>') {
        	defaultErrorMessage = '<fmt:message key="com.rcs.general.error"/>';
         	<c:if test="${messages != ''}" >messages = ${messages};</c:if>
         	namespace = '<portlet:namespace/>';
                        
            <%--//Actions to perform when change section --%>
            //@@ if: si es posible mostrar el reporte
            //jQuery("#<portlet:namespace/>report-container-mask").mask('<fmt:message key="com.rcs.general.mask.loading.text"/>');
            //jQuery(".alert").hide();
            //var link = jQuery(this).attr("href").replace("#","");                
            //jQuery(".toHide").addClass("hidden");
            //jQuery("#<portlet:namespace/>" + jQuery(this).attr("rel")).addClass("toHide").removeClass("hidden");                              
            
            jQuery("#<portlet:namespace/>report-container").load("${adminSectionsURL}"
                ,{
                    "section" : "view_reports"//@@agregar constante
                }
                ,function() {
                    //jQuery("#<portlet:namespace/>report-container-mask").unmask();
                }
            );            
            //@@show message: you need to configure your account in control panel
           
        }
    });
</script>