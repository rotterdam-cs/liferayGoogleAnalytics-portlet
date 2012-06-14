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

package com.rcs.service.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the configuration local service. This utility wraps {@link com.rcs.service.service.impl.ConfigurationLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author RCS - Pablo Rendon
 * @see ConfigurationLocalService
 * @see com.rcs.service.service.base.ConfigurationLocalServiceBaseImpl
 * @see com.rcs.service.service.impl.ConfigurationLocalServiceImpl
 * @generated
 */
public class ConfigurationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.rcs.service.service.impl.ConfigurationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the configuration to the database. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @return the configuration that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.service.model.Configuration addConfiguration(
		com.rcs.service.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addConfiguration(configuration);
	}

	/**
	* Creates a new configuration with the primary key. Does not add the configuration to the database.
	*
	* @param configurationId the primary key for the new configuration
	* @return the new configuration
	*/
	public static com.rcs.service.model.Configuration createConfiguration(
		long configurationId) {
		return getService().createConfiguration(configurationId);
	}

	/**
	* Deletes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param configurationId the primary key of the configuration
	* @throws PortalException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteConfiguration(long configurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteConfiguration(configurationId);
	}

	/**
	* Deletes the configuration from the database. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteConfiguration(
		com.rcs.service.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteConfiguration(configuration);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.rcs.service.model.Configuration fetchConfiguration(
		long configurationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchConfiguration(configurationId);
	}

	/**
	* Returns the configuration with the primary key.
	*
	* @param configurationId the primary key of the configuration
	* @return the configuration
	* @throws PortalException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.service.model.Configuration getConfiguration(
		long configurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getConfiguration(configurationId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the configurations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @return the range of configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.service.model.Configuration> getConfigurations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getConfigurations(start, end);
	}

	/**
	* Returns the number of configurations.
	*
	* @return the number of configurations
	* @throws SystemException if a system exception occurred
	*/
	public static int getConfigurationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getConfigurationsCount();
	}

	/**
	* Updates the configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @return the configuration that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.service.model.Configuration updateConfiguration(
		com.rcs.service.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateConfiguration(configuration);
	}

	/**
	* Updates the configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param configuration the configuration
	* @param merge whether to merge the configuration with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the configuration that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.service.model.Configuration updateConfiguration(
		com.rcs.service.model.Configuration configuration, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateConfiguration(configuration, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.rcs.service.model.Configuration addConfiguration(
		long userId, long groupId, java.lang.String propertyname,
		java.lang.String propertyvalue)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addConfiguration(userId, groupId, propertyname,
			propertyvalue);
	}

	public static java.util.List<com.rcs.service.model.Configuration> getConfigurationByPropertyName(
		java.lang.String propertyname, long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getConfigurationByPropertyName(propertyname, groupId,
			companyId);
	}

	public static void clearService() {
		_service = null;
	}

	public static ConfigurationLocalService getService() {
		if (_service == null) {
			Object object = PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ConfigurationLocalService.class.getName());
			ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					"portletClassLoader");

			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(object,
					ConfigurationLocalService.class.getName(),
					portletClassLoader);

			_service = new ConfigurationLocalServiceClp(classLoaderProxy);

			ClpSerializer.setClassLoader(portletClassLoader);

			ReferenceRegistry.registerReference(ConfigurationLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ConfigurationLocalService.class);
		}

		return _service;
	}

	public void setService(ConfigurationLocalService service) {
		MethodCache.remove(ConfigurationLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ConfigurationLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ConfigurationLocalService.class);
	}

	private static ConfigurationLocalService _service;
}