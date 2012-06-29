package com.rcs.expert;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
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

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Component
public class GoogleTokenExpert {
	private static Log log = LogFactoryUtil.getLog(GoogleTokenExpert.class);
	Iterable<String> scope = Arrays.asList(AnalyticsScopes.ANALYTICS_READONLY);
    
	/**
	 * @param CLENT_ID
	 * @param redirect_url
	 * @return
	 */
	public String getAuthURL(String CLENT_ID, String redirect_url) {
		String authorizeUrl = new GoogleAuthorizationCodeRequestUrl(
				 CLENT_ID
				,redirect_url
				,scope)
		.setAccessType("offline")
		.build();
		return authorizeUrl;
	}
	
	/**
	 * @param CLENT_ID
	 * @param CLENT_SECRET
	 * @param refreshToken
	 * @param authorizationCode
	 * @param redirect_url
	 * @return
	 */
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
	        	log.error("try to refresh credentials based on the refresh token");
	        	GoogleTokenResponse restoredResponse = new GoogleTokenResponse();
	        	restoredResponse.setRefreshToken(refreshToken);
		        credential = flow.createAndStoreCredential(restoredResponse, null);
		        
	            if (credential.refreshToken()) {
	            	log.error("refresh token succeed access token: " + credential.getAccessToken());
	            } else {
	                log.error("Could not refresh token.");
	            }
    	    } catch (TokenResponseException e) {
    	    	credential = null;
    	      if (e.getDetails() != null) {
    	    	  log.error("Error: " + e.getDetails().getError());
    	        if (e.getDetails().getErrorDescription() != null) {
    	        	log.error(e.getDetails().getErrorDescription());
    	        }
    	        if (e.getDetails().getErrorUri() != null) {
    	        	log.error(e.getDetails().getErrorUri());
    	        }
    	      } else {
    	    	  log.error(e.getMessage());
    	      }    	    
	            
	        } catch (IOException e) {
	                e.printStackTrace();
	                log.error("Error");
	        }
        }         
        
        // finally try to ask user to grant access
        if (credential == null) {
        	if (authorizationCode != null && !authorizationCode.isEmpty() && redirect_url != null && !redirect_url.isEmpty()){
        		log.error("try to ask user to grant access");
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