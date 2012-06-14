package com.rcs.googleanalytics.portlet;

import static com.rcs.common.Constants.ADMIN_SECTION_CONFIGURATION;
import static com.rcs.common.Constants.ADMIN_SECTION_VIEW_REPORTS;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.rcs.common.GoogleAnalyticsConfigurationEnum;
import com.rcs.common.LocalResponse;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ResourceBundleHelper;
import com.rcs.common.ServiceActionResult;
import com.rcs.googleanalytics.dto.ConfigurationDTO;
import com.rcs.googleanalytics.expert.ConfigurationExpert;
import com.rcs.googleanalytics.expert.UtilsExpert;
import com.rcs.service.model.Configuration;

/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller
@RequestMapping("VIEW")
public class GoogleAnalyticsController {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsController.class);
	
	@Autowired
    private ConfigurationExpert configurationExpert;
	
	@Autowired
    private UtilsExpert utilsExpert;	
	
	
	@RenderMapping
	public ModelAndView resolveView(PortletRequest request, PortletResponse response) throws PortalException, SystemException {			
		PortalInstanceIdentifier pII = utilsExpert.getPortalInstanceIdentifier(request);
		ConfigurationDTO configurationDTO = configurationExpert.getConfiguration(pII);		
		log.error("********************* configuration getAccount_id" + configurationDTO.getAccount_id());
		return new ModelAndView("googleanalytics/view");
	}
	
	
	@ResourceMapping(value = "adminSections")
    public ModelAndView senseAdminSectionsController(
             String section
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {
		
	    HashMap<String, Object> modelAttrs = new HashMap<String, Object>();
		PortalInstanceIdentifier pII = utilsExpert.getPortalInstanceIdentifier(request);		
        ConfigurationDTO configurationDTO = configurationExpert.getConfiguration(pII);
        modelAttrs.put("configuration", configurationDTO);        
        if (section.equals(ADMIN_SECTION_CONFIGURATION)) {            
            //Account          
        } else if (section.equals(ADMIN_SECTION_VIEW_REPORTS)) {            
            //Analytics                      
        } 
        return new ModelAndView("googleanalytics/" + section, modelAttrs);       
    }
	
	
	@ResourceMapping(value = "adminSaveConfiguration")
    public ModelAndView adminSaveConfigurationController(
             String client_id
            ,String api_key
            ,ResourceRequest request
            ,ResourceResponse response
    ) throws Exception {		
		HashMap<String, String> configurationOptions = new HashMap<String, String>();       
		Locale locale = LocaleUtil.fromLanguageId(LanguageUtil.getLanguageId(request));	
		PortalInstanceIdentifier pII = utilsExpert.getPortalInstanceIdentifier(request);
		LocalResponse result = new LocalResponse();
		String message = "";
        
        //CONFIGURATION OPTIONS
        configurationOptions.put(GoogleAnalyticsConfigurationEnum.CLIENT_ID.getKey(), client_id);
        configurationOptions.put(GoogleAnalyticsConfigurationEnum.APIKEY.getKey(), api_key);
        
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
		
        result.setSuccess(true);
        message += ResourceBundleHelper.getKeyLocalizedValue("com.rcs.admin.configuration.saved", locale);
        result.setMessage(message);
        log.error(message);
        response.getWriter().write(utilsExpert.getJsonFromLocalResponse(result));
        return null;	
	}
	

}