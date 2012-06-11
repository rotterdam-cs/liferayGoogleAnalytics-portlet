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


/**
 * @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
 */
@Controller(value="GoogleAnalyticsController") 
@RequestMapping("VIEW")
public class GoogleAnalyticsController {
	private static Log log = LogFactoryUtil.getLog(GoogleAnalyticsController.class);
	
	@RenderMapping
	public ModelAndView resolveView(PortletRequest request, PortletResponse response) throws PortalException, SystemException {
		log.error("********************* GoogleAnalyticsController");
		long userId = (Long) request.getAttribute(WebKeys.USER_ID);
		log.error("********************* userId: " + userId);
		return new ModelAndView("view");
	}

}