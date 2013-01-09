/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.rcs.configuration.service.impl;

import java.util.Date;
import java.util.List;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.rcs.configuration.model.Configuration;
import com.rcs.configuration.service.base.ConfigurationLocalServiceBaseImpl;
import com.rcs.enums.GoogleAnalyticsConfigurationEnum;

/**
 * The implementation of the configuration local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.rcs.configuration.service.ConfigurationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author rotterdamcs
 * @see com.rcs.configuration.service.base.ConfigurationLocalServiceBaseImpl
 * @see com.rcs.configuration.service.ConfigurationLocalServiceUtil
 */
public class ConfigurationLocalServiceImpl
	extends ConfigurationLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.rcs.configuration.service.ConfigurationLocalServiceUtil} to access the configuration local service.
	 */
	private static Log log = LogFactoryUtil.getLog(ConfigurationLocalServiceImpl.class);

	/**
	 * 
	 */
	public Configuration addConfiguration(long userId, long groupId, String propertyname, String propertyvalue) throws PortalException, SystemException {
	    User user = UserUtil.findByPrimaryKey(userId);
	    
	    List<Configuration> configurations = getConfigurationByPropertyName(propertyname);
	    
	    if (configurations.isEmpty()) {
	    
		    Date now = new Date();
		    long configurationId = CounterLocalServiceUtil.increment(Configuration.class.getName());
	
		    Configuration configuration = configurationPersistence.create(configurationId);
		    configuration.setPropertyname(propertyname);
		    configuration.setPropertyvalue(propertyvalue);
		    configuration.setCompanyId(user.getCompanyId());
		    configuration.setGroupId(groupId);
		    configuration.setUserId(user.getUserId());
		    configuration.setUserName(user.getFullName());
		    configuration.setCreateDate(now);
		    configuration.setModifiedDate(now);
		    
		    return configurationPersistence.update(configuration,false);
	    } else {
	    	return null;
	    }
	}
		
	public List<Configuration> getConfigurationByPropertyName(String propertyname, long groupId, long companyId) 
			throws PortalException, SystemException {	    
	    List<Configuration> entities = configurationPersistence.findByPropertynameGroupIdCompanyId(propertyname, groupId, companyId);
		return entities;
	}	
	
	public List<Configuration> getConfigurationByPropertyName(String propertyname) 
			throws PortalException, SystemException {	    
	    List<Configuration> entities = configurationPersistence.findByPropertyname(propertyname);
		return entities;
	}
	
	public void deleteDetailedConfiguration() throws SystemException {
		configurationPersistence.removeByPropertyname(GoogleAnalyticsConfigurationEnum.ACCOUNT_ID.getKey());
		configurationPersistence.removeByPropertyname(GoogleAnalyticsConfigurationEnum.CODE.getKey());
		configurationPersistence.removeByPropertyname(GoogleAnalyticsConfigurationEnum.PROFILE_ID.getKey());
		configurationPersistence.removeByPropertyname(GoogleAnalyticsConfigurationEnum.PROPERTY_ID.getKey());
		configurationPersistence.removeByPropertyname(GoogleAnalyticsConfigurationEnum.TOKEN.getKey());
		configurationPersistence.clearCache();
	}
	
	
}