package com.rcs.common;

import java.util.Locale;
import java.util.ResourceBundle;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 *
 * @author chuqui
 */
public class ResourceBundleHelper {
	
	private static Log log = LogFactoryUtil.getLog(ResourceBundleHelper.class);
	
    public static String getKeyLocalizedValue(String key, Locale locale){
    	try {
	        ResourceBundle res = ResourceBundle.getBundle("Language", locale);
	        if(res.containsKey(key)){
	            return res.getString(key);
	        }else{
	            return null;
	        }
    	} catch (Exception e) {
    		log.error("ResourceBundleHelper: " + e.getMessage());
    		return null;
    	}
    }
}