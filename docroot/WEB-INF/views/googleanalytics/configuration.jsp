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
<portlet:resourceURL var="adminSaveConfigurationsURL" id="adminSaveConfigurations" />
<portlet:resourceURL var="adminSaveConfigurationURL" id="adminSaveConfiguration" />

<div class="modal hide fade" id="<portlet:namespace/>helpWindow">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">x</a>
        <h3><i class="icon-question-sign"></i> <fmt:message key="com.rcs.admin.help.center"/> - <fmt:message key="com.rcs.admin.configuration"/> <span style="float: right;"></span></h3>
    </div>
    <div class="modal-body">
        <p><fmt:message key="com.rcs.googleanalytics.admin.help.configuration1"/></p>
        <p><fmt:message key="com.rcs.googleanalytics.admin.help.configuration2"/></p>        
        <p><fmt:message key="com.rcs.googleanalytics.admin.help.configuration3"/></p>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal"><fmt:message key="com.rcs.general.close"/></a>
    </div>
</div>



<form class="well" id="<portlet:namespace/>configurationform">
	<h3><fmt:message key="com.rcs.googleanalytics.register.api.console"/> <a data-toggle="modal" href="#<portlet:namespace/>helpWindow"><i class="icon-question-sign"></i></a></h3>  
    <p>
        <label for="<portlet:namespace/>client_id"><fmt:message key="com.rcs.googleanalytics.configuration.client.id"/>:</label>
        <input type="text" name="<portlet:namespace/>client_id" class="required span6" id="<portlet:namespace/>client_id" value="${configuration.client_id}" />
    </p>
    <p>
        <label for="<portlet:namespace/>api_key"><fmt:message key="com.rcs.googleanalytics.configuration.api.key"/>:</label>
        <input type="text" name="<portlet:namespace/>api_key" class="required span6" id="<portlet:namespace/>api_key" value="${configuration.api_key}" />
    </p>
    <p>
        <button type="button" class="btn" id="<portlet:namespace/>save-configuration" <c:if test="${configuration.client_id != '' && configuration.api_key != ''}" >disabled="true"</c:if> ><fmt:message key="com.rcs.general.save"/></button>
        <button type="button" class="btn btn-primary" id="<portlet:namespace/>authorize-button" style="display: none;"><fmt:message key="com.rcs.general.authorize"/></button>
    </p>
</form>

<form class="well" id="<portlet:namespace/>detailedconfigurationform">
    <div id="<portlet:namespace/>detailed-configuration">
    	<div class="control-group">            
            <div class="controls">
              <label for="<portlet:namespace/>account_id"><fmt:message key="com.rcs.googleanalytics.account"/>:</label>
              <select id="<portlet:namespace/>account_id" name="<portlet:namespace/>account_id">
                <option value="0"><fmt:message key="com.rcs.googleanalytics.select.account"/></option>
              </select>
            </div>
          </div>
          
          <div class="control-group">            
            <div class="controls">
              <label for="<portlet:namespace/>property_id"><fmt:message key="com.rcs.googleanalytics.property"/>:</label>
              <select id="<portlet:namespace/>property_id" name="<portlet:namespace/>property_id">
                <option value="0"><fmt:message key="com.rcs.googleanalytics.select.property"/></option>
              </select>
            </div>
          </div>
          
          <div class="control-group">            
            <div class="controls">
              <label for="<portlet:namespace/>profile_id"><fmt:message key="com.rcs.googleanalytics.profile"/>:</label>
              <select id="<portlet:namespace/>profile_id" name="<portlet:namespace/>profile_id">
                <option value="0"><fmt:message key="com.rcs.googleanalytics.select.profile"/></option>
              </select>
            </div>
          </div>
          <p>
	          <button type="button" class="btn hidden" id="<portlet:namespace/>save-detailed-configuration" ><fmt:message key="com.rcs.general.save"/></button>
	      </p>
    </div>
</form>

<script type="text/javascript">	

	function updateToken(token) {
		jQuery.post("${adminSaveConfigurationURL}",{
		        "configurationname" : "token"                
		        ,"configurationvalue" : token
		    }, function(responseText) {
		    	var response = getResponseTextInfo(responseText);
	            if (!response[0]) {
	            	console.log("error updating token: " + response[1]);                           
	            } else {
	            	console.log("token updated: " + response[1]);	                
	            }   
		    }
		);         
	} 

    jQuery(function() {
    	<c:if test="${configuration.account_id != ''}" >current_account_id = ${configuration.account_id};</c:if>
    	<c:if test="${configuration.property_id != ''}" >current_property_id = '${configuration.property_id}';</c:if>
    	<c:if test="${configuration.profile_id != ''}" >current_profile_id = ${configuration.profile_id};</c:if>
 
        <%--//Handle SAVE Response--%>
        function saveHandleResponse(responseText, statusText, xhr, form) {  
        	jQuery("#<portlet:namespace/>administration-container-mask").unmask();
        	var response = getResponseTextInfo(responseText);
            if (!response[0]) {
            	showError(response[1]);                           
            } else {
            	showInfo(response[1]);               
                jQuery(".sense-admin-right-menu li.disabled").removeClass("disabled");
                jQuery("#<portlet:namespace/>save-configuration").attr("disabled", true);
                var apiKey = jQuery("#<portlet:namespace/>api_key").val();
        		var clientId = jQuery("#<portlet:namespace/>client_id").val();
                retreiveGoogleAnalyticsAccountInfo(apiKey, clientId);
            }            
        }
        
        <%--//Handle Deatiled SAVE Response--%>
        function saveDeatiledHandleResponse(responseText, statusText, xhr, form) {  
        	jQuery("#<portlet:namespace/>administration-container-mask").unmask();
        	var response = getResponseTextInfo(responseText);
            if (!response[0]) {
            	showError(response[1]);                           
            } else {
            	showInfo(response[1]);                
            }            
        }
        
        <%--//Enable save when onchange--%>
        jQuery(document).on("keypress", "#<portlet:namespace/>client_id", function() {
            jQuery("#<portlet:namespace/>save-configuration").attr("disabled", false);            
        });
        jQuery(document).on("keypress", "#<portlet:namespace/>api_key", function() {
            jQuery("#<portlet:namespace/>save-configuration").attr("disabled", false);            
        });
        
        <%--//Form Options for Save Button --%>
        var optionsSave = {
            url : '${adminSaveConfigurationsURL}'
            ,type : 'POST'
            ,success : saveHandleResponse
        };
        
        <%--//Form Options for Detailed Save Button --%>
        var detailedOptionsSave = {
            url : '${adminSaveConfigurationsURL}'
            ,type : 'POST'
            ,success : saveDeatiledHandleResponse
        };
        
        <%--//Validation Options --%>
        jQuery("#<portlet:namespace/>configurationform").validate({
       	  rules: {
       		  '<portlet:namespace/>client_id': {
	       	       required: true
	       	      ,maxlength: 150
       		  }
       		  ,'<portlet:namespace/>api_key': {
	       	       required: true
	       	      ,maxlength: 150
       		  }
       	  }
       	});
        
        <%--//Validation Options for detailed form --%>
        jQuery("#<portlet:namespace/>detailedconfigurationform").validate({
       	  rules: {
       		  '<portlet:namespace/>account_id': {
	       	       required: true
       		  }
       		  ,'<portlet:namespace/>property_id': {
	       	       required: true
       		  }
       		  ,'<portlet:namespace/>profile_id': {
	       	       required: true
      		  }
       	  }
       	});
        
        <%--//Configuration Form Button Listener --%>
        jQuery("#<portlet:namespace/>save-configuration").click(function() {
            if(jQuery('#<portlet:namespace/>configurationform').valid()) {
                jQuery("#<portlet:namespace/>administration-container-mask").mask('<fmt:message key="com.rcs.general.mask.loading.text"/>');
                jQuery('#<portlet:namespace/>configurationform').ajaxSubmit(optionsSave);
            }
        });
        
        <%--//Detailed Configuration Form Button Listener --%>
        jQuery("#<portlet:namespace/>save-detailed-configuration").click(function() {
            if(jQuery('#<portlet:namespace/>detailedconfigurationform').valid()) {
                jQuery("#<portlet:namespace/>administration-container-mask").mask('<fmt:message key="com.rcs.general.mask.loading.text"/>');
                jQuery('#<portlet:namespace/>detailedconfigurationform').ajaxSubmit(detailedOptionsSave);
            }
        });        
        
        <%--//Authorization Form Button Listener --%>
        jQuery("#<portlet:namespace/>authorize-button").click(handleAuthClick); 
        
        <%--//Listeners for Combo Boxes --%>
        jQuery("#<portlet:namespace/>account_id").on("change", function() {
        	var selected_value = jQuery(this).val();
        	if (selected_value != 0) {
        		queryWebpropertiesConfiguration(selected_value);
        	} else {
        		jQuery("#<portlet:namespace/>save-detailed-configuration").addClass("hidden");
        		clearSelectOption("<portlet:namespace/>property_id");
        		clearSelectOption("<portlet:namespace/>profile_id");
        	}
        });
        jQuery("#<portlet:namespace/>property_id").on("change", function() {
        	var selected_value = jQuery(this).val();
        	var selectedAccountId = jQuery("#<portlet:namespace/>account_id").val();
        	if (selected_value != 0 && selectedAccountId != 0) {
        		queryProfilesConfiguration(selectedAccountId, selected_value);
        	} else {
        		jQuery("#<portlet:namespace/>save-detailed-configuration").addClass("hidden");
        		clearSelectOption("<portlet:namespace/>profile_id");
        	}
        });
        jQuery("#<portlet:namespace/>profile_id").on("change", function() {
        	var selected_value = jQuery(this).val();
        	if (selected_value != 0) {
        		jQuery("#<portlet:namespace/>save-detailed-configuration").removeClass("hidden");        		
        	} else {
        		jQuery("#<portlet:namespace/>save-detailed-configuration").addClass("hidden");
        	}
        });
    });  
</script>