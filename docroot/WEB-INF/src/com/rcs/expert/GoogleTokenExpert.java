package com.rcs.expert;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
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
    ) throws TokenResponseException, IOException, Exception {    	
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
	            	log.info("refresh token succeed access token ");
	            } else {
	                log.info("Could not refresh token.");
	            }
    	    } catch (TokenResponseException e) {
    	    	log.debug("TokenResponseException in GoogleTokenExpert in getToken nr 1");    	    	    	    	
    	    	if(e.getDetails()!=null && e.getDetails().getError().equals("unauthorized_client")) {
    	    		log.debug("unauthorized_client");
    	    		credential = null;
    	    	} else {
    	    		throw e;
    	    	}   	    	    	    
	        } catch (IOException e) {
	        	log.debug("IOException in GoogleTokenExpert in getToken");
                e.printStackTrace();
                throw e;
	        } catch (Exception e) {
	        	log.debug("Exception in GoogleTokenExpert in getToken");
                e.printStackTrace();
                throw e;
	        }
        }         
        
        // finally try to ask user to grant access
        if (credential == null) {
        	if (authorizationCode != null && !authorizationCode.isEmpty() && redirect_url != null && !redirect_url.isEmpty()){
        		log.info("try to ask user to grant access");
	        	try {		        		
	            	GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode).setRedirectUri(redirect_url).execute();
	                credential = flow.createAndStoreCredential(tokenResponse, null);	                	    	       
	            } catch (TokenResponseException e) {                    
                    log.info("TokenResponseException in GoogleTokenExpert in getToken nr 2");
                    e.printStackTrace();
                    throw e;
	            } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
	            }
        	} else {
        		log.error("Invalid Authorization Code");
        	}
        }
        return credential;
    }        
    		
}