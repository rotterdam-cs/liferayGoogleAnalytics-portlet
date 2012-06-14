//************************************************
//@author Prj.M@x <pablo.rendon@rotterdam-cs.com>
//************************************************

var scopes = 'https://www.googleapis.com/auth/analytics.readonly';

//Get authorization with the stored Token
function handleClientLoadTokenConfiguration(storedtoken) {
	var token = {
		 access_token : storedtoken
		,expires_in: "3600"
		,state: ""
		,token_type: "Bearer"
	};
	gapi.auth.setToken(token);
	gapi.client.load('analytics', 'v3', makeApiCallConfiguration);
}

//Get authorization the first time
//Step 1
function handleClientLoadConfiguration(apiKey, clientId) {
	gapi.client.setApiKey(apiKey);
	window.setTimeout("checkAuthConfiguration('" + clientId + "')", 1);
}
var isTimeOut = false;
function checkTimeOut() {
	if (isTimeOut) {
		var error = getLocallizedKey("com.rcs.googleanalytics.error.timeout.wrong.parameters");
		console.log(error);
		showError(error);
		jQuery("#"+namespace+"detailed-configuration").unmask();
	}
}
//Step 2
// Call the Google Accounts Service to determine the current user's auth status
// Pass the response to the handleAuthResultConfiguration callback function
function checkAuthConfiguration(clientId) {	
	isTimeOut = true;	
	window.setTimeout(checkTimeOut, 10000);	
	gapi.auth.authorize({
		client_id : clientId,
		scope : scopes,
		immediate : true
	},handleAuthResultConfiguration);
}
//Step 3
//Handle the authorization answer
function handleAuthResultConfiguration(authResult) {
	isTimeOut = false;
	if (authResult) {	
		loadAnalyticsClientConfiguration();
	} else {
		handleUnAuthorizedConfiguration();
	}
}
//Step 4
//If the authorization was OK store the Token to avoid the process next time
//Load the Analytics client and set handleAuthorized as the callback function
function loadAnalyticsClientConfiguration() {
	var receivedToken = gapi.auth.getToken();
	updateToken(receivedToken.access_token);
	gapi.client.load('analytics', 'v3', handleAuthorizedConfiguration);
}
//Step 5
//Call the api configuration
function handleAuthorizedConfiguration() {
	makeApiCallConfiguration();
}
//Step 6
//If the authorization was not OK it shows an error
function handleUnAuthorizedConfiguration() {
	jQuery("#"+namespace+"detailed-configuration").unmask();
	showError(getLocallizedKey("com.rcs.googleanalytics.error.unauthorized.access"));
}