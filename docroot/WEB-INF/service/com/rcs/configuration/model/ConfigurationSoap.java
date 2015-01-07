/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.rcs.configuration.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author rotterdamcs
 * @generated
 */
public class ConfigurationSoap implements Serializable {
	public static ConfigurationSoap toSoapModel(Configuration model) {
		ConfigurationSoap soapModel = new ConfigurationSoap();

		soapModel.setConfigurationId(model.getConfigurationId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPropertyname(model.getPropertyname());
		soapModel.setPropertyvalue(model.getPropertyvalue());

		return soapModel;
	}

	public static ConfigurationSoap[] toSoapModels(Configuration[] models) {
		ConfigurationSoap[] soapModels = new ConfigurationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ConfigurationSoap[][] toSoapModels(Configuration[][] models) {
		ConfigurationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ConfigurationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ConfigurationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ConfigurationSoap[] toSoapModels(List<Configuration> models) {
		List<ConfigurationSoap> soapModels = new ArrayList<ConfigurationSoap>(models.size());

		for (Configuration model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ConfigurationSoap[soapModels.size()]);
	}

	public ConfigurationSoap() {
	}

	public long getPrimaryKey() {
		return _configurationId;
	}

	public void setPrimaryKey(long pk) {
		setConfigurationId(pk);
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

	private long _configurationId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _propertyname;
	private String _propertyvalue;
}