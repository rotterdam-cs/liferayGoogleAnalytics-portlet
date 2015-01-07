package com.rcs.expert;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NoHttpResponseException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.Account;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;
import com.google.api.services.analytics.model.Profile;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import com.google.api.services.analytics.model.Webproperty;
import com.google.gson.Gson;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ResourceBundleHelper;
import com.rcs.dto.ConfigurationDTO;
import com.rcs.dto.GoogleAnalyticsAccountsDTO;
import com.rcs.dto.GoogleAnalyticsParamAccountDTO;
import com.rcs.dto.GoogleAnalyticsParamProfilesDTO;
import com.rcs.dto.GoogleAnalyticsParamPropertiesDTO;
import com.rcs.dto.LiferayGoogleAnalyticsDTO;
import com.rcs.dto.LiferayGoogleAnalyticsValueDTO;
import com.rcs.dto.LiferayGoogleAnalyticsValuesDTO;
import com.rcs.enums.GoogleAnalyticsConfigurationEnum;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 * To manually delete a token
 * https://accounts.google.com/b/0/IssuedAuthSubTokens?authsub_revoke_token=AAWk9lQfACCIvd3Mrdzk48Xn7VcXsmSY500A5iYr6CJxUdzf4JFt3ztIPmcDzQAKFTngS2cXkN0e0HaHTfMh2vx4d_vohR9tEd4pfH-KUpKwKNCsJ8IX9BzD9UoNbNbj_d9-IdBxI1_rVSWoZ1c1C-yDEyyWOItjVIEmrdNuECc9cZXRqAkp74v5jXMaF51tmnd0x00WIX7Y&timeStmp=1340630600&secTok=.AG5fkS85uYrsq8oqpeq526zA2FNvU_wkPg%3D%3D
 */
@Component
@Scope("session")
public class GoogleAnalyticsDataExpert implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsDataExpert.class);
	
	private static Credential sessionCredential;
	private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private JsonFactory JSON_FACTORY = new JacksonFactory();
		
	private GoogleTokenExpert googleTokenExpert = new GoogleTokenExpert();
	
    private ConfigurationExpert configurationExpert = new ConfigurationExpert();
	
	
	/**
	 * @param analytics
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	private GaData getResults(Analytics analytics, String profileId, Date startDate, Date endDate) throws SocketTimeoutException, IOException {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = sdf.format(startDate);
		String endDateStr = sdf.format(endDate);
	    return analytics.data().ga()
	        .get("ga:" + profileId
	            ,startDateStr
	            ,endDateStr
	            ,"ga:visits,ga:visitors,ga:newVisits,ga:pageviews,ga:pageviewsPerVisit,ga:avgTimeOnSite,ga:visitBounceRate,ga:percentNewVisits") // Metrics.
	        .setDimensions("ga:date")
	        .setSort("ga:date")
	        .setMaxResults(100)
	        .execute();
	}
	
	/**
	 * @param analytics
	 * @param profileId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IOException
	 */
	private GaData getTrafficSourcesResults(Analytics analytics, String profileId, Date startDate, Date endDate) throws IOException {
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = sdf.format(startDate);
		String endDateStr = sdf.format(endDate);
	    return analytics.data().ga()
	        .get("ga:" + profileId
	            ,startDateStr
	            ,endDateStr
	            ,"ga:visits") // Metrics.
	        .setDimensions("ga:medium")
	        .setSort("ga:medium")
	        .setMaxResults(100)
	        .execute();
	}	
	
	/**
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @param locale
	 * @param pII
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public LiferayGoogleAnalyticsDTO getGoogleAnalyticsData(
			 ConfigurationDTO configurationDTO
			,String authorizationCode
			,String redirect_url
			,Locale locale
			,PortalInstanceIdentifier pII
			,Date startDate
			,Date endDate
			,Date startDatePrev
			,Date endDatePrev
	) throws SocketTimeoutException {		
		LiferayGoogleAnalyticsDTO liferayGoogleAnalyticsDTO = new LiferayGoogleAnalyticsDTO();
		try {		
			if (startDatePrev == null || endDatePrev == null){
				int days = diffBetweenDates(startDate.getTime(), endDate.getTime());
				Calendar gc = GregorianCalendar.getInstance();
				gc.setTime(startDate);         
				gc.add(Calendar.DAY_OF_MONTH, -days);        
				startDatePrev = gc.getTime();
				endDatePrev=startDate;
			}
						
			Analytics analytics = initializeAnalytics(configurationDTO, pII);
			GaData gaData = getResults(analytics, configurationDTO.getProfile_id(),startDate,endDate);		
			GaData gaDataPrev = getResults(analytics, configurationDTO.getProfile_id(), startDatePrev, endDatePrev);
			//printDataTable(gaData);
			
			if (gaData.getTotalResults() > 0) {
				Map<String, String> totals = gaData.getTotalsForAllResults();
				Map<String, String> totalsPrev = gaDataPrev.getTotalsForAllResults();
				
				int i = 0;
				for (ColumnHeaders header : gaData.getColumnHeaders()) {
					liferayGoogleAnalyticsDTO.setSuccess(true);
					
					//Current Data
					LiferayGoogleAnalyticsValueDTO curData = new LiferayGoogleAnalyticsValueDTO();
					for (List<String>  value : gaData.getRows()) {
						curData.addData(fixDoubles(value.get(i)));
					}
					curData.setSparkline(simpleEncode(curData.getData()));
					curData.setValue(totals.get(header.getName()));
					
					//Previous Data
					LiferayGoogleAnalyticsValueDTO prevData = new LiferayGoogleAnalyticsValueDTO();
					for (List<String>  value : gaDataPrev.getRows()) {
						prevData.addData(fixDoubles(value.get(i)));
					}
					prevData.setSparkline(simpleEncode(prevData.getData()));
					prevData.setValue(totalsPrev.get(header.getName()));					
					
					if (curData.getValue() != null && prevData.getValue() != null){
						double curvalue = Double.parseDouble(curData.getValue().replace(",", ""));
						double prevvalue = Double.parseDouble(prevData.getValue().replace(",", ""));						
						double delta = ((curvalue - prevvalue) * 100) / prevvalue;
						prevData.setDelta(((delta > 0)?"+":"") + roundTwoDecimals(delta) + " %");						
						prevData.setValue(fixValue(prevData.getValue()));						
						curData.setValue(fixValue(curData.getValue()));						
					}
					
					if (header.getName().equalsIgnoreCase("ga:date")) {
						for (List<String> value : gaData.getRows()) {
							liferayGoogleAnalyticsDTO.addDates(getDateFromGA(value.get(i)));							
						}
						for (List<String> value : gaDataPrev.getRows()) {
							liferayGoogleAnalyticsDTO.addPastDates(getDateFromGA(value.get(i)));
						}
					} else if (header.getName().equalsIgnoreCase("ga:visits")) {
						LiferayGoogleAnalyticsValuesDTO visits = new LiferayGoogleAnalyticsValuesDTO();
						visits.setTitle(fixTitle(header.getName()));
						visits.setCur(curData);	
						visits.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setVisits(visits);
					} else if(header.getName().equalsIgnoreCase("ga:visitors")) {
						LiferayGoogleAnalyticsValuesDTO visitors = new LiferayGoogleAnalyticsValuesDTO();
						visitors.setTitle(fixTitle(header.getName()));
						visitors.setCur(curData);
						visitors.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setVisitors(visitors);
					} else if(header.getName().equalsIgnoreCase("ga:pageviews")) {
						LiferayGoogleAnalyticsValuesDTO pageviews = new LiferayGoogleAnalyticsValuesDTO();
						pageviews.setTitle(fixTitle(header.getName()));
						pageviews.setCur(curData);	
						pageviews.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setPageviews(pageviews);
					} else if(header.getName().equalsIgnoreCase("ga:pageviewsPerVisit")) {
						LiferayGoogleAnalyticsValuesDTO pageviewsPerVisit = new LiferayGoogleAnalyticsValuesDTO();
						pageviewsPerVisit.setTitle(fixTitle(header.getName()));
						pageviewsPerVisit.setCur(curData);
						pageviewsPerVisit.setPrev(prevData);			
						liferayGoogleAnalyticsDTO.setPageviewsPerVisit(pageviewsPerVisit);
					} else if(header.getName().equalsIgnoreCase("ga:visitBounceRate")) {
						LiferayGoogleAnalyticsValuesDTO visitBounceRate = new LiferayGoogleAnalyticsValuesDTO();
						visitBounceRate.setTitle(fixTitle(header.getName()));
						visitBounceRate.setCur(curData);
						visitBounceRate.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setVisitBounceRate(visitBounceRate);
					} else if(header.getName().equalsIgnoreCase("ga:avgTimeOnSite")) {
						LiferayGoogleAnalyticsValuesDTO avgTimeOnSite = new LiferayGoogleAnalyticsValuesDTO();
						avgTimeOnSite.setTitle(fixTitle(header.getName()));
						avgTimeOnSite.setCur(curData);
						avgTimeOnSite.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setAvgTimeOnSite(avgTimeOnSite);
					} else if(header.getName().equalsIgnoreCase("ga:percentNewVisits")) {
						LiferayGoogleAnalyticsValuesDTO percentNewVisits = new LiferayGoogleAnalyticsValuesDTO();
						percentNewVisits.setTitle(fixTitle(header.getName()));
						percentNewVisits.setCur(curData);
						percentNewVisits.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setPercentNewVisits(percentNewVisits);
					}			
					i++;					
				}
				
				//Adding Traffic Sources Data
				GaData gaDataTS = getTrafficSourcesResults(analytics, configurationDTO.getProfile_id(),startDate,endDate);
				//printDataTable(gaDataTS);
				if (gaDataTS.getTotalResults() > 0) {
					Map<String, String> totalsTS = gaDataTS.getTotalsForAllResults();					
					int totalvisitors = Integer.parseInt(totalsTS.get("ga:visits"));					
					for (List<String>  value : gaDataTS.getRows()) {
						double intVisitors = Double.parseDouble(value.get(1));
						double visitorsPer = (intVisitors * 100) / totalvisitors;
						if (value.get(0).equals("(none)")) {														
							liferayGoogleAnalyticsDTO.setDirectVisits(roundTwoDecimals(visitorsPer));
						} else if (value.get(0).equals("organic")) {							
							liferayGoogleAnalyticsDTO.setOrganicVisits(roundTwoDecimals(visitorsPer));
						} else if (value.get(0).equals("referral")) {
							liferayGoogleAnalyticsDTO.setReferralVisits(roundTwoDecimals(visitorsPer));
						}
					}
				}				
				
			} else {
				liferayGoogleAnalyticsDTO.setSuccess(false);
		    	liferayGoogleAnalyticsDTO.setMessage(ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.data.no.reults.found", locale));
		    	log.info("No results found");
			}			
		} catch (SocketTimeoutException e) {
			killSessionCredential();
			log.info("SocketTimeoutException");
			throw e;
		} catch (Exception e) {
			killSessionCredential();
			log.info(e.getMessage());
		}
		return liferayGoogleAnalyticsDTO;
	}
	
	public LiferayGoogleAnalyticsDTO getGoogleAnalyticsData(
			 ConfigurationDTO configurationDTO					
			,Locale locale
			,String accessToken			
			,Date startDate
			,Date endDate
			,Date startDatePrev
			,Date endDatePrev
	) {		
		LiferayGoogleAnalyticsDTO liferayGoogleAnalyticsDTO = new LiferayGoogleAnalyticsDTO();
		try {		
			if (startDatePrev == null || endDatePrev == null){
				int days = diffBetweenDates(startDate.getTime(), endDate.getTime());
				Calendar gc = GregorianCalendar.getInstance();
				gc.setTime(startDate);         
				gc.add(Calendar.DAY_OF_MONTH, -days);        
				startDatePrev = gc.getTime();
				endDatePrev=startDate;
			}
						
			Analytics analytics = initializeAnalytics(accessToken);
			GaData gaData = getResults(analytics, configurationDTO.getProfile_id(),startDate,endDate);		
			GaData gaDataPrev = getResults(analytics, configurationDTO.getProfile_id(), startDatePrev, endDatePrev);
			//printDataTable(gaData);
			
			if (gaData.getTotalResults() > 0) {
				Map<String, String> totals = gaData.getTotalsForAllResults();
				Map<String, String> totalsPrev = gaDataPrev.getTotalsForAllResults();
				
				int i = 0;
				for (ColumnHeaders header : gaData.getColumnHeaders()) {
					liferayGoogleAnalyticsDTO.setSuccess(true);
					
					//Current Data
					LiferayGoogleAnalyticsValueDTO curData = new LiferayGoogleAnalyticsValueDTO();
					for (List<String>  value : gaData.getRows()) {
						curData.addData(fixDoubles(value.get(i)));
					}
					curData.setSparkline(simpleEncode(curData.getData()));
					curData.setValue(totals.get(header.getName()));
					
					//Previous Data
					LiferayGoogleAnalyticsValueDTO prevData = new LiferayGoogleAnalyticsValueDTO();
					for (List<String>  value : gaDataPrev.getRows()) {
						prevData.addData(fixDoubles(value.get(i)));
					}
					prevData.setSparkline(simpleEncode(prevData.getData()));
					prevData.setValue(totalsPrev.get(header.getName()));					
					
					if (curData.getValue() != null && prevData.getValue() != null){
						double curvalue = Double.parseDouble(curData.getValue().replace(",", ""));
						double prevvalue = Double.parseDouble(prevData.getValue().replace(",", ""));						
						double delta = ((curvalue - prevvalue) * 100) / prevvalue;
						prevData.setDelta(((delta > 0)?"+":"") + roundTwoDecimals(delta) + " %");						
						prevData.setValue(fixValue(prevData.getValue()));						
						curData.setValue(fixValue(curData.getValue()));						
					}
					
					if (header.getName().equalsIgnoreCase("ga:date")) {
						for (List<String> value : gaData.getRows()) {
							liferayGoogleAnalyticsDTO.addDates(getDateFromGA(value.get(i)));							
						}
						for (List<String> value : gaDataPrev.getRows()) {
							liferayGoogleAnalyticsDTO.addPastDates(getDateFromGA(value.get(i)));
						}
					} else if (header.getName().equalsIgnoreCase("ga:visits")) {
						LiferayGoogleAnalyticsValuesDTO visits = new LiferayGoogleAnalyticsValuesDTO();
						visits.setTitle(fixTitle(header.getName()));
						visits.setCur(curData);	
						visits.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setVisits(visits);
					} else if(header.getName().equalsIgnoreCase("ga:visitors")) {
						LiferayGoogleAnalyticsValuesDTO visitors = new LiferayGoogleAnalyticsValuesDTO();
						visitors.setTitle(fixTitle(header.getName()));
						visitors.setCur(curData);
						visitors.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setVisitors(visitors);
					} else if(header.getName().equalsIgnoreCase("ga:pageviews")) {
						LiferayGoogleAnalyticsValuesDTO pageviews = new LiferayGoogleAnalyticsValuesDTO();
						pageviews.setTitle(fixTitle(header.getName()));
						pageviews.setCur(curData);	
						pageviews.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setPageviews(pageviews);
					} else if(header.getName().equalsIgnoreCase("ga:pageviewsPerVisit")) {
						LiferayGoogleAnalyticsValuesDTO pageviewsPerVisit = new LiferayGoogleAnalyticsValuesDTO();
						pageviewsPerVisit.setTitle(fixTitle(header.getName()));
						pageviewsPerVisit.setCur(curData);
						pageviewsPerVisit.setPrev(prevData);			
						liferayGoogleAnalyticsDTO.setPageviewsPerVisit(pageviewsPerVisit);
					} else if(header.getName().equalsIgnoreCase("ga:visitBounceRate")) {
						LiferayGoogleAnalyticsValuesDTO visitBounceRate = new LiferayGoogleAnalyticsValuesDTO();
						visitBounceRate.setTitle(fixTitle(header.getName()));
						visitBounceRate.setCur(curData);
						visitBounceRate.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setVisitBounceRate(visitBounceRate);
					} else if(header.getName().equalsIgnoreCase("ga:avgTimeOnSite")) {
						LiferayGoogleAnalyticsValuesDTO avgTimeOnSite = new LiferayGoogleAnalyticsValuesDTO();
						avgTimeOnSite.setTitle(fixTitle(header.getName()));
						avgTimeOnSite.setCur(curData);
						avgTimeOnSite.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setAvgTimeOnSite(avgTimeOnSite);
					} else if(header.getName().equalsIgnoreCase("ga:percentNewVisits")) {
						LiferayGoogleAnalyticsValuesDTO percentNewVisits = new LiferayGoogleAnalyticsValuesDTO();
						percentNewVisits.setTitle(fixTitle(header.getName()));
						percentNewVisits.setCur(curData);
						percentNewVisits.setPrev(prevData);
						liferayGoogleAnalyticsDTO.setPercentNewVisits(percentNewVisits);
					}			
					i++;					
				}
				
				//Adding Traffic Sources Data
				GaData gaDataTS = getTrafficSourcesResults(analytics, configurationDTO.getProfile_id(),startDate,endDate);
				//printDataTable(gaDataTS);
				if (gaDataTS.getTotalResults() > 0) {
					Map<String, String> totalsTS = gaDataTS.getTotalsForAllResults();					
					int totalvisitors = Integer.parseInt(totalsTS.get("ga:visits"));					
					for (List<String>  value : gaDataTS.getRows()) {
						double intVisitors = Double.parseDouble(value.get(1));
						double visitorsPer = (intVisitors * 100) / totalvisitors;
						if (value.get(0).equals("(none)")) {														
							liferayGoogleAnalyticsDTO.setDirectVisits(roundTwoDecimals(visitorsPer));
						} else if (value.get(0).equals("organic")) {							
							liferayGoogleAnalyticsDTO.setOrganicVisits(roundTwoDecimals(visitorsPer));
						} else if (value.get(0).equals("referral")) {
							liferayGoogleAnalyticsDTO.setReferralVisits(roundTwoDecimals(visitorsPer));
						}
					}
				}				
				
			} else {
				liferayGoogleAnalyticsDTO.setSuccess(false);
		    	liferayGoogleAnalyticsDTO.setMessage(ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.data.no.reults.found", locale));
		    	log.info("No results found");
			}			
		} catch (Exception e) {
			killSessionCredential();
			log.info(e.getMessage());
		}
		return liferayGoogleAnalyticsDTO;
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
			Analytics analytics = initializeAnalytics(configurationDTO, pII);
			
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
			killSessionCredential();
			googleAnalyticsAccountDTO.setSuccess(false);
			googleAnalyticsAccountDTO.appendMessage(e.getDetails().getMessage());
		    log.info("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());		  
		} catch (NoHttpResponseException e) {
			killSessionCredential();
			log.error(e.getMessage());
		} catch (Exception e) {
			killSessionCredential();
			log.error(e.getMessage());
		}
		
		return googleAnalyticsAccountDTO;
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
			,String redirect_url
			,Locale locale
			,PortalInstanceIdentifier pII
	) throws TokenResponseException, Exception {
		GoogleAnalyticsAccountsDTO googleAnalyticsAccountDTO = new GoogleAnalyticsAccountsDTO();		
		try {			
			Analytics analytics = initializeAnalytics(configurationDTO, pII);
			
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
			
		} catch (TokenResponseException e) {
			throw e;
		} catch (GoogleJsonResponseException e) {
			killSessionCredential();
			googleAnalyticsAccountDTO.setSuccess(false);
			googleAnalyticsAccountDTO.appendMessage(e.getDetails().getMessage());
		    log.info("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());		  
		} catch (NoHttpResponseException e) {
			killSessionCredential();
			log.error(e.getMessage());
		} catch (Exception e) {
			killSessionCredential();
			log.error(e.getMessage());
			throw e;
		}
		
		return googleAnalyticsAccountDTO;
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
			,Locale locale
			,PortalInstanceIdentifier pII
	) throws TokenResponseException, NoHttpResponseException, Exception {
		GoogleAnalyticsAccountsDTO googleAnalyticsAccountDTO = new GoogleAnalyticsAccountsDTO();		
		try {				
			Analytics analytics = initializeAnalytics(configurationDTO, pII);
			
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
			
		} catch (TokenResponseException e) {
			throw e;
		} catch (GoogleJsonResponseException e) {
			killSessionCredential();
			googleAnalyticsAccountDTO.setSuccess(false);						
		    if("User does not have any Google Analytics account.".equals(e.getDetails().getMessage())) {
		    	String message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.no.account", locale);
		    	googleAnalyticsAccountDTO.appendMessage(message);
		    	throw new Exception(message);
		    } else {
		    	log.info("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		    }
		    googleAnalyticsAccountDTO.setMessage(e.getDetails().getMessage());
		    throw e;
		} catch (NoHttpResponseException e) {
			killSessionCredential();
			log.debug(e.getMessage());
			throw e;
		} catch (Exception e) {
			killSessionCredential();
			log.debug(e.getMessage());
			throw e;
		}
		
		return googleAnalyticsAccountDTO;
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
	) throws TokenResponseException {
		boolean result = false;
		try {
			if (initializeAnalytics(configurationDTO, pII) != null){
				result = true;
			}
		} catch (TokenResponseException e) {			
			throw e;
		} catch (Exception ignored) {}
		return result;
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
			,PortalInstanceIdentifier pII
	) throws TokenResponseException, Exception {
		boolean result = false;
		try {
			if (initializeAnalytics(configurationDTO, pII) != null){
				result = true;
			}
		} catch (TokenResponseException e) {
			if(e.getDetails()!=null && e.getDetails().getError().equals("unauthorized_client")) {				
	    		return false;
	    	} else {
	    		throw e;
	    	} 			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	public HashMap<String, Object> getGoogleAnalyticsAccountData(ConfigurationDTO configurationDTO, PortalInstanceIdentifier pII, Locale locale, HashMap<String, Object> modelAttrs) {		
		//Authorize Google API			   
	    boolean isValidAccess = false;
	    String message = null;
	    try {
	    	isValidAccess = isValidAccess(configurationDTO, pII);
	    } catch(TokenResponseException e) {			
	    	
	    	if(e.getDetails() != null) {
	    		if(e.getDetails().getError().equals("invalid_client")) {
	    			message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.token.response", locale);	    			
	    		} else if(e.getDetails().getError().equals("invalid_grant")) { 
	    			// If you contact Google for an OAuth2 token too quickly, 
	    			// before the previous token expires,
	    			// they will serve you that message.
	    			//You have requested access too soon, you have to wait a couple of minutes to retry.
	    			//com.rcs.googleanalytics.error.token.response.invalid.grant
	    			message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.token.response.invalid.grant", locale);
	    		} else {
	    			//general error
	    			message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.general.error.processing.data", locale);	    			
	    		}
	    	}	    	
	    	modelAttrs.put("errorMessage", message);	    
	    } catch(Exception e) {
	    	message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.general.error.processing.data", locale);
	    	modelAttrs.put("errorMessage", message);
	    }
	    //If there are not access to google api, request authorization	  
	    if (!isValidAccess) {
	    	if (configurationDTO.getClient_id() != null && !configurationDTO.getClient_id().isEmpty()){	    		
		    	String authURL = googleTokenExpert.getAuthURL(configurationDTO.getClient_id(), configurationDTO.getRedirect_url());		    	
		    	modelAttrs.put("authURL", authURL);
	    	}
		//If there are access retrieve the Accounts DTO
	    } else {	    	
	    	GoogleAnalyticsAccountsDTO googleAnalyticsAccountsDTO = new GoogleAnalyticsAccountsDTO();
	    	try {
	    		googleAnalyticsAccountsDTO = getGoogleAnalyticsAccounts(configurationDTO, locale, pII);
		    } catch(TokenResponseException e) {					    	
		    	if(e.getDetails() != null) {
		    		if(e.getDetails().getError().equals("invalid_client")) {
		    			message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.token.response", locale);	    			
		    		} else if(e.getDetails().getError().equals("invalid_grant")) { 
		    			// If you contact Google for an OAuth2 token too quickly, 
		    			// before the previous token expires,
		    			// they will serve you that message.
		    			//You have requested access too soon, you have to wait a couple of minutes to retry.
		    			//com.rcs.googleanalytics.error.token.response.invalid.grant
		    			message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.token.response.invalid.grant", locale);
		    		} else {
		    			//general error
		    			message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.general.error.processing.data", locale);	    			
		    		}
		    	}		    	
		    	modelAttrs.put("errorMessage", message);		    	
		    	googleAnalyticsAccountsDTO.setMessage(message);
		    	return modelAttrs;
		    } catch(Exception e) {		    	
		    	message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.no.account", locale);		    	
		    	modelAttrs.put("errorMessage", message);
		    	googleAnalyticsAccountsDTO.setMessage(message);
		    }	    		    
			if (googleAnalyticsAccountsDTO.isSuccess()){
				modelAttrs.put("googleAnalyticsAccounts", googleAnalyticsAccountsDTO);
				Gson gson = new Gson();
		        String googleAnalyticsAccountsJSON = gson.toJson(googleAnalyticsAccountsDTO);
		        modelAttrs.put("googleAnalyticsAccountsJSON", googleAnalyticsAccountsJSON);
			} else {
				modelAttrs.put("errorMessage", googleAnalyticsAccountsDTO.getMessage());
			}			 
		}
	    return modelAttrs;		
	}
	
	/**
	 * Kill session credential
	 */
	public void killSessionCredential() {
		sessionCredential=null;
	}
	
	/**
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @return
	 * @throws Exception
	 */
	private Analytics initializeAnalytics(
			 ConfigurationDTO configurationDTO					
			,PortalInstanceIdentifier pII
	) throws TokenResponseException, Exception {		
		Analytics response = null;
		Credential credential = null;
		
		try {
			if (sessionCredential == null) {
				log.debug("first getCredential");
				credential = getCredential(configurationDTO, pII);			
			} else {
				log.debug("sesson credential");
				credential = sessionCredential;
			}
						
			if (credential != null) {			
				response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);			
				if (response == null) {
					log.info("second getCredential");
					credential = getCredential(configurationDTO, pII);
					response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);					
				}
			}
		} catch(Exception e) {
			log.debug(e.getMessage());
			throw e;
		}
		return response;
	}
	
	/**
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @return
	 * @throws Exception
	 *
	private Analytics initializeAnalytics(
			 ConfigurationDTO configurationDTO			
			,String redirect_url
			,PortalInstanceIdentifier pII
	) throws TokenResponseException, Exception {		
		Analytics response = null;
		Credential credential = null;
		
		if (sessionCredential == null) {
			log.info("first getCredential");
			credential = getCredential(configurationDTO, pII);			
		} else {
			log.info("sesson credential");
			credential = sessionCredential;
		}
		
		if (credential != null) {			
			response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);			
			if (response == null) {
				log.info("second getCredential");
				credential = getCredential(configurationDTO, pII);
				response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);
				log.info("RESPONSE NULL");
			}
		}
		return response;
	}
	*/
	
	private Analytics initializeAnalytics(String accessToken) throws Exception {		
		Analytics response = null;
		Credential credential = null;
		
		if (sessionCredential == null) {
			log.debug("first getCredential");
			credential = getCredential(accessToken);			
		} else {
			log.debug("sesson credential");
			credential = sessionCredential;
		}
		
		if (credential != null) {			
			response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);
		}
		return response;
	}
	
	public Credential getCredential(String accessToken) {
    	GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        return credential;
    }
	/**
	 * Build Analytics Object
	 * @param credential
	 * @param HTTP_TRANSPORT
	 * @param JSON_FACTORY
	 * @return
	 */
	private Analytics getAnalytics(Credential credential, HttpTransport HTTP_TRANSPORT, JsonFactory JSON_FACTORY) throws Exception {

		Analytics analytics = null;
		try{
			if (credential != null) {
				
			    // Set up and return Google Analytics API client.
				analytics = new Analytics.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
			    .setApplicationName("Liferay-Google-Analytics").build();
		
			}
		} catch (Exception e) {			
			log.debug(e.getMessage());
			throw e;
		}				
		return analytics;
	}
	
	/**
	 * Get Credential and update session and stored credential
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @param pII
	 * @return
	 * @throws Exception
	 *
	private Credential getCredential(
			 ConfigurationDTO configurationDTO
			,String authorizationCode
			,String redirect_url
			,PortalInstanceIdentifier pII
	) throws TokenResponseException {
		Credential credential = null;
		try {
			credential = googleTokenExpert.getToken(
				 configurationDTO.getClient_id()
				,configurationDTO.getClient_secret()
				,configurationDTO.getRefreshtoken()
				,authorizationCode
				,redirect_url
			);
		} catch (TokenResponseException e) {			
			throw e;
		} catch (Exception e) {
			log.info("Not Valid credential generated");
		}
		
		//Update session credential
		sessionCredential = credential;
		
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
		}
		
		return credential;
	}
	*/
	
	/**
	 * Get Credential and update session and stored credential
	 * @param configurationDTO
	 * @param authorizationCode
	 * @param redirect_url
	 * @param pII
	 * @return
	 * @throws Exception
	 */
	private Credential getCredential(
			 ConfigurationDTO configurationDTO						
			,PortalInstanceIdentifier pII
	) throws TokenResponseException, Exception {
		Credential credential = null;
		try {
			credential = googleTokenExpert.getToken(
				 configurationDTO.getClient_id()
				,configurationDTO.getClient_secret()
				,configurationDTO.getRefreshtoken()
				,configurationDTO.getCode()
				,configurationDTO.getRedirect_url()
			);
		} catch (TokenResponseException e) {			
			throw e;
		} catch (Exception e) {
			log.info("Not Valid credential generated");
			throw e;
		}
		
		//Update session credential
		sessionCredential = credential;
		
		if (credential != null) {
	    	//Store Credentials
	    	String retrievedAccessToken = credential.getAccessToken();
	    	if (retrievedAccessToken != null && !retrievedAccessToken.isEmpty()) {
	    		configurationExpert.updateConfiguration(pII, GoogleAnalyticsConfigurationEnum.TOKEN.getKey(), retrievedAccessToken);
	    	}	    	
	    	String retrievedRefreshoken = credential.getRefreshToken();
	    	if (retrievedRefreshoken != null && !retrievedRefreshoken.isEmpty()) {
	    		configurationExpert.updateConfiguration(pII, GoogleAnalyticsConfigurationEnum.REFRESHTOKEN.getKey() + "@" + configurationDTO.getClient_id(), retrievedRefreshoken);
	    	}
		}
		
		return credential;
	}
	
	/**
	 * @param value
	 * @return
	 */
	private String fixValue(String value) {
		String result = value;
		if (isInteger(value) && Integer.parseInt(value) > 1000){
			DecimalFormat twoDForm = new DecimalFormat("###,###");
			result = twoDForm.format(Integer.parseInt(value));
		} else if (!isInteger(value)){
			result = roundTwoDecimals(Double.parseDouble(value.replace(",", "")));
		}
		return result;
	}
	
	/**
	 * @param value
	 * @return
	 */
	private String fixDoubles(String value) {
		String result = value;
		 if (!isInteger(value)){
			result = roundTwoDecimals(Double.parseDouble(value.replace(",", "")));
		}
		return result;
	}
	
	/**
	 * @param d
	 * @return
	 */
	private String roundTwoDecimals(double d) {
		DecimalFormat twoDForm;		
		if (d>1000) {
			twoDForm = new DecimalFormat("###,###.##");
		} else {
			twoDForm = new DecimalFormat("###.##");
		}
		return twoDForm.format(d);
	}
	
	/**
	 * @param in
	 * @return
	 */
	private boolean isInteger(String in) {	        
        try {
            Integer.parseInt(in);        
        } catch (NumberFormatException ex) {
            return false;
        }	        
        return true;
    }
	
	/**
	 * @param dates
	 * @return
	 */
	private String getDateFromGA(String dates) {
		SimpleDateFormat newdate =  new SimpleDateFormat("yyyyMMdd");		
		String result;
		try {
			Date datef = newdate.parse(dates);
			result = String.valueOf(datef.getTime());
			return result;
		} catch (ParseException e) {
			log.error(e.getMessage());
			return null;
		}		
	}
	
	/**
	 * @param startDatel
	 * @param endDatel
	 * @param typeDiff
	 * @return
	 */
	public int diffBetweenDates(long startDatel, long endDatel) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(startDatel);

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(endDatel);

        int difference = 0;
        Calendar date = (Calendar) startDate.clone();

        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            difference++;
        }	        
        return difference;
	}
	
	/**
	 * @param headName
	 * @return
	 */
	private String fixTitle(String headName){
		String title = headName.replace("ga:", "");
		final StringBuilder result = new StringBuilder(title.length());
		String[] words = title.split("(?=[A-Z])");
		for(int i=0,l=words.length;i<l;++i) {
		  if(i>0) result.append(" ");      
		  result.append(Character.toUpperCase(words[i].charAt(0)))
		        .append(words[i].substring(1));
		}
		return result.toString();
	}
	
	/**
	 * Used to generate thumnbail image of the graphics
	 * @param valueArray
	 * @return
	 */
	private String simpleEncode(List<String>valueArray) {
		String simpleEncoding = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int maxValue = getMaxValue(valueArray);	
		List<String> chartData = new ArrayList<String>();		  
		for (String currentValuec : valueArray) {			
			try {
				int currentValue = (int) Math.round(Double.parseDouble(currentValuec.replace(",", "")));
				if (currentValue >= 0) {
					chartData.add(simpleEncoding.charAt(Math.round((simpleEncoding.length()-1) * currentValue / maxValue))+"");
			    } else {
			    	chartData.add("_");
			    }
	        } catch (NumberFormatException ex) {
	        	chartData.add("_");
	        }			
		}
		return combine(chartData, "");		  
	}
	
	/**
	 * @param s
	 * @param glue
	 * @return
	 */
	private String combine(List<String> s, String glue)	{
		  int k=s.size();
		  if (k==0)
		    return null;
		  StringBuilder out=new StringBuilder();
		  out.append(s.get(0));
		  for (int x=1;x<k;++x)
		    out.append(glue).append(s.get(x));
		  return out.toString();
		}
	
	/**
	 * @param array
	 * @return
	 */
	private int getMaxValue(List<String> array) {		
		int maxValue = 10;
		try {
			if (array.size() > 0) {
				for (String string : array) {
					int compValue = (int) Math.ceil(Double.parseDouble(string.replace(",", "")));
					if (compValue > maxValue) {
						maxValue = compValue;
					}
				}
			}        
        } catch (NumberFormatException ex) {  
        	log.error(ex.getMessage());
        }		
		return maxValue;
	}
	
	
	/**
	 * Usefull Method for testing purposes
	 * @param gaData
	 */
	public static void printDataTable(GaData gaData) {
	    if (gaData.getTotalResults() > 0) {
	      System.out.println("Data Table:");
	      Map<String, String> totals = gaData.getTotalsForAllResults();
	      // Print the column names.
	      for (ColumnHeaders header : gaData.getColumnHeaders()) {
	        System.out.format("%-32s", header.getName());
	      }
	      System.out.println();
	      for (ColumnHeaders header : gaData.getColumnHeaders()) {
	        System.out.format("%-32s", totals.get(header.getName()));
	      }
	      
	      System.out.println();

	      // Print the rows of data.
	      for (List<String> rowValues : gaData.getRows()) {
	        for (String value : rowValues) {
	          System.out.format("%-32s", value);
	        }
	        System.out.println();
	      }
	      System.out.println();
	    } else {
	      System.out.println("No data");
	    }
	  }
	
}
