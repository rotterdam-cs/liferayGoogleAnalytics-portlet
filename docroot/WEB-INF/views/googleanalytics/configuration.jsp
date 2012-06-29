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
        <p></p>        
        <p></p>
        <h4><fmt:message key="com.rcs.googleanalytics.admin.help.configuration2"/></h4>
        <p><fmt:message key="com.rcs.googleanalytics.admin.help.configuration3"/></p>

        <ol>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration4"/></li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration5"/></li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration6"/></li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration7"/></li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration8"/>
            <ol>
              <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration9"/></li>
              <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration10"/></li>
              <li class="control-group error"><fmt:message key="com.rcs.googleanalytics.admin.help.configuration11"/>
              <br><textarea rows="4" id="textarea" class="input-xlarge  uneditable-input" style="width: 500px; height: 60px;">${fullCurrentURL}</textarea></li>
              <li class="control-group error"><fmt:message key="com.rcs.googleanalytics.admin.help.configuration12"/>
              <br><textarea rows="1" id="textarea" class="input-xlarge  uneditable-input" style="width: 500px; height: 20px;">${serverURL}</textarea></li>
              <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration13"/></li>
            </ol>
          </li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration14"/></li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration15"/></li>
          <li>
            <fmt:message key="com.rcs.googleanalytics.admin.help.configuration16"/>
            <ul>
              <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration17"/></li>
              <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration18"/></li>
            </ul>
          </li>
          <li><fmt:message key="com.rcs.googleanalytics.admin.help.configuration19"/></li>
        </ol>
        
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
        <label for="<portlet:namespace/>client_secret"><fmt:message key="com.rcs.googleanalytics.configuration.client.secret"/>:</label>
        <input type="text" name="<portlet:namespace/>client_secret" class="required span6" id="<portlet:namespace/>client_secret" value="${configuration.client_secret}" />
    </p>
    <p>
        <button type="button" class="btn" id="<portlet:namespace/>save-configuration" <c:if test="${configuration.client_id != '' && configuration.client_secret != ''}" >disabled="true"</c:if> ><fmt:message key="com.rcs.general.save"/></button>
        <button type="button" class="btn btn-primary" id="<portlet:namespace/>authorize-button" <c:if test="${authURL == null || (googleAnalyticsAccounts != null && googleAnalyticsAccounts.success == 'TRUE') }" > style="display: none"; </c:if> ><fmt:message key="com.rcs.general.authorize"/></button>
    </p>
</form>

<form class="well" id="<portlet:namespace/>detailedconfigurationform" <c:if test="${googleAnalyticsAccounts == null || googleAnalyticsAccounts.success != 'TRUE' }" > style="display: none"; </c:if> >
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
	function queryAccountsConfiguration(googleAnalyticsAccountsJSON){
		clearSelectOption("<portlet:namespace/>account_id");
		jQuery("#<portlet:namespace/>save-detailed-configuration").addClass("hidden");
		jQuery.each(googleAnalyticsAccountsJSON.accounts, function(index, value) {
            setSelectOption("<portlet:namespace/>account_id", value.value, value.html);
            if (value.selected == true) {
				jQuery("#<portlet:namespace/>account_id").val(value.value);
				queryWebpropertiesConfiguration(googleAnalyticsAccountsJSON, value.value);
			}
        });
	}
	function queryWebpropertiesConfiguration(googleAnalyticsAccountsJSON, accountId){
		clearSelectOption("<portlet:namespace/>property_id");
		jQuery("#<portlet:namespace/>save-detailed-configuration").addClass("hidden");
		jQuery.each(googleAnalyticsAccountsJSON.accounts, function(index, value) {
			if (value.value == accountId) {
				jQuery.each(value.webProperties, function(indexwp, valuewp) {
					setSelectOption("<portlet:namespace/>property_id", valuewp.value, valuewp.html);
					if (valuewp.selected == true) {
						jQuery("#<portlet:namespace/>property_id").val(valuewp.value);
						queryProfilesConfiguration(googleAnalyticsAccountsJSON, accountId, valuewp.value);
					}
				});
			}
        });
	}
	function queryProfilesConfiguration(googleAnalyticsAccountsJSON, accountId, selected_value){
		clearSelectOption("<portlet:namespace/>profile_id");
		jQuery("#<portlet:namespace/>save-detailed-configuration").addClass("hidden");
		jQuery.each(googleAnalyticsAccountsJSON.accounts, function(index, value) {
			if (value.value == accountId) {
				jQuery.each(value.webProperties, function(indexwp, valuewp) {
					if (valuewp.value == selected_value) {
						jQuery.each(valuewp.profiles, function(indexprof, valueprof) {
							setSelectOption("<portlet:namespace/>profile_id", valueprof.value, valueprof.html);
							if (valueprof.selected == true) {
								jQuery("#<portlet:namespace/>profile_id").val(valueprof.value);
								jQuery("#<portlet:namespace/>save-detailed-configuration").removeClass("hidden");
							}
						});
					}
				});
			}
        });
	}
	var authUrl = "${authURL}"; 
	function handleAuthClick(event) {
		if (authUrl!=""){
			window.location=authUrl;
		} else {
			console.log("invalid Authorization URL");
		}
		return false;
	}
	
    jQuery(function() {
    	<c:if test="${googleAnalyticsAccountsJSON != null && googleAnalyticsAccountsJSON != ''}" >
    	   var googleAnalyticsAccountsJSON = ${googleAnalyticsAccountsJSON};
    	   queryAccountsConfiguration(googleAnalyticsAccountsJSON);
    	</c:if>
    	<c:if test="${authURL != null && authURL != ''}" >
    		jQuery("#<portlet:namespace/>authorize-button").show();
    		jQuery("#<portlet:namespace/>save-configuration").hide();
    		jQuery("#<portlet:namespace/>detailedconfigurationform").hide();    		
    	</c:if>
    	<c:if test="${errorMessage != null && errorMessage != ''}" >showError('${errorMessage}');</c:if>
    	<c:if test="${infoMessage != null && infoMessage != ''}" >showInfo('${infoMessage}');</c:if>
    	
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
                var responseBodyObj = jQuery.parseJSON(response[2]);
                authUrl = responseBodyObj.authURL;
                if (responseBodyObj.isValidAccess) {
                	jQuery("#<portlet:namespace/>authorize-button").hide();
            		jQuery("#<portlet:namespace/>save-configuration").show();
            		jQuery("#<portlet:namespace/>detailedconfigurationform").show();
                } else {
                	jQuery("#<portlet:namespace/>authorize-button").show();
            		jQuery("#<portlet:namespace/>save-configuration").hide();
            		jQuery("#<portlet:namespace/>detailedconfigurationform").hide();
                }
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
            	jQuery(".admin-right-menu li.disabled").removeClass("disabled");
            }            
        }
        
        <%--//Enable save when onchange--%>
        jQuery(document).on("keypress", "#<portlet:namespace/>client_id", function() {
            jQuery("#<portlet:namespace/>save-configuration").attr("disabled", false); 
            jQuery("#<portlet:namespace/>save-configuration").show();
            jQuery("#<portlet:namespace/>authorize-button").hide();
        });
        jQuery(document).on("change", "#<portlet:namespace/>client_id", function() {
            jQuery("#<portlet:namespace/>save-configuration").attr("disabled", false);
            jQuery("#<portlet:namespace/>save-configuration").show();
            jQuery("#<portlet:namespace/>authorize-button").hide();
        });
        jQuery(document).on("keypress", "#<portlet:namespace/>client_secret", function() {
            jQuery("#<portlet:namespace/>save-configuration").attr("disabled", false);
            jQuery("#<portlet:namespace/>save-configuration").show();
            jQuery("#<portlet:namespace/>authorize-button").hide();
        });
        jQuery(document).on("change", "#<portlet:namespace/>client_secret", function() {
            jQuery("#<portlet:namespace/>save-configuration").attr("disabled", false);
            jQuery("#<portlet:namespace/>save-configuration").show();
            jQuery("#<portlet:namespace/>authorize-button").hide();
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
       		  ,'<portlet:namespace/>client_secret': {
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
        		queryWebpropertiesConfiguration(googleAnalyticsAccountsJSON, selected_value);
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
        		queryProfilesConfiguration(googleAnalyticsAccountsJSON, selectedAccountId, selected_value);
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