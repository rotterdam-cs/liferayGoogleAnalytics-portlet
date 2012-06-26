//************************************************
//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>
//************************************************

function makeApiCallConfiguration() {	
	queryAccountsConfiguration();
}

function queryAccountsConfiguration() {	
	gapi.client.analytics.management.accounts.list().execute(handleAccountsConfiguration);
}

function handleAccountsConfiguration(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			accountsconfiguration = results;		
			clearSelectOption(namespace+"account_id");
			for	(var i=0;i<results.items.length;i++) {
				setSelectOption(namespace+"account_id", results.items[i].id, results.items[i].name);
			}
			var firstAccountId = results.items[0].id;			
			//Only one result and is not yet selected or is the same that the selected one
			if (results.items.length == 1 && (firstAccountId == current_account_id || current_account_id == 0)) {
				jQuery("#"+namespace+"account_id").val(firstAccountId);
				queryWebpropertiesConfiguration(firstAccountId);			
			//More that one result and there is a previous selected one
			}else if(results.items.length > 1 && current_account_id != 0) {
				jQuery("#"+namespace+"account_id").val(current_account_id);
				queryWebpropertiesConfiguration(current_account_id);
			} else {
				jQuery("#"+namespace+"detailed-configuration").unmask();
			}
		} else {
			var error = getLocallizedKey("com.rcs.googleanalytics.error.no.accounts.found");
			console.log(error);
			showError(error);
			jQuery("#"+namespace+"detailed-configuration").unmask();
		}
	} else {
		var error = getLocallizedKey("com.rcs.googleanalytics.error.querying.accounts");
		console.log(error + ': ' + results.message);
		showError(error + ': ' + results.message);
		jQuery("#"+namespace+"detailed-configuration").unmask();
	}
}

function queryWebpropertiesConfiguration(accountId) {
	gapi.client.analytics.management.webproperties.list({
		'accountId' : accountId
	}).execute(handleWebpropertiesConfiguration);
}

function handleWebpropertiesConfiguration(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			clearSelectOption(namespace+"property_id");		
			for	(var i=0;i<results.items.length;i++) {
				setSelectOption(namespace+"property_id", results.items[i].id, results.items[i].name);
			}	
			var firstAccountId = results.items[0].accountId;
			var firstWebpropertyId = results.items[0].id;
			//Only one result and is not yet selected or is the same that the selected one
			if (results.items.length == 1 && (firstWebpropertyId == current_property_id || current_property_id == 0) ) {
				jQuery("#"+namespace+"property_id").val(firstWebpropertyId);
				queryProfilesConfiguration(firstAccountId, firstWebpropertyId);
			//More that one result and there is a previous selected one
			}else if(results.items.length > 1 && current_property_id != 0) {
				jQuery("#"+namespace+"property_id").val(current_property_id);
				queryProfilesConfiguration(firstAccountId, current_property_id);
			} else {
				jQuery("#"+namespace+"detailed-configuration").unmask();
			}
		} else {
			var error = getLocallizedKey("com.rcs.googleanalytics.error.no.webproperties.found");
			console.log(error);
			showError(error);
			jQuery("#"+namespace+"detailed-configuration").unmask();
		}
	} else {
		var error = getLocallizedKey("com.rcs.googleanalytics.error.querying.webproperties");
		console.log(error + ': ' + results.message);
		showError(error + ': ' + results.message);
		jQuery("#"+namespace+"detailed-configuration").unmask();
	}
}

function queryProfilesConfiguration(accountId, webpropertyId) {
	gapi.client.analytics.management.profiles.list({
		'accountId' : accountId,
		'webPropertyId' : webpropertyId
	}).execute(handleProfilesConfiguration);
}

function handleProfilesConfiguration(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {	
			clearSelectOption(namespace+"profile_id");		
			for	(var i=0;i<results.items.length;i++) {
				setSelectOption(namespace+"profile_id", results.items[i].id, results.items[i].name);
			}	
			var firstProfileId = results.items[0].id;
			//Only one result and is not yet selected or is the same that the selected one
			if (results.items.length == 1 && (firstProfileId == current_profile_id || current_profile_id == 0) ) {				
				jQuery("#"+namespace+"profile_id").val(firstProfileId);
				jQuery("#"+namespace+"save-detailed-configuration").removeClass("hidden");
			//More that one result and there is a previous selected one
			} else if(results.items.length > 1 && current_property_id != 0) {
				jQuery("#"+namespace+"profile_id").val(current_property_id);
				jQuery("#"+namespace+"save-detailed-configuration").removeClass("hidden");
			}
			jQuery("#"+namespace+"detailed-configuration").unmask();
		} else {
			var error = getLocallizedKey("com.rcs.googleanalytics.error.no.profiles.found");
			console.log(error);
			showError(error);
			jQuery("#"+namespace+"detailed-configuration").unmask();
		}
	} else {
		var error = getLocallizedKey("com.rcs.googleanalytics.error.querying.profiles");
		console.log(error + ': ' + results.message);
		showError(error + ': ' + results.message);
		jQuery("#"+namespace+"detailed-configuration").unmask();
	}
}
