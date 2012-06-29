package com.rcs.expert;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.rcs.common.LocalResponse;
import com.rcs.common.PortalInstanceIdentifier;

@Component
public class UtilsExpert {
	private static final Logger log = Logger.getLogger(UtilsExpert.class);

	/**
	 * Get PortalInstaceIdentifier based on the PortletRequest
	 * @param request
	 * @return
	 */
	public PortalInstanceIdentifier getPortalInstanceIdentifier(PortletRequest request) {
		ThemeDisplay themeDisplay= (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		long liferayUserId = (Long) request.getAttribute(WebKeys.USER_ID);
		long groupId = themeDisplay.getScopeGroupId();		
		return getPortalInstanceIdentifierByParameters(groupId, liferayUserId);
	}
	
	/**
	 * Get PortalInstaceIdentifier based on the ResourceRequest
	 * @param request
	 * @return
	 */
	public PortalInstanceIdentifier getPortalInstanceIdentifier(ResourceRequest request) {
		ThemeDisplay themeDisplay= (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		long liferayUserId = (Long) request.getAttribute(WebKeys.USER_ID);
		long groupId = themeDisplay.getScopeGroupId();		
		return getPortalInstanceIdentifierByParameters(groupId, liferayUserId);
	}
	
	/**
	 * Get PortalInstaceIdentifier based on the groupId and lifearyUserId
	 * @param groupId
	 * @param liferayUserId
	 * @return
	 */
	private PortalInstanceIdentifier getPortalInstanceIdentifierByParameters(long groupId, long liferayUserId) {
		PortalInstanceIdentifier portalInstanceIdentifier = new PortalInstanceIdentifier();
		portalInstanceIdentifier.setCompanyIdByUserId(liferayUserId);
		portalInstanceIdentifier.setGroupId(groupId);
		portalInstanceIdentifier.setUserId(liferayUserId);
		return portalInstanceIdentifier;
	}
	
	/**
	 * 
	 * @param localResponse
	 * @return
	 */
	public String getJsonFromLocalResponse(LocalResponse localResponse) {
        Gson gson = new Gson();
        String result = gson.toJson(localResponse);
        log.error("validationMessage: " + result);
        return result;

    }
	
	
	
}
