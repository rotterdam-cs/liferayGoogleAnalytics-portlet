package com.rcs.googleanalytics.portlet;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ResourceBundleHelper;
import com.rcs.dto.ConfigurationDTO;
import com.rcs.dto.LiferayGoogleAnalyticsDTO;
import com.rcs.enums.MessagesEnum;
import com.rcs.expert.ConfigurationExpert;
import com.rcs.expert.GoogleAnalyticsDataExpert;
import com.rcs.expert.UtilsExpert;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@Scope("session")
@RequestMapping("VIEW")
public class GoogleAnalyticsController {
	private ConfigurationDTO configurationDTO = new ConfigurationDTO();
	private Locale locale;
	private PortalInstanceIdentifier pII;
	private ConfigurationExpert configurationExpert = new ConfigurationExpert();
		
    private UtilsExpert utilsExpert = new UtilsExpert();
		
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
		configurationDTO.getRedirect_url();
		pII = utilsExpert.getPortalInstanceIdentifier(request);
	   
		modelAttrs = googleAnalyticsDataExpert.getGoogleAnalyticsAccountData(configurationDTO, pII, locale, modelAttrs);		
		modelAttrs.put("configuration", configurationDTO);		
	    String messagesJson = MessagesEnum.getMessagesDTO(locale);
	    modelAttrs.put("messages", messagesJson);
	    	    			
		return new ModelAndView("view", modelAttrs);
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
            return new ModelAndView("googleanalytics/top_messages", modelAttrs);
    	}
         
        return new ModelAndView("googleanalytics/view_reports", modelAttrs);       
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