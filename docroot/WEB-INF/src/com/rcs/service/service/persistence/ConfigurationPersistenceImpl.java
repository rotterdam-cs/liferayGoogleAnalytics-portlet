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

package com.rcs.service.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.rcs.service.NoSuchConfigurationException;
import com.rcs.service.model.Configuration;
import com.rcs.service.model.impl.ConfigurationImpl;
import com.rcs.service.model.impl.ConfigurationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the configuration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author RCS - Pablo Rendon
 * @see ConfigurationPersistence
 * @see ConfigurationUtil
 * @generated
 */
public class ConfigurationPersistenceImpl extends BasePersistenceImpl<Configuration>
	implements ConfigurationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ConfigurationUtil} to access the configuration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ConfigurationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PROPERTYNAME =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBypropertyname",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBypropertyname",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			ConfigurationModelImpl.PROPERTYNAME_COLUMN_BITMASK |
			ConfigurationModelImpl.GROUPID_COLUMN_BITMASK |
			ConfigurationModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PROPERTYNAME = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBypropertyname",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the configuration in the entity cache if it is enabled.
	 *
	 * @param configuration the configuration
	 */
	public void cacheResult(Configuration configuration) {
		EntityCacheUtil.putResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationImpl.class, configuration.getPrimaryKey(),
			configuration);

		configuration.resetOriginalValues();
	}

	/**
	 * Caches the configurations in the entity cache if it is enabled.
	 *
	 * @param configurations the configurations
	 */
	public void cacheResult(List<Configuration> configurations) {
		for (Configuration configuration : configurations) {
			if (EntityCacheUtil.getResult(
						ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						ConfigurationImpl.class, configuration.getPrimaryKey()) == null) {
				cacheResult(configuration);
			}
			else {
				configuration.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all configurations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ConfigurationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ConfigurationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the configuration.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Configuration configuration) {
		EntityCacheUtil.removeResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationImpl.class, configuration.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Configuration> configurations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Configuration configuration : configurations) {
			EntityCacheUtil.removeResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				ConfigurationImpl.class, configuration.getPrimaryKey());
		}
	}

	/**
	 * Creates a new configuration with the primary key. Does not add the configuration to the database.
	 *
	 * @param configurationId the primary key for the new configuration
	 * @return the new configuration
	 */
	public Configuration create(long configurationId) {
		Configuration configuration = new ConfigurationImpl();

		configuration.setNew(true);
		configuration.setPrimaryKey(configurationId);

		return configuration;
	}

	/**
	 * Removes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param configurationId the primary key of the configuration
	 * @return the configuration that was removed
	 * @throws com.rcs.service.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration remove(long configurationId)
		throws NoSuchConfigurationException, SystemException {
		return remove(Long.valueOf(configurationId));
	}

	/**
	 * Removes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the configuration
	 * @return the configuration that was removed
	 * @throws com.rcs.service.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration remove(Serializable primaryKey)
		throws NoSuchConfigurationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Configuration configuration = (Configuration)session.get(ConfigurationImpl.class,
					primaryKey);

			if (configuration == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(configuration);
		}
		catch (NoSuchConfigurationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Configuration removeImpl(Configuration configuration)
		throws SystemException {
		configuration = toUnwrappedModel(configuration);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, configuration);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(configuration);

		return configuration;
	}

	@Override
	public Configuration updateImpl(
		com.rcs.service.model.Configuration configuration, boolean merge)
		throws SystemException {
		configuration = toUnwrappedModel(configuration);

		boolean isNew = configuration.isNew();

		ConfigurationModelImpl configurationModelImpl = (ConfigurationModelImpl)configuration;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, configuration, merge);

			configuration.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ConfigurationModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((configurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						configurationModelImpl.getOriginalPropertyname(),
						Long.valueOf(configurationModelImpl.getOriginalGroupId()),
						Long.valueOf(configurationModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PROPERTYNAME,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME,
					args);

				args = new Object[] {
						configurationModelImpl.getPropertyname(),
						Long.valueOf(configurationModelImpl.getGroupId()),
						Long.valueOf(configurationModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PROPERTYNAME,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME,
					args);
			}
		}

		EntityCacheUtil.putResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationImpl.class, configuration.getPrimaryKey(),
			configuration);

		return configuration;
	}

	protected Configuration toUnwrappedModel(Configuration configuration) {
		if (configuration instanceof ConfigurationImpl) {
			return configuration;
		}

		ConfigurationImpl configurationImpl = new ConfigurationImpl();

		configurationImpl.setNew(configuration.isNew());
		configurationImpl.setPrimaryKey(configuration.getPrimaryKey());

		configurationImpl.setConfigurationId(configuration.getConfigurationId());
		configurationImpl.setGroupId(configuration.getGroupId());
		configurationImpl.setCompanyId(configuration.getCompanyId());
		configurationImpl.setUserId(configuration.getUserId());
		configurationImpl.setUserName(configuration.getUserName());
		configurationImpl.setCreateDate(configuration.getCreateDate());
		configurationImpl.setModifiedDate(configuration.getModifiedDate());
		configurationImpl.setPropertyname(configuration.getPropertyname());
		configurationImpl.setPropertyvalue(configuration.getPropertyvalue());

		return configurationImpl;
	}

	/**
	 * Returns the configuration with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the configuration
	 * @return the configuration
	 * @throws com.liferay.portal.NoSuchModelException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the configuration with the primary key or throws a {@link com.rcs.service.NoSuchConfigurationException} if it could not be found.
	 *
	 * @param configurationId the primary key of the configuration
	 * @return the configuration
	 * @throws com.rcs.service.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration findByPrimaryKey(long configurationId)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = fetchByPrimaryKey(configurationId);

		if (configuration == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + configurationId);
			}

			throw new NoSuchConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				configurationId);
		}

		return configuration;
	}

	/**
	 * Returns the configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the configuration
	 * @return the configuration, or <code>null</code> if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param configurationId the primary key of the configuration
	 * @return the configuration, or <code>null</code> if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration fetchByPrimaryKey(long configurationId)
		throws SystemException {
		Configuration configuration = (Configuration)EntityCacheUtil.getResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				ConfigurationImpl.class, configurationId);

		if (configuration == _nullConfiguration) {
			return null;
		}

		if (configuration == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				configuration = (Configuration)session.get(ConfigurationImpl.class,
						Long.valueOf(configurationId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (configuration != null) {
					cacheResult(configuration);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						ConfigurationImpl.class, configurationId,
						_nullConfiguration);
				}

				closeSession(session);
			}
		}

		return configuration;
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
	public List<Configuration> findBypropertyname(String propertyname,
		long groupId, long companyId) throws SystemException {
		return findBypropertyname(propertyname, groupId, companyId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public List<Configuration> findBypropertyname(String propertyname,
		long groupId, long companyId, int start, int end)
		throws SystemException {
		return findBypropertyname(propertyname, groupId, companyId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public List<Configuration> findBypropertyname(String propertyname,
		long groupId, long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME;
			finderArgs = new Object[] { propertyname, groupId, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PROPERTYNAME;
			finderArgs = new Object[] {
					propertyname, groupId, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Configuration> list = (List<Configuration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CONFIGURATION_WHERE);

			if (propertyname == null) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1);
			}
			else {
				if (propertyname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_PROPERTYNAME_GROUPID_2);

			query.append(_FINDER_COLUMN_PROPERTYNAME_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (propertyname != null) {
					qPos.add(propertyname);
				}

				qPos.add(groupId);

				qPos.add(companyId);

				list = (List<Configuration>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param propertyname the propertyname
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching configuration
	 * @throws com.rcs.service.NoSuchConfigurationException if a matching configuration could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration findBypropertyname_First(String propertyname,
		long groupId, long companyId, OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		List<Configuration> list = findBypropertyname(propertyname, groupId,
				companyId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("propertyname=");
			msg.append(propertyname);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchConfigurationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param propertyname the propertyname
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching configuration
	 * @throws com.rcs.service.NoSuchConfigurationException if a matching configuration could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration findBypropertyname_Last(String propertyname,
		long groupId, long companyId, OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		int count = countBypropertyname(propertyname, groupId, companyId);

		List<Configuration> list = findBypropertyname(propertyname, groupId,
				companyId, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("propertyname=");
			msg.append(propertyname);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchConfigurationException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the configurations before and after the current configuration in the ordered set where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param configurationId the primary key of the current configuration
	 * @param propertyname the propertyname
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next configuration
	 * @throws com.rcs.service.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration[] findBypropertyname_PrevAndNext(
		long configurationId, String propertyname, long groupId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = findByPrimaryKey(configurationId);

		Session session = null;

		try {
			session = openSession();

			Configuration[] array = new ConfigurationImpl[3];

			array[0] = getBypropertyname_PrevAndNext(session, configuration,
					propertyname, groupId, companyId, orderByComparator, true);

			array[1] = configuration;

			array[2] = getBypropertyname_PrevAndNext(session, configuration,
					propertyname, groupId, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Configuration getBypropertyname_PrevAndNext(Session session,
		Configuration configuration, String propertyname, long groupId,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONFIGURATION_WHERE);

		if (propertyname == null) {
			query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1);
		}
		else {
			if (propertyname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2);
			}
		}

		query.append(_FINDER_COLUMN_PROPERTYNAME_GROUPID_2);

		query.append(_FINDER_COLUMN_PROPERTYNAME_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(ConfigurationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (propertyname != null) {
			qPos.add(propertyname);
		}

		qPos.add(groupId);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(configuration);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Configuration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the configurations.
	 *
	 * @return the configurations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Configuration> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<Configuration> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of configurations
	 * @param end the upper bound of the range of configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of configurations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Configuration> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Configuration> list = (List<Configuration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CONFIGURATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CONFIGURATION.concat(ConfigurationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param propertyname the propertyname
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBypropertyname(String propertyname, long groupId,
		long companyId) throws SystemException {
		for (Configuration configuration : findBypropertyname(propertyname,
				groupId, companyId)) {
			remove(configuration);
		}
	}

	/**
	 * Removes all the configurations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Configuration configuration : findAll()) {
			remove(configuration);
		}
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
	public int countBypropertyname(String propertyname, long groupId,
		long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { propertyname, groupId, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PROPERTYNAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CONFIGURATION_WHERE);

			if (propertyname == null) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1);
			}
			else {
				if (propertyname.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2);
				}
			}

			query.append(_FINDER_COLUMN_PROPERTYNAME_GROUPID_2);

			query.append(_FINDER_COLUMN_PROPERTYNAME_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (propertyname != null) {
					qPos.add(propertyname);
				}

				qPos.add(groupId);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PROPERTYNAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of configurations.
	 *
	 * @return the number of configurations
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CONFIGURATION);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the configuration persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.rcs.service.model.Configuration")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Configuration>> listenersList = new ArrayList<ModelListener<Configuration>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Configuration>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ConfigurationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ConfigurationPersistence.class)
	protected ConfigurationPersistence configurationPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_CONFIGURATION = "SELECT configuration FROM Configuration configuration";
	private static final String _SQL_SELECT_CONFIGURATION_WHERE = "SELECT configuration FROM Configuration configuration WHERE ";
	private static final String _SQL_COUNT_CONFIGURATION = "SELECT COUNT(configuration) FROM Configuration configuration";
	private static final String _SQL_COUNT_CONFIGURATION_WHERE = "SELECT COUNT(configuration) FROM Configuration configuration WHERE ";
	private static final String _FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1 = "configuration.propertyname IS NULL AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2 = "configuration.propertyname = ? AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3 = "(configuration.propertyname IS NULL OR configuration.propertyname = ?) AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAME_GROUPID_2 = "configuration.groupId = ? AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAME_COMPANYID_2 = "configuration.companyId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "configuration.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Configuration exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Configuration exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ConfigurationPersistenceImpl.class);
	private static Configuration _nullConfiguration = new ConfigurationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Configuration> toCacheModel() {
				return _nullConfigurationCacheModel;
			}
		};

	private static CacheModel<Configuration> _nullConfigurationCacheModel = new CacheModel<Configuration>() {
			public Configuration toEntityModel() {
				return _nullConfiguration;
			}
		};
}