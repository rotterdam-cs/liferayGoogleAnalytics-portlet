/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.rcs.configuration.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ConfigurationLocalService}.
 *
 * @author rotterdamcs
 * @see ConfigurationLocalService
 * @generated
 */
public class ConfigurationLocalServiceWrapper
	implements ConfigurationLocalService,
		ServiceWrapper<ConfigurationLocalService> {
	public ConfigurationLocalServiceWrapper(
		ConfigurationLocalService configurationLocalService) {
		_configurationLocalService = configurationLocalService;
	}

	/**
	* Adds the configuration to the database. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @return the configuration that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.rcs.configuration.model.Configuration addConfiguration(
		com.rcs.configuration.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.addConfiguration(configuration);
	}

	/**
	* Creates a new configuration with the primary key. Does not add the configuration to the database.
	*
	* @param configurationId the primary key for the new configuration
	* @return the new configuration
	*/
	@Override
	public com.rcs.configuration.model.Configuration createConfiguration(
		long configurationId) {
		return _configurationLocalService.createConfiguration(configurationId);
	}

	/**
	* Deletes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param configurationId the primary key of the configuration
	* @return the configuration that was removed
	* @throws PortalException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.rcs.configuration.model.Configuration deleteConfiguration(
		long configurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.deleteConfiguration(configurationId);
	}

	/**
	* Deletes the configuration from the database. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @return the configuration that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.rcs.configuration.model.Configuration deleteConfiguration(
		com.rcs.configuration.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.deleteConfiguration(configuration);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _configurationLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.rcs.configuration.model.Configuration fetchConfiguration(
		long configurationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.fetchConfiguration(configurationId);
	}

	/**
	* Returns the configuration with the primary key.
	*
	* @param configurationId the primary key of the configuration
	* @return the configuration
	* @throws PortalException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.rcs.configuration.model.Configuration getConfiguration(
		long configurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.getConfiguration(configurationId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the configurations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @return the range of configurations
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.rcs.configuration.model.Configuration> getConfigurations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.getConfigurations(start, end);
	}

	/**
	* Returns the number of configurations.
	*
	* @return the number of configurations
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getConfigurationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.getConfigurationsCount();
	}

	/**
	* Updates the configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @return the configuration that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.rcs.configuration.model.Configuration updateConfiguration(
		com.rcs.configuration.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.updateConfiguration(configuration);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _configurationLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_configurationLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _configurationLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public com.rcs.configuration.model.Configuration addConfiguration(
		long userId, long groupId, java.lang.String propertyname,
		java.lang.String propertyvalue)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.addConfiguration(userId, groupId,
			propertyname, propertyvalue);
	}

	@Override
	public java.util.List<com.rcs.configuration.model.Configuration> getConfigurationByPropertyName(
		java.lang.String propertyname, long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.getConfigurationByPropertyName(propertyname,
			groupId, companyId);
	}

	@Override
	public java.util.List<com.rcs.configuration.model.Configuration> getConfigurationByPropertyName(
		java.lang.String propertyname)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _configurationLocalService.getConfigurationByPropertyName(propertyname);
	}

	@Override
	public void deleteDetailedConfiguration()
		throws com.liferay.portal.kernel.exception.SystemException {
		_configurationLocalService.deleteDetailedConfiguration();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ConfigurationLocalService getWrappedConfigurationLocalService() {
		return _configurationLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedConfigurationLocalService(
		ConfigurationLocalService configurationLocalService) {
		_configurationLocalService = configurationLocalService;
	}

	@Override
	public ConfigurationLocalService getWrappedService() {
		return _configurationLocalService;
	}

	@Override
	public void setWrappedService(
		ConfigurationLocalService configurationLocalService) {
		_configurationLocalService = configurationLocalService;
	}

	private ConfigurationLocalService _configurationLocalService;
}