//************************************************
//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>
//https://developers.google.com/chart/image/docs/chart_wizard
//https://developers.google.com/chart/image/docs/data_formats#encoding_data
//https://developers.google.com/analytics/resources/articles/gdataAnalyticsCharts
//https://developers.google.com/analytics/devguides/reporting/core/dimsmets
//
//https://developers.google.com/analytics/devguides/reporting/core/v3/
//https://developers.google.com/analytics/resources/tutorials/hello-analytics-api
//https://code.google.com/apis/console/?pli=1#project:374594455776:access
//************************************************

function makeApiCall() {
	jQuery("#mod_analytics").removeClass("hidden");
	jQuery("#mod_analytics").mask('Loading...');	
	queryAccounts();
}
function makeApiCallConfiguration() {
	jQuery("#"+namespace+"detailed-configuration").mask('Loading...');
	queryAccountsConfiguration();
}



function queryAccounts() {
	console.log('Querying Accounts.');

	// Get a list of all Google Analytics accounts for this user
	gapi.client.analytics.management.accounts.list().execute(handleAccounts);
	console.log('Querying Accounts1.');
}
function queryAccountsConfiguration() {	
	gapi.client.analytics.management.accounts.list().execute(handleAccountsConfiguration);
}



function handleAccounts(results) {
	console.log('handleAccounts:' + results);
	if (!results.code) {
		if (results && results.items && results.items.length) {

			console.log('handleAccounts: ' + results.items.length);			
			for	(var i=0;i<results.items.length;i++) {
				console.log('handleAccount: ' + results.items[i].id);
			}
			
			// Get the first Google Analytics account
			var firstAccountId = results.items[0].id;

			// Query for Web Properties
			queryWebproperties(firstAccountId);

		} else {
			console.log('No accounts found for this user.');
		}
	} else {
		console.log('There was an error querying accounts: ' + results.message);
	}
}
function handleAccountsConfiguration(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			accountsconfiguration = results;		
			for	(var i=0;i<results.items.length;i++) {
				setSelectOption(namespace+"account_id", results.items[i].id, results.items[i].name);
			}
			var firstAccountId = results.items[0].id;			
			if (results.items.length == 1 && (firstAccountId == current_account_id || current_account_id == 0)) {
				jQuery("#"+namespace+"account_id").val(firstAccountId);
				queryWebpropertiesConfiguration(firstAccountId);				
			} else {
				jQuery("#"+namespace+"detailed-configuration").unmask();
			}
		} else {
			console.log('No accounts found for this user.');
			showError('@@No accounts found for this user.');
		}
	} else {
		console.log('There was an error querying accounts: ' + results.message);
		showError('@@There was an error querying accounts: ' + results.message);
	}
}



function queryWebproperties(accountId) {
	console.log('Querying Webproperties.');

	// Get a list of all the Web Properties for the account
	gapi.client.analytics.management.webproperties.list({
		'accountId' : accountId
	}).execute(handleWebproperties);
}
function queryWebpropertiesConfiguration(accountId) {
	gapi.client.analytics.management.webproperties.list({
		'accountId' : accountId
	}).execute(handleWebpropertiesConfiguration);
}


var lalala;

function handleWebproperties(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			lalala = results;
			console.log('handleWebproperties: ' + results.items.length);			
			for	(var i=0;i<results.items.length;i++) {
				console.log('handleWebpropertie: ' + results.items[i].id + " Name: " + results.items[i].name);
			}
			
			// Get the first Google Analytics account
			var firstAccountId = results.items[1].accountId;
			console.log("Selected accountId: " + results.items[1].accountId);

			// Get the first Web Property ID
			var firstWebpropertyId = results.items[1].id;
			console.log("Selected id: " + results.items[1].id);

			// Query for Profiles
			queryProfiles(firstAccountId, firstWebpropertyId);

		} else {
			console.log('No webproperties found for this user.');
		}
	} else {
		console.log('There was an error querying webproperties: '
				+ results.message);
	}
}
function handleWebpropertiesConfiguration(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {		
			for	(var i=0;i<results.items.length;i++) {				
				setSelectOption(namespace+"property_id", results.items[i].id, results.items[i].name);
			}	
			var firstAccountId = results.items[0].accountId;
			var firstWebpropertyId = results.items[0].id;
			if (results.items.length == 1 && (firstWebpropertyId == current_property_id || current_account_id == 0) ) {
				jQuery("#"+namespace+"property_id").val(firstWebpropertyId);
				queryProfilesConfiguration(firstAccountId, firstWebpropertyId);
			} else {
				jQuery("#"+namespace+"detailed-configuration").unmask();
			}
		} else {
			console.log('No webproperties found for this user.');
			showError('@@No webproperties found for this user ');
		}
	} else {
		console.log('There was an error querying webproperties: ' + results.message);
		showError('@@There was an error querying webproperties: ' + results.message);
	}
}


function queryProfiles(accountId, webpropertyId) {
	console.log('Querying Profiles.');

	// Get a list of all Profiles for the first Web Property of the first
	// Account
	gapi.client.analytics.management.profiles.list({
		'accountId' : accountId,
		'webPropertyId' : webpropertyId
	}).execute(handleProfiles);
}
function queryProfilesConfiguration(accountId, webpropertyId) {
	gapi.client.analytics.management.profiles.list({
		'accountId' : accountId,
		'webPropertyId' : webpropertyId
	}).execute(handleProfilesConfiguration);
}

function handleProfiles(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			
			console.log('Profiles: ' + results.items.length);			
			for	(var i=0;i<results.items.length;i++) {
				console.log('Profile: ' + results.items[i].id);
			}
			
			// Get the first Profile ID
			var firstProfileId = results.items[0].id;

			// Step 3. Query the Core Reporting API
			queryCoreReportingApi(firstProfileId);

		} else {
			console.log('No profiles found for this user.');
		}
	} else {
		console.log('There was an error querying profiles: ' + results.message);
	}
}
function handleProfilesConfiguration(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {			
			for	(var i=0;i<results.items.length;i++) {
				setSelectOption(namespace+"profile_id", results.items[i].id, results.items[i].name);
			}	
			var firstProfileId = results.items[0].id;
			if (results.items.length == 1 && (firstProfileId == current_profile_id || current_account_id == 0) ) {				
				jQuery("#"+namespace+"profile_id").val(firstProfileId);
			}
			jQuery("#"+namespace+"detailed-configuration").unmask();
		} else {
			console.log('No profiles found for this user.');
		}
	} else {
		console.log('There was an error querying profiles: ' + results.message);
	}
}

function queryCoreReportingApi(profileId) {
	selProfileId = profileId;
	console.log('Querying Core Reporting API.');

	// Use the Analytics Service Object to query the Core Reporting API
	gapi.client.analytics.data.ga.get({
		 'ids' : 'ga:' + profileId
		,'start-date' : '2012-05-04'
		,'end-date' : '2012-06-04'
		//,'metrics' : 'ga:visitors'
		,'metrics' : 'ga:visits,ga:visitors,ga:newVisits,ga:pageviews,ga:pageviewsPerVisit,ga:avgTimeOnSite,ga:visitBounceRate,ga:percentNewVisits'
		//,'dimensions' : 'ga:day,ga:visitorType'
		,'dimensions' : 'ga:date'
	    //,'metrics' : 'ga:visits'
	    ,'sort' : 'ga:date'
	    ,'max-results' : '100'	
	}).execute(handleCoreReportingResults);
}
function handleCoreReportingResults(results) {
	if (results.error) {
		console.log('There was an error querying core reporting API: ' + results.message);
	} else {
		printResults(results);
	}
}
var resultado;
function printResults(results) {
	if (results.rows && results.rows.length) {
		resultado = results;
		drawLiferayGoogleAnalyticsGraphics(results);
	} else {
		console.log('No results found');
	}
}

function queryCoreReportingApiPrev() {
	jQuery("#mod_analytics").mask('Loading...');
	if (data.visits.prev.data.length > 0){
		initprev(data);
	} else {
		if (selProfileId) {
			console.log('Querying Core Reporting API.');
		
			// Use the Analytics Service Object to query the Core Reporting API
			gapi.client.analytics.data.ga.get({
				 'ids' : 'ga:' + selProfileId
				,'start-date' : '2012-04-04'
				,'end-date' : '2012-05-05'
				,'metrics' : 'ga:visits,ga:visitors,ga:newVisits,ga:pageviews,ga:pageviewsPerVisit,ga:avgTimeOnSite,ga:visitBounceRate,ga:percentNewVisits'
				//,'dimensions' : 'ga:day,ga:visitorType'
				,'dimensions' : 'ga:date'
			    //,'metrics' : 'ga:visits'
			    ,'sort' : 'ga:date'
			    ,'max-results' : '100'	
			}).execute(handleCoreReportingResultsPrev);
		}
	}
}
function handleCoreReportingResultsPrev(results) {
	if (results.error) {
		console.log('There was an error querying core reporting API: ' + results.message);
	} else {
		printResultsPrev(results);
	}
}

var resultadoprev;
function printResultsPrev(results) {
	if (results.rows && results.rows.length) {
		resultadoprev = results;
		drawLiferayGoogleAnalyticsGraphicsPrev(results);
	} else {
		console.log('No results found');
	}
}
