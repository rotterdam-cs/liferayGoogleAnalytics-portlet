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

package com.rcs.service.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.rcs.service.model.Configuration;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Configuration in entity cache.
 *
 * @author RCS - Pablo Rendon
 * @see Configuration
 * @generated
 */
public class ConfigurationCacheModel implements CacheModel<Configuration>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{configurationId=");
		sb.append(configurationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", propertyname=");
		sb.append(propertyname);
		sb.append(", propertyvalue=");
		sb.append(propertyvalue);
		sb.append("}");

		return sb.toString();
	}

	public Configuration toEntityModel() {
		ConfigurationImpl configurationImpl = new ConfigurationImpl();

		configurationImpl.setConfigurationId(configurationId);
		configurationImpl.setGroupId(groupId);
		configurationImpl.setCompanyId(companyId);
		configurationImpl.setUserId(userId);

		if (userName == null) {
			configurationImpl.setUserName(StringPool.BLANK);
		}
		else {
			configurationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			configurationImpl.setCreateDate(null);
		}
		else {
			configurationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			configurationImpl.setModifiedDate(null);
		}
		else {
			configurationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (propertyname == null) {
			configurationImpl.setPropertyname(StringPool.BLANK);
		}
		else {
			configurationImpl.setPropertyname(propertyname);
		}

		if (propertyvalue == null) {
			configurationImpl.setPropertyvalue(StringPool.BLANK);
		}
		else {
			configurationImpl.setPropertyvalue(propertyvalue);
		}

		configurationImpl.resetOriginalValues();

		return configurationImpl;
	}

	public long configurationId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String propertyname;
	public String propertyvalue;
}