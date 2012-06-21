package com.rcs.googleanalytics.expert;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.analytics.AnalyticsScopes;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component
public class GoogleTokenExpert {
	private static Log log = LogFactoryUtil.getLog(GoogleTokenExpert.class);
	Iterable<String> scope = Arrays.asList(AnalyticsScopes.ANALYTICS_READONLY);
    
	public String getAuthURL(String CLENT_ID, String redirect_url) {
		String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
				 CLENT_ID
				,redirect_url
				,scope)
		.setAccessType("offline")
		.build();
		return authorizeUrl;
	}
	
    public Credential getToken(
    		 String CLENT_ID
    		,String CLENT_SECRET
    		,String refreshToken
    		,String authorizationCode
    		,String redirect_url
    ){
    	GoogleClientSecrets mSecrets;
        HttpTransport mTransport;
        JsonFactory mJsonFactory;    	
        Credential credential = null;
        
        //prepare google auth flow
        GoogleAuthorizationCodeFlow flow = null;
        
        GoogleClientSecrets.Details secretDetails = new GoogleClientSecrets.Details();
        secretDetails.setClientId(CLENT_ID);
        secretDetails.setClientSecret(CLENT_SECRET);

        mSecrets = new GoogleClientSecrets();
        mSecrets.setInstalled(secretDetails);

        mTransport = new ApacheHttpTransport();
        mJsonFactory = new JacksonFactory();

        flow = new GoogleAuthorizationCodeFlow.Builder(
                        mTransport,
                        mJsonFactory,
                        mSecrets,
                        scope)
        .build();
        
        // try to refresh credentials based on the refresh token
        if (refreshToken != null && !refreshToken.isEmpty()) {
	        try {
	        	GoogleTokenResponse restoredResponse = new GoogleTokenResponse();
		        restoredResponse.setRefreshToken(refreshToken);
		        credential = flow.createAndStoreCredential(restoredResponse, null);
	            if (credential.refreshToken()) {
	            	log.error("refresh token succeed access token: " + credential.getAccessToken());
	            } else {
	                log.error("Could not refresh token.");
	            }
	                
	        } catch (IOException e) {
	                e.printStackTrace();
	        }
        }    
                
        // finally try to ask user to grant access
        if (credential == null) {
        	if (authorizationCode != null && !authorizationCode.isEmpty() && redirect_url != null && !redirect_url.isEmpty()){
	        	try {	
	        		log.error("authorizationCode:" + authorizationCode);
	        		log.error("redirect_url:" + redirect_url);
	            	GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode).setRedirectUri(redirect_url).execute();
	                credential = flow.createAndStoreCredential(tokenResponse, null);
	                log.error("Access token: " + tokenResponse.getAccessToken());
	    	        log.error("Refresh token: " + tokenResponse.getRefreshToken());
	    	        log.error("expiresIn: " + tokenResponse.getExpiresInSeconds());	    	        
	            } catch (Exception e) {
                    e.printStackTrace();
	            }
        	} else {
        		log.error("Invalid Authorization Code");
        	}
        }
        return credential;
    }
}