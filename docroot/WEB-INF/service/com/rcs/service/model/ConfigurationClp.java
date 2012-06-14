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

package com.rcs.service.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.rcs.service.service.ConfigurationLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;

/**
 * @author RCS - Pablo Rendon
 */
public class ConfigurationClp extends BaseModelImpl<Configuration>
	implements Configuration {
	public ConfigurationClp() {
	}

	public Class<?> getModelClass() {
		return Configuration.class;
	}

	public String getModelClassName() {
		return Configuration.class.getName();
	}

	public long getPrimaryKey() {
		return _configurationId;
	}

	public void setPrimaryKey(long primaryKey) {
		setConfigurationId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_configurationId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getConfigurationId() {
		return _configurationId;
	}

	public void setConfigurationId(long configurationId) {
		_configurationId = configurationId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getPropertyname() {
		return _propertyname;
	}

	public void setPropertyname(String propertyname) {
		_propertyname = propertyname;
	}

	public String getPropertyvalue() {
		return _propertyvalue;
	}

	public void setPropertyvalue(String propertyvalue) {
		_propertyvalue = propertyvalue;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			ConfigurationLocalServiceUtil.addConfiguration(this);
		}
		else {
			ConfigurationLocalServiceUtil.updateConfiguration(this);
		}
	}

	@Override
	public Configuration toEscapedModel() {
		return (Configuration)Proxy.newProxyInstance(Configuration.class.getClassLoader(),
			new Class[] { Configuration.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		ConfigurationClp clone = new ConfigurationClp();

		clone.setConfigurationId(getConfigurationId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setPropertyname(getPropertyname());
		clone.setPropertyvalue(getPropertyvalue());

		return clone;
	}

	public int compareTo(Configuration configuration) {
		int value = 0;

		if (getConfigurationId() < configuration.getConfigurationId()) {
			value = -1;
		}
		else if (getConfigurationId() > configuration.getConfigurationId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ConfigurationClp configuration = null;

		try {
			configuration = (ConfigurationClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = configuration.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{configurationId=");
		sb.append(getConfigurationId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", propertyname=");
		sb.append(getPropertyname());
		sb.append(", propertyvalue=");
		sb.append(getPropertyvalue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.rcs.service.model.Configuration");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>configurationId</column-name><column-value><![CDATA[");
		sb.append(getConfigurationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>propertyname</column-name><column-value><![CDATA[");
		sb.append(getPropertyname());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>propertyvalue</column-name><column-value><![CDATA[");
		sb.append(getPropertyvalue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _configurationId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _propertyname;
	private String _propertyvalue;
}