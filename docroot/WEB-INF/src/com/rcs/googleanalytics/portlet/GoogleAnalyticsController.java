package com.rcs.googleanalytics.portlet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.rcs.common.GoogleAnalyticsConfigurationEnum;
import com.rcs.service.service.ConfigurationLocalServiceUtil;


/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller(value="GoogleAnalyticsController") 
@RequestMapping("VIEW")
public class GoogleAnalyticsController {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsController.class);
	
	@RenderMapping
	public ModelAndView resolveView(PortletRequest request, PortletResponse response) throws PortalException, SystemException {
		ThemeDisplay themeDisplay= (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		
		long userId = (Long) request.getAttribute(WebKeys.USER_ID);
		User user = UserUtil.findByPrimaryKey(userId);
		long companyId = user.getCompanyId();
		long groupId = themeDisplay.getScopeGroupId();
		
		
		ConfigurationLocalServiceUtil.getConfigurationByProperty(GoogleAnalyticsConfigurationEnum.ACCOUNT_ID.getKey(), groupId, companyId);
		log.error("********************* GoogleAnalyticsController");
		log.error("********************* userId: " + userId);
		return new ModelAndView("view");
	}

}