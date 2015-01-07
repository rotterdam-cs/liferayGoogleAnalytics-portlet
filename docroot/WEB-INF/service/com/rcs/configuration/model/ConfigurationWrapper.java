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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Configuration}.
 * </p>
 *
 * @author rotterdamcs
 * @see Configuration
 * @generated
 */
public class ConfigurationWrapper implements Configuration,
	ModelWrapper<Configuration> {
	public ConfigurationWrapper(Configuration configuration) {
		_configuration = configuration;
	}

	@Override
	public Class<?> getModelClass() {
		return Configuration.class;
	}

	@Override
	public String getModelClassName() {
		return Configuration.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("configurationId", getConfigurationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("propertyname", getPropertyname());
		attributes.put("propertyvalue", getPropertyvalue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long configurationId = (Long)attributes.get("configurationId");

		if (configurationId != null) {
			setConfigurationId(configurationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String propertyname = (String)attributes.get("propertyname");

		if (propertyname != null) {
			setPropertyname(propertyname);
		}

		String propertyvalue = (String)attributes.get("propertyvalue");

		if (propertyvalue != null) {
			setPropertyvalue(propertyvalue);
		}
	}

	/**
	* Returns the primary key of this configuration.
	*
	* @return the primary key of this configuration
	*/
	@Override
	public long getPrimaryKey() {
		return _configuration.getPrimaryKey();
	}

	/**
	* Sets the primary key of this configuration.
	*
	* @param primaryKey the primary key of this configuration
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_configuration.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the configuration ID of this configuration.
	*
	* @return the configuration ID of this configuration
	*/
	@Override
	public long getConfigurationId() {
		return _configuration.getConfigurationId();
	}

	/**
	* Sets the configuration ID of this configuration.
	*
	* @param configurationId the configuration ID of this configuration
	*/
	@Override
	public void setConfigurationId(long configurationId) {
		_configuration.setConfigurationId(configurationId);
	}

	/**
	* Returns the group ID of this configuration.
	*
	* @return the group ID of this configuration
	*/
	@Override
	public long getGroupId() {
		return _configuration.getGroupId();
	}

	/**
	* Sets the group ID of this configuration.
	*
	* @param groupId the group ID of this configuration
	*/
	@Override
	public void setGroupId(long groupId) {
		_configuration.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this configuration.
	*
	* @return the company ID of this configuration
	*/
	@Override
	public long getCompanyId() {
		return _configuration.getCompanyId();
	}

	/**
	* Sets the company ID of this configuration.
	*
	* @param companyId the company ID of this configuration
	*/
	@Override
	public void setCompanyId(long companyId) {
		_configuration.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this configuration.
	*
	* @return the user ID of this configuration
	*/
	@Override
	public long getUserId() {
		return _configuration.getUserId();
	}

	/**
	* Sets the user ID of this configuration.
	*
	* @param userId the user ID of this configuration
	*/
	@Override
	public void setUserId(long userId) {
		_configuration.setUserId(userId);
	}

	/**
	* Returns the user uuid of this configuration.
	*
	* @return the user uuid of this configuration
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configuration.getUserUuid();
	}

	/**
	* Sets the user uuid of this configuration.
	*
	* @param userUuid the user uuid of this configuration
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_configuration.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this configuration.
	*
	* @return the user name of this configuration
	*/
	@Override
	public java.lang.String getUserName() {
		return _configuration.getUserName();
	}

	/**
	* Sets the user name of this configuration.
	*
	* @param userName the user name of this configuration
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_configuration.setUserName(userName);
	}

	/**
	* Returns the create date of this configuration.
	*
	* @return the create date of this configuration
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _configuration.getCreateDate();
	}

	/**
	* Sets the create date of this configuration.
	*
	* @param createDate the create date of this configuration
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_configuration.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this configuration.
	*
	* @return the modified date of this configuration
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _configuration.getModifiedDate();
	}

	/**
	* Sets the modified date of this configuration.
	*
	* @param modifiedDate the modified date of this configuration
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_configuration.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the propertyname of this configuration.
	*
	* @return the propertyname of this configuration
	*/
	@Override
	public java.lang.String getPropertyname() {
		return _configuration.getPropertyname();
	}

	/**
	* Sets the propertyname of this configuration.
	*
	* @param propertyname the propertyname of this configuration
	*/
	@Override
	public void setPropertyname(java.lang.String propertyname) {
		_configuration.setPropertyname(propertyname);
	}

	/**
	* Returns the propertyvalue of this configuration.
	*
	* @return the propertyvalue of this configuration
	*/
	@Override
	public java.lang.String getPropertyvalue() {
		return _configuration.getPropertyvalue();
	}

	/**
	* Sets the propertyvalue of this configuration.
	*
	* @param propertyvalue the propertyvalue of this configuration
	*/
	@Override
	public void setPropertyvalue(java.lang.String propertyvalue) {
		_configuration.setPropertyvalue(propertyvalue);
	}

	@Override
	public boolean isNew() {
		return _configuration.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_configuration.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _configuration.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_configuration.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _configuration.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _configuration.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_configuration.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _configuration.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_configuration.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_configuration.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_configuration.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ConfigurationWrapper((Configuration)_configuration.clone());
	}

	@Override
	public int compareTo(
		com.rcs.configuration.model.Configuration configuration) {
		return _configuration.compareTo(configuration);
	}

	@Override
	public int hashCode() {
		return _configuration.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.rcs.configuration.model.Configuration> toCacheModel() {
		return _configuration.toCacheModel();
	}

	@Override
	public com.rcs.configuration.model.Configuration toEscapedModel() {
		return new ConfigurationWrapper(_configuration.toEscapedModel());
	}

	@Override
	public com.rcs.configuration.model.Configuration toUnescapedModel() {
		return new ConfigurationWrapper(_configuration.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _configuration.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _configuration.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_configuration.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ConfigurationWrapper)) {
			return false;
		}

		ConfigurationWrapper configurationWrapper = (ConfigurationWrapper)obj;

		if (Validator.equals(_configuration, configurationWrapper._configuration)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Configuration getWrappedConfiguration() {
		return _configuration;
	}

	@Override
	public Configuration getWrappedModel() {
		return _configuration;
	}

	@Override
	public void resetOriginalValues() {
		_configuration.resetOriginalValues();
	}

	private Configuration _configuration;
}