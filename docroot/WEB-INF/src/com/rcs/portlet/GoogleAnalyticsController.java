package com.rcs.portlet;

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
import com.rcs.service.model.Configuration;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@Scope("session")
@RequestMapping("VIEW")
public class GoogleAnalyticsController {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsController.class);
	
	private ConfigurationDTO configurationDTO;
	private Locale locale;
	private PortalInstanceIdentifier pII;
	private String fullCurrentURL;
	private String code;
	
	@Autowired
    private ConfigurationExpert configurationExpert;
	
	@Autowired
    private UtilsExpert utilsExpert;
	
	@Autowired
    private GoogleTokenExpert googleTokenExpert;
	
	@Autowired
	private GoogleAnalyticsDataExpert googleAnalyticsDataExpert;
		
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
		HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		locale = LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request));		
		pII = utilsExpert.getPortalInstanceIdentifier(request);
		configurationDTO = configurationExpert.getConfiguration(pII);
		fullCurrentURL = PortalUtil.getCurrentCompleteURL(httpReq);
		code = httpReq.getParameter("code");
		
		modelAttrs= getGoogleAnalyticsAccountData(modelAttrs); 
		modelAttrs.put("configuration", configurationDTO);		
	    String messagesJson = MessagesEnum.getMessagesDTO(locale);
	    modelAttrs.put("messages", messagesJson);
	    	    
		return new ModelAndView("googleanalytics/view", modelAttrs);
	}
	
	/**
	 * @param modelAttrs
	 * @param httpReq
	 * @return
	 */
	private HashMap<String, Object> getGoogleAnalyticsAccountData(HashMap<String, Object> modelAttrs) {		
		//Authorize Google API
		String redirect_url = fullCurrentURL;
	    if (code != null) {
	    	redirect_url = fullCurrentURL.replace("&code=", "").replace("?code=", "").replace(code, "");
	    }

	    //If there are not access to google api, request authorization
	    if (!googleAnalyticsDataExpert.isValidAccess(configurationDTO, code, redirect_url, pII)) {
	    	if (configurationDTO.getClient_id() != null && !configurationDTO.getClient_id().isEmpty()){
		    	String authURL = googleTokenExpert.getAuthURL(configurationDTO.getClient_id(), fullCurrentURL);
		    	modelAttrs.put("authURL", authURL);
	    	}
		//If there are access retrieve the Accounts DTO
	    } else {
			GoogleAnalyticsAccountsDTO googleAnalyticsAccountsDTO = googleAnalyticsDataExpert.getGoogleAnalyticsAccounts(configurationDTO, code, redirect_url, locale, pII);
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
             String section
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {		
	    HashMap<String, Object> modelAttrs = new HashMap<String, Object>();	
        modelAttrs.put("configuration", configurationDTO);
        
        //Account
        if (section.equals(ADMIN_SECTION_CONFIGURATION)) {        	
        	modelAttrs= getGoogleAnalyticsAccountData(modelAttrs); 
        	modelAttrs.put("fullCurrentURL", fullCurrentURL);
        	URL url = new URL(fullCurrentURL);
        	modelAttrs.put("serverURL", "http://"+url.getHost());
        
        	//Analytics
        } else if (section.equals(ADMIN_SECTION_VIEW_REPORTS)) {
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
        		String message = ResourceBundleHelper.getKeyLocalizedValue("com.rcs.admin.error.graphics.profile.not.selected", locale);
                modelAttrs.put("errorMessage", message);
                return new ModelAndView("googleanalytics/top_messages", modelAttrs);
        	}
        } 
        return new ModelAndView("googleanalytics/" + section, modelAttrs);       
    }
	
	/**
	 * Save several configurations
	 * @param client_id
	 * @param api_key
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "adminSaveConfigurations")
    public ModelAndView adminSaveConfigurationsController(
             String client_id
            ,String api_key
            ,String client_secret
            ,String account_id
            ,String property_id
            ,String profile_id
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {		
		HashMap<String, String> configurationOptions = new HashMap<String, String>();
		LocalResponse result = new LocalResponse();
		String message = "";
        
        //CONFIGURATION OPTIONS
		if (client_id != null) {
			configurationOptions.put(GoogleAnalyticsConfigurationEnum.CLIENT_ID.getKey(), client_id);
		}
		if (api_key != null) {
			configurationOptions.put(GoogleAnalyticsConfigurationEnum.APIKEY.getKey(), api_key);
		}
		if (client_secret != null) {
			configurationOptions.put(GoogleAnalyticsConfigurationEnum.CLIENT_SECRET.getKey(), client_secret);
		}
		if (account_id != null) {
			configurationOptions.put(GoogleAnalyticsConfigurationEnum.ACCOUNT_ID.getKey(), account_id);
		}
		if (property_id != null) {
			configurationOptions.put(GoogleAnalyticsConfigurationEnum.PROPERTY_ID.getKey(), property_id);
		}
		if (profile_id != null) {
			configurationOptions.put(GoogleAnalyticsConfigurationEnum.PROFILE_ID.getKey(), profile_id);
		}
        
        //Save all configuration options
        for (Map.Entry<String, String> entry : configurationOptions.entrySet()) {
            ServiceActionResult<Configuration> resultupdate = configurationExpert.updateConfiguration(pII, entry.getKey(), entry.getValue());
            if (!resultupdate.isSuccess()) {
                result.setSuccess(false);                
                List<String> validationKeys = resultupdate.getValidationKeys();
                for (String key : validationKeys) {                    
                    message += key + " ";
                    log.error("ERROR " + key);
                }
                result.setMessage(message);        
                response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
                return null;
            }
        }
		
        configurationDTO = configurationExpert.getConfiguration(pII);
        
        result.setSuccess(true);
        message += ResourceBundleHelper.getKeyLocalizedValue("com.rcs.admin.configuration.saved", locale);
        result.setMessage(message);
        log.error(message);
        
        if (client_id != null && client_secret != null){
	        googleAnalyticsDataExpert.killSessionCredential();
	        if (configurationDTO.getClient_id() != null && !configurationDTO.getClient_id().isEmpty()){
		    	String authURL = googleTokenExpert.getAuthURL(configurationDTO.getClient_id(), fullCurrentURL);
		    	configurationDTO.setAuthURL(authURL);	    	
		    	
		    	 if (googleAnalyticsDataExpert.isValidAccess(configurationDTO, code, fullCurrentURL, pII)) {
		    		 configurationDTO.setValidAccess(true);
		    	 } else {
		    		 configurationDTO.setValidAccess(false);
		    	 }		    	
		    	Gson gson = new Gson();
		        String body = gson.toJson(configurationDTO);
		        result.setBody(body);
	    	}   
        }
        
        
        response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
        return null;	
	}
	
	/**
	 * Save one configuration parameter
	 * @param configurationname
	 * @param configurationvalue
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResourceMapping(value = "adminSaveConfiguration")
    public ModelAndView adminSaveConfigurationController(
             String configurationname
            ,String configurationvalue
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {
		LocalResponse result = new LocalResponse();
		String message = "";
       
        ServiceActionResult<Configuration> resultupdate = configurationExpert.updateConfiguration(pII, configurationname, configurationvalue);
        if (!resultupdate.isSuccess()) {
            result.setSuccess(false);                
            List<String> validationKeys = resultupdate.getValidationKeys();
            for (String key : validationKeys) {                    
                message += key + " ";
                log.error("ERROR " + key);
            }
            result.setMessage(message);        
            response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
            return null;
        }
        
        configurationDTO = configurationExpert.getConfiguration(pII);
        
        result.setSuccess(true);
        message += ResourceBundleHelper.getKeyLocalizedValue("com.rcs.admin.configuration.saved", locale);
        result.setMessage(message);
        log.error(message);
        response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
        return null;	
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