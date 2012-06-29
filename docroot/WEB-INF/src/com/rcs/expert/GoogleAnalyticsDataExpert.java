package com.rcs.expert;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NoHttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;
import com.google.api.services.analytics.model.Profile;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import com.google.api.services.analytics.model.Webproperty;
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
public class GoogleAnalyticsDataExpert {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsDataExpert.class);
	
	private Credential sessionCredential;
	private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private JsonFactory JSON_FACTORY = new JacksonFactory();
	
	@Autowired
	private GoogleTokenExpert googleTokenExpert;

	@Autowired
    private ConfigurationExpert configurationExpert;
	
	
	/**
	 * @param analytics
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	private GaData getResults(Analytics analytics, String profileId, Date startDate, Date endDate) throws IOException {
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
						
			Analytics analytics = initializeAnalytics(configurationDTO, authorizationCode, redirect_url, pII);
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
		    	log.error("No results found");
			}			
		} catch (Exception e) {
			killSessionCredential();
			e.printStackTrace();
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
			killSessionCredential();
			googleAnalyticsAccountDTO.setSuccess(false);
			googleAnalyticsAccountDTO.appendMessage(e.getDetails().getMessage());
		    log.error("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());		  
		} catch (NoHttpResponseException e) {
			killSessionCredential();
			e.printStackTrace();
		} catch (Exception e) {
			killSessionCredential();
			e.printStackTrace();
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
			,String authorizationCode
			,String redirect_url
			,PortalInstanceIdentifier pII
	) throws Exception {		
		Analytics response = null;
		Credential credential = null;
		
		if (sessionCredential == null) {
			log.error("first getCredential");
			credential = getCredential(configurationDTO, authorizationCode, redirect_url, pII);			
		} else {
			log.error("sesson credential");
			credential = sessionCredential;
		}
		
		if (credential != null) {			
			response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);			
			if (response == null) {
				log.error("second getCredential");
				credential = getCredential(configurationDTO, authorizationCode, redirect_url, pII);
				response = getAnalytics(credential, HTTP_TRANSPORT, JSON_FACTORY);
				log.error("RESPONSE NULL");
			}
		}
		return response;
	}
	
	/**
	 * Build Analytics Object
	 * @param credential
	 * @param HTTP_TRANSPORT
	 * @param JSON_FACTORY
	 * @return
	 */
	private Analytics getAnalytics(Credential credential, HttpTransport HTTP_TRANSPORT, JsonFactory JSON_FACTORY) {
		Analytics analytics = null;
		try{
			if (credential != null) {
				analytics = Analytics.builder(HTTP_TRANSPORT, JSON_FACTORY)
				.setApplicationName("Liferay-Google-Analytics")
				.setHttpRequestInitializer(credential).build();
			}
		} catch (Exception e) {
			log.error(e);
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
	 */
	private Credential getCredential(
			 ConfigurationDTO configurationDTO
			,String authorizationCode
			,String redirect_url
			,PortalInstanceIdentifier pII
	) {
		Credential credential = null;
		try {
			credential = googleTokenExpert.getToken(
				 configurationDTO.getClient_id()
				,configurationDTO.getClient_secret()
				,configurationDTO.getRefreshtoken()
				,authorizationCode
				,redirect_url
			);
		} catch (Exception e) {
			log.error("Not Valid credential generated");
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
			log.error(e);
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
        	log.error(ex);
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
