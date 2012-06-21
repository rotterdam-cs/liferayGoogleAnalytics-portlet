package com.rcs.googleanalytics.expert;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.Account;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.Profile;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import com.google.api.services.analytics.model.Webproperty;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ResourceBundleHelper;
import com.rcs.common.ServiceActionResult;
import com.rcs.googleanalytics.dto.ConfigurationDTO;
import com.rcs.googleanalytics.dto.GoogleAnalyticsAccountsDTO;
import com.rcs.googleanalytics.dto.GoogleAnalyticsParamAccountDTO;
import com.rcs.googleanalytics.dto.GoogleAnalyticsParamProfilesDTO;
import com.rcs.googleanalytics.dto.GoogleAnalyticsParamPropertiesDTO;
import com.rcs.googleanalytics.enums.GoogleAnalyticsConfigurationEnum;
import com.rcs.service.model.Configuration;

@Component
public class GoogleAnalyticsDataExpert {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsDataExpert.class);

	@Autowired
	private GoogleTokenExpert googleTokenExpert;

	@Autowired
    private ConfigurationExpert configurationExpert;
	
	/**
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @return
	 * @throws Exception
	 */
	private Analytics initializeAnalytics(
			 ConfigurationDTO configurationDTO
			,String authorizationCode
			,String redirect_url
			,PortalInstanceIdentifier pII
	) throws Exception {		
		Analytics response = null;
		
		HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
		JsonFactory JSON_FACTORY = new JacksonFactory();

		Credential credential = googleTokenExpert.getToken(
			 configurationDTO.getClient_id()
			,configurationDTO.getClient_secret()
			,configurationDTO.getRefreshtoken()
			,authorizationCode
			,redirect_url
		);
		
		if (credential != null) {
	    	//Store Credentials
	    	String retrievedAccessToken = credential.getAccessToken();
	    	if (retrievedAccessToken != null && !retrievedAccessToken.isEmpty()) {
	    		configurationExpert.updateConfiguration(pII, GoogleAnalyticsConfigurationEnum.TOKEN.getKey(), retrievedAccessToken);
	    	}	    	
	    	String retrievedRefreshoken = credential.getRefreshToken();
	    	if (retrievedRefreshoken != null && !retrievedRefreshoken.isEmpty()) {
	    		configurationExpert.updateConfiguration(pII, GoogleAnalyticsConfigurationEnum.REFRESHTOKEN.getKey(), retrievedRefreshoken);
	    	}
	    	
	    	//Build Analytics Object
			response = Analytics.builder(HTTP_TRANSPORT, JSON_FACTORY)
				.setApplicationName("Liferay-Google-Analytics")
				.setHttpRequestInitializer(credential).build();
		}
		return response;
	}
	
	/**
	 * Verify if there are access to google analytics api
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @return
	 */
	public boolean isValidAccess(
			 ConfigurationDTO configurationDTO
			,String authorizationCode
			,String redirect_url
			,PortalInstanceIdentifier pII
	){
		boolean result = false;
		try {
			if (initializeAnalytics(configurationDTO, authorizationCode, redirect_url, pII) != null){
				result = true;
			}
		} catch (Exception e) {}
		return result;
	}
	
	/**
	 * Retrieve the Google Analytics Accounts DTO (Accounts, WebProperties and Profiles)
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @param locale
	 * @return
	 */
	public GoogleAnalyticsAccountsDTO getGoogleAnalyticsAccounts(
			 ConfigurationDTO configurationDTO
			,String authorizationCode
			,String redirect_url
			,Locale locale
			,PortalInstanceIdentifier pII
	) {
		GoogleAnalyticsAccountsDTO googleAnalyticsAccountDTO = new GoogleAnalyticsAccountsDTO();		
		try {			
			Analytics analytics = initializeAnalytics(configurationDTO, authorizationCode, redirect_url, pII);
			
			// Query accounts collection.
			Accounts accounts = analytics.management().accounts().list().execute();
			if (accounts.getItems().isEmpty()) {
				googleAnalyticsAccountDTO.setSuccess(false);
				googleAnalyticsAccountDTO.appendMessage(ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.no.accounts.found", locale));
			} else {
				
				for (Account account : accounts.getItems()) {
					GoogleAnalyticsParamAccountDTO googleAnalyticsParamAccountDTO = new GoogleAnalyticsParamAccountDTO();
					googleAnalyticsParamAccountDTO.setValue(account.getId());
					googleAnalyticsParamAccountDTO.setHtml(account.getName());
					if (account.getId().equals(configurationDTO.getAccount_id())){
						googleAnalyticsParamAccountDTO.setSelected(true);
					}
					
					// Query webproperties collection.
					Webproperties webproperties = analytics.management().webproperties().list(account.getId()).execute();
					if (webproperties.getItems().isEmpty()) {
						googleAnalyticsAccountDTO.setSuccess(false);
						googleAnalyticsAccountDTO.appendMessage(ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.no.webproperties.found", locale));
					} else {
						
						for (Webproperty webproperty : webproperties.getItems()) {
							GoogleAnalyticsParamPropertiesDTO googleAnalyticsParamPropertiesDTO = new GoogleAnalyticsParamPropertiesDTO();
							googleAnalyticsParamPropertiesDTO.setValue(webproperty.getId());
							googleAnalyticsParamPropertiesDTO.setHtml(webproperty.getName());
							if (webproperty.getId().equals(configurationDTO.getProperty_id())){
								googleAnalyticsParamPropertiesDTO.setSelected(true);
							}
							
							// Query profiles collection.
							Profiles profiles = analytics.management().profiles().list(account.getId(), webproperty.getId()).execute();
							if (profiles.getItems().isEmpty()) {
								googleAnalyticsAccountDTO.setSuccess(false);
								googleAnalyticsAccountDTO.appendMessage(ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.querying.profiles", locale));
							} else {
								for (Profile profile : profiles.getItems()) {
									GoogleAnalyticsParamProfilesDTO googleAnalyticsParamProfilesDTO = new GoogleAnalyticsParamProfilesDTO();
									googleAnalyticsParamProfilesDTO.setValue(profile.getId());
									googleAnalyticsParamProfilesDTO.setHtml(profile.getName());
									if (profile.getId().equals(configurationDTO.getProfile_id())){
										googleAnalyticsParamProfilesDTO.setSelected(true);
									}
									googleAnalyticsParamPropertiesDTO.addProfiles(googleAnalyticsParamProfilesDTO);
								}					
								googleAnalyticsParamAccountDTO.addWebProperties(googleAnalyticsParamPropertiesDTO);
								googleAnalyticsAccountDTO.setSuccess(true);
							}
							
						}				
						googleAnalyticsAccountDTO.addAccounts(googleAnalyticsParamAccountDTO);
					}
				}
			}
			
		} catch (GoogleJsonResponseException e) {
		    log.error("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());		  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return googleAnalyticsAccountDTO;
	}

}
