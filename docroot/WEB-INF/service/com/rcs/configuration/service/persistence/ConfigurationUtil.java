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

package com.rcs.configuration.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.rcs.configuration.model.Configuration;

import java.util.List;

/**
 * The persistence utility for the configuration service. This utility wraps {@link ConfigurationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author rotterdamcs
 * @see ConfigurationPersistence
 * @see ConfigurationPersistenceImpl
 * @generated
 */
public class ConfigurationUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Configuration configuration) {
		getPersistence().clearCache(configuration);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Configuration> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Configuration> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Configuration> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Configuration update(Configuration configuration)
		throws SystemException {
		return getPersistence().update(configuration);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Configuration update(Configuration configuration,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(configuration, serviceContext);
	}

	/**
	* Returns all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @return the matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findByPropertynameGroupIdCompanyId(
		java.lang.String propertyname, long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId);
	}

	/**
	* Returns a range of all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @return the range of matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findByPropertynameGroupIdCompanyId(
		java.lang.String propertyname, long groupId, long companyId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId, start, end);
	}

	/**
	* Returns an ordered range of all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findByPropertynameGroupIdCompanyId(
		java.lang.String propertyname, long groupId, long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration findByPropertynameGroupIdCompanyId_First(
		java.lang.String propertyname, long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence()
				   .findByPropertynameGroupIdCompanyId_First(propertyname,
			groupId, companyId, orderByComparator);
	}

	/**
	* Returns the first configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching configuration, or <code>null</code> if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration fetchByPropertynameGroupIdCompanyId_First(
		java.lang.String propertyname, long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPropertynameGroupIdCompanyId_First(propertyname,
			groupId, companyId, orderByComparator);
	}

	/**
	* Returns the last configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration findByPropertynameGroupIdCompanyId_Last(
		java.lang.String propertyname, long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence()
				   .findByPropertynameGroupIdCompanyId_Last(propertyname,
			groupId, companyId, orderByComparator);
	}

	/**
	* Returns the last configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching configuration, or <code>null</code> if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration fetchByPropertynameGroupIdCompanyId_Last(
		java.lang.String propertyname, long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPropertynameGroupIdCompanyId_Last(propertyname,
			groupId, companyId, orderByComparator);
	}

	/**
	* Returns the configurations before and after the current configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param configurationId the primary key of the current configuration
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration[] findByPropertynameGroupIdCompanyId_PrevAndNext(
		long configurationId, java.lang.String propertyname, long groupId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence()
				   .findByPropertynameGroupIdCompanyId_PrevAndNext(configurationId,
			propertyname, groupId, companyId, orderByComparator);
	}

	/**
	* Removes all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63; from the database.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPropertynameGroupIdCompanyId(
		java.lang.String propertyname, long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId);
	}

	/**
	* Returns the number of configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	*
	* @param propertyname the propertyname
	* @param groupId the group ID
	* @param companyId the company ID
	* @return the number of matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPropertynameGroupIdCompanyId(
		java.lang.String propertyname, long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId);
	}

	/**
	* Returns all the configurations where propertyname = &#63;.
	*
	* @param propertyname the propertyname
	* @return the matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findByPropertyname(
		java.lang.String propertyname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPropertyname(propertyname);
	}

	/**
	* Returns a range of all the configurations where propertyname = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param propertyname the propertyname
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @return the range of matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findByPropertyname(
		java.lang.String propertyname, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPropertyname(propertyname, start, end);
	}

	/**
	* Returns an ordered range of all the configurations where propertyname = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param propertyname the propertyname
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findByPropertyname(
		java.lang.String propertyname, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPropertyname(propertyname, start, end,
			orderByComparator);
	}

	/**
	* Returns the first configuration in the ordered set where propertyname = &#63;.
	*
	* @param propertyname the propertyname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration findByPropertyname_First(
		java.lang.String propertyname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence()
				   .findByPropertyname_First(propertyname, orderByComparator);
	}

	/**
	* Returns the first configuration in the ordered set where propertyname = &#63;.
	*
	* @param propertyname the propertyname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching configuration, or <code>null</code> if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration fetchByPropertyname_First(
		java.lang.String propertyname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPropertyname_First(propertyname, orderByComparator);
	}

	/**
	* Returns the last configuration in the ordered set where propertyname = &#63;.
	*
	* @param propertyname the propertyname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration findByPropertyname_Last(
		java.lang.String propertyname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence()
				   .findByPropertyname_Last(propertyname, orderByComparator);
	}

	/**
	* Returns the last configuration in the ordered set where propertyname = &#63;.
	*
	* @param propertyname the propertyname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching configuration, or <code>null</code> if a matching configuration could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration fetchByPropertyname_Last(
		java.lang.String propertyname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPropertyname_Last(propertyname, orderByComparator);
	}

	/**
	* Returns the configurations before and after the current configuration in the ordered set where propertyname = &#63;.
	*
	* @param configurationId the primary key of the current configuration
	* @param propertyname the propertyname
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration[] findByPropertyname_PrevAndNext(
		long configurationId, java.lang.String propertyname,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence()
				   .findByPropertyname_PrevAndNext(configurationId,
			propertyname, orderByComparator);
	}

	/**
	* Removes all the configurations where propertyname = &#63; from the database.
	*
	* @param propertyname the propertyname
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPropertyname(java.lang.String propertyname)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPropertyname(propertyname);
	}

	/**
	* Returns the number of configurations where propertyname = &#63;.
	*
	* @param propertyname the propertyname
	* @return the number of matching configurations
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPropertyname(java.lang.String propertyname)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPropertyname(propertyname);
	}

	/**
	* Caches the configuration in the entity cache if it is enabled.
	*
	* @param configuration the configuration
	*/
	public static void cacheResult(
		com.rcs.configuration.model.Configuration configuration) {
		getPersistence().cacheResult(configuration);
	}

	/**
	* Caches the configurations in the entity cache if it is enabled.
	*
	* @param configurations the configurations
	*/
	public static void cacheResult(
		java.util.List<com.rcs.configuration.model.Configuration> configurations) {
		getPersistence().cacheResult(configurations);
	}

	/**
	* Creates a new configuration with the primary key. Does not add the configuration to the database.
	*
	* @param configurationId the primary key for the new configuration
	* @return the new configuration
	*/
	public static com.rcs.configuration.model.Configuration create(
		long configurationId) {
		return getPersistence().create(configurationId);
	}

	/**
	* Removes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param configurationId the primary key of the configuration
	* @return the configuration that was removed
	* @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration remove(
		long configurationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence().remove(configurationId);
	}

	public static com.rcs.configuration.model.Configuration updateImpl(
		com.rcs.configuration.model.Configuration configuration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(configuration);
	}

	/**
	* Returns the configuration with the primary key or throws a {@link com.rcs.configuration.NoSuchConfigurationException} if it could not be found.
	*
	* @param configurationId the primary key of the configuration
	* @return the configuration
	* @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration findByPrimaryKey(
		long configurationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.rcs.configuration.NoSuchConfigurationException {
		return getPersistence().findByPrimaryKey(configurationId);
	}

	/**
	* Returns the configuration with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param configurationId the primary key of the configuration
	* @return the configuration, or <code>null</code> if a configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.rcs.configuration.model.Configuration fetchByPrimaryKey(
		long configurationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(configurationId);
	}

	/**
	* Returns all the configurations.
	*
	* @return the configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.rcs.configuration.model.Configuration> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the configurations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rcs.configuration.model.impl.ConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of configurations
	* @param end the upper bound of the range of configurations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.rcs.configuration.model.Configuration> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the configurations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of configurations.
	*
	* @return the number of configurations
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ConfigurationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ConfigurationPersistence)PortletBeanLocatorUtil.locate(com.rcs.configuration.service.ClpSerializer.getServletContextName(),
					ConfigurationPersistence.class.getName());

			ReferenceRegistry.registerReference(ConfigurationUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ConfigurationPersistence persistence) {
	}

	private static ConfigurationPersistence _persistence;
}