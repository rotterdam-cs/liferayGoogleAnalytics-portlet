package com.rcs.expert;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.rcs.common.PortalInstanceIdentifier;
import com.rcs.common.ServiceActionResult;
import com.rcs.dto.ConfigurationDTO;
import com.rcs.enums.GoogleAnalyticsConfigurationEnum;
import com.rcs.service.model.Configuration;
import com.rcs.service.service.ConfigurationLocalServiceUtil;

/**
* @author Prj.M@x <pablo.rendon@rotterdam-cs.com>
*/
@Component
public class ConfigurationExpert {
	private static Log log = LogFactoryUtil.getLog(ConfigurationExpert.class); 
	
	/**
	 * Get Configuration DTO
	 * @param portalInstanceIdentifier
	 * @return
	 */
	public ConfigurationDTO getConfiguration(PortalInstanceIdentifier pII) {		
		ConfigurationDTO configurationDTO = new ConfigurationDTO();		
		configurationDTO.setAccount_id(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.ACCOUNT_ID.getKey(), pII));
		configurationDTO.setApi_key(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.APIKEY.getKey(), pII));
		configurationDTO.setClient_id(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.CLIENT_ID.getKey(), pII));
		configurationDTO.setProfile_id(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.PROFILE_ID.getKey(), pII));
		configurationDTO.setProperty_id(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.PROPERTY_ID.getKey(), pII));
		configurationDTO.setToken(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.TOKEN.getKey(), pII));
		configurationDTO.setRefreshtoken(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.REFRESHTOKEN.getKey(), pII));
		configurationDTO.setClient_secret(getConfigurationValueByPropertyName(GoogleAnalyticsConfigurationEnum.CLIENT_SECRET.getKey(), pII));
		return configurationDTO;
	}
	
	
	/**
	 * Get Configuration Object by property name
	 * @param propertyname
	 * @param portalInstanceIdentifier
	 * @return
	 */
	public Configuration getConfigurationByPropertyName(String propertyname, PortalInstanceIdentifier pII){
		long groupId = pII.getGroupId();
		long companyId = pII.getCompanyId();
		Configuration entity = null;
		if (pII.validateParameters()) {
			List<Configuration> configuration;
			try {
				configuration = ConfigurationLocalServiceUtil.getConfigurationByPropertyName(propertyname, groupId, companyId);
				if (!configuration.isEmpty()) {
					entity = configuration.get(0);
				}
			} catch (PortalException e) {
				log.error(e);
			} catch (SystemException e) {
				log.error(e);
			}		
		}
		return entity;
	}
		
	/**
	 * Get Configuration value by property name
	 * @param propertyname
	 * @param portalInstanceIdentifier
	 * @return
	 */
	public String getConfigurationValueByPropertyName(String propertyname, PortalInstanceIdentifier pII){
		String value = "";
		Configuration configuration = getConfigurationByPropertyName(propertyname, pII);
		if (configuration != null){
			value = configuration.getPropertyvalue();
		}
		return value;
	}
	
	
	/**
	 * Update Configuration or create a new one if it does not exists
	 * @param portalInstanceIdentifier
	 * @param propretyName
	 * @param propertyValue
	 * @return
	 */
	public ServiceActionResult<Configuration> updateConfiguration (PortalInstanceIdentifier pII, String propretyName, String propertyValue) {				
		ServiceActionResult<Configuration> resultupdate = null;
		try {
			if (pII.validateFullParameters()) {
				Configuration configuration = getConfigurationByPropertyName(propretyName, pII);
		        if (configuration == null) {
		        	configuration = ConfigurationLocalServiceUtil.addConfiguration(pII.getUserId(), pII.getGroupId(), propretyName, propertyValue);
		        } else {		        	
		        	configuration.setPropertyvalue(propertyValue);
	        		ConfigurationLocalServiceUtil.updateConfiguration(configuration);
	        		resultupdate = ServiceActionResult.buildSuccess(configuration);
		        }
		        resultupdate = ServiceActionResult.buildSuccess(configuration);
			} else {
				resultupdate = ServiceActionResult.buildFailure(null);
			}
	        
		} catch (NoSuchUserException e) {
			log.error("NoSuchUserException " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		} catch (SystemException e) {
			log.error("SystemException " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		}catch (PortalException e) {
			log.error("PortalException " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		} catch (Exception e) {
			log.error("Exception " + e.getMessage());
			resultupdate = ServiceActionResult.buildFailure(null);
		} 
		return resultupdate;
    }
	
}
