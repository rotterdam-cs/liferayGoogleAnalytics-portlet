package com.rcs.googleanalytics.portlet;

import static com.rcs.common.Constants.ADMIN_SECTION_CONFIGURATION;
import static com.rcs.common.Constants.ADMIN_SECTION_VIEW_REPORTS;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.gson.Gson;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.PortalUtil;
import com.rcs.common.LocalResponse;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ResourceBundleHelper;
import com.rcs.common.ServiceActionResult;
import com.rcs.dto.ConfigurationDTO;
import com.rcs.dto.GoogleAnalyticsAccountsDTO;
import com.rcs.dto.LiferayGoogleAnalyticsDTO;
import com.rcs.enums.GoogleAnalyticsConfigurationEnum;
import com.rcs.enums.MessagesEnum;
import com.rcs.expert.ConfigurationExpert;
import com.rcs.expert.GoogleAnalyticsDataExpert;
import com.rcs.expert.GoogleTokenExpert;
import com.rcs.expert.UtilsExpert;
import com.rcs.configuration.model.Configuration;
import com.rcs.configuration.service.ConfigurationLocalServiceUtil;
import com.rcs.configuration.service.persistence.ConfigurationUtil;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@Scope("session")
@RequestMapping("VIEW")
public class GoogleAnalyticsController {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsController.class);
	
	private ConfigurationDTO configurationDTO = new ConfigurationDTO();
	private Locale locale;
	private PortalInstanceIdentifier pII;
	private String fullCurrentURL;	
		
    private ConfigurationExpert configurationExpert = new ConfigurationExpert();
		
    private UtilsExpert utilsExpert = new UtilsExpert();
		
    private GoogleTokenExpert googleTokenExpert = new GoogleTokenExpert();
		
	private GoogleAnalyticsDataExpert googleAnalyticsDataExpert = new GoogleAnalyticsDataExpert();
		
	/**
	 * Main view
	 * @param request
	 * @param response
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	@RenderMapping
	public ModelAndView resolveView(PortletRequest request, PortletResponse response) throws PortalException, SystemException {
		HashMap<String, Object> modelAttrs = new HashMap<String, Object>();
		locale = LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request));		
		configurationDTO = configurationExpert.getConfiguration();
		fullCurrentURL = configurationDTO.getRedirect_url();
				
		modelAttrs = googleAnalyticsDataExpert.getGoogleAnalyticsAccountData(configurationDTO, pII, locale, modelAttrs);		
		modelAttrs.put("configuration", configurationDTO);		
	    String messagesJson = MessagesEnum.getMessagesDTO(locale);
	    modelAttrs.put("messages", messagesJson);
	    	    			
		return new ModelAndView("/WEB-INF/views/view.jsp", modelAttrs);
	}
	
	/**
	 * @param modelAttrs
	 * @param httpReq
	 * @return
	 */
	private HashMap<String, Object> getGoogleAnalyticsAccountData(HashMap<String, Object> modelAttrs) throws TokenResponseException {		
		//		//Authorize Google API			   
	    boolean isValidAccess = false;
	    String message = null;
	    try {
	    	isValidAccess = googleAnalyticsDataExpert.isValidAccess(configurationDTO, pII);
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
		    	String authURL = googleTokenExpert.getAuthURL(configurationDTO.getClient_id(), fullCurrentURL);		    	
		    	modelAttrs.put("authURL", authURL);
	    	}
		//If there are access retrieve the Accounts DTO
	    } else {	    	
	    	GoogleAnalyticsAccountsDTO googleAnalyticsAccountsDTO = new GoogleAnalyticsAccountsDTO();
	    	try {
	    		googleAnalyticsAccountsDTO = googleAnalyticsDataExpert.getGoogleAnalyticsAccounts(configurationDTO, locale, pII);
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
		    	return modelAttrs;
		    } catch(Exception e) {
		    	message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.general.error.processing.data", locale);
		    	modelAttrs.put("errorMessage", message);
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
	 * Handle Ajax sections
	 * @param section
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "adminSections")
    public ModelAndView senseAdminSectionsController(
             ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {		
	    HashMap<String, Object> modelAttrs = new HashMap<String, Object>();	
        modelAttrs.put("configuration", configurationDTO);
                
    	//Default Date Range
    	Date endDate = new Date();
    	Calendar gc = GregorianCalendar.getInstance();
        gc.setTime(new Date());         
        gc.add(Calendar.DAY_OF_MONTH, -30); 
    	Date startDate = gc.getTime();        	
    	modelAttrs.put("startDate", startDate.getTime());
    	modelAttrs.put("endDate", endDate.getTime());
    	
    	//Default Previous Date Range
    	Calendar gcPrev = GregorianCalendar.getInstance();
    	gcPrev.setTime(new Date());
    	gcPrev.add(Calendar.DAY_OF_MONTH, -60); 
		Date startDatePrev = gcPrev.getTime();
		Date endDatePrev=startDate;
		modelAttrs.put("startDatePrev", startDatePrev.getTime());
    	modelAttrs.put("endDatePrev", endDatePrev.getTime());
    	
    	if (configurationDTO.getProfile_id() != null &&  !configurationDTO.getProfile_id().isEmpty()){        	
        	LiferayGoogleAnalyticsDTO liferayGoogleAnalyticsDTO = googleAnalyticsDataExpert.getGoogleAnalyticsData(configurationDTO, null, null, locale, pII, startDate, endDate, startDatePrev, endDatePrev);
        	Gson gson = new Gson();
	        String gad = gson.toJson(liferayGoogleAnalyticsDTO);
	        
	        modelAttrs.put("googleAnalyticsData", gad);
    	} else {
    		String message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.googleanalytics.error.graphics.admin.missing.configuration", locale);
            modelAttrs.put("errorMessage", message);
            return new ModelAndView("/WEB-INF/views/googleanalytics/top_messages.jsp", modelAttrs);
    	}
         
        return new ModelAndView("/WEB-INF/views/googleanalytics/view_reports.jsp", modelAttrs);       
    }
		
	@ResourceMapping(value = "getAnalyticsData")
    public ModelAndView getAnalyticsDataController(
             Long startDateS
            ,Long endDateS
            ,Long startDatePrevS
            ,Long endDatePrevS
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {       
		Date startDate = new Date(startDateS);
		Date endDate = new Date(endDateS);		
		Date startDatePrev = null;
		Date endDatePrev=null;
		
		if (startDatePrevS != null && endDatePrevS != null) {
			startDatePrev = new Date(startDatePrevS);
			endDatePrev = new Date(endDatePrevS);
		}
		
		LiferayGoogleAnalyticsDTO liferayGoogleAnalyticsDTO = googleAnalyticsDataExpert.getGoogleAnalyticsData(configurationDTO, null, null, locale, pII, startDate, endDate, startDatePrev, endDatePrev);
    	Gson gson = new Gson();
        String gad = gson.toJson(liferayGoogleAnalyticsDTO);

        response.getWriter().write(gad);
        return null;	
	}
	
}