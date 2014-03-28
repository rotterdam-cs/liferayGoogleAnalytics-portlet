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

package com.rcs.configuration.service.persistence;

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
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.rcs.configuration.NoSuchConfigurationException;
import com.rcs.configuration.model.Configuration;
import com.rcs.configuration.model.impl.ConfigurationImpl;
import com.rcs.configuration.model.impl.ConfigurationModelImpl;

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
 * @author rotterdamcs
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPropertynameGroupIdCompanyId",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPropertynameGroupIdCompanyId",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			ConfigurationModelImpl.PROPERTYNAME_COLUMN_BITMASK |
			ConfigurationModelImpl.GROUPID_COLUMN_BITMASK |
			ConfigurationModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PROPERTYNAMEGROUPIDCOMPANYID =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPropertynameGroupIdCompanyId",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63;.
	 *
	 * @param propertyname the propertyname
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Configuration> findByPropertynameGroupIdCompanyId(
		String propertyname, long groupId, long companyId)
		throws SystemException {
		return findByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<Configuration> findByPropertynameGroupIdCompanyId(
		String propertyname, long groupId, long companyId, int start, int end)
		throws SystemException {
		return findByPropertynameGroupIdCompanyId(propertyname, groupId,
			companyId, start, end, null);
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
	@Override
	public List<Configuration> findByPropertynameGroupIdCompanyId(
		String propertyname, long groupId, long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID;
			finderArgs = new Object[] { propertyname, groupId, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID;
			finderArgs = new Object[] {
					propertyname, groupId, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Configuration> list = (List<Configuration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Configuration configuration : list) {
				if (!Validator.equals(propertyname,
							configuration.getPropertyname()) ||
						(groupId != configuration.getGroupId()) ||
						(companyId != configuration.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

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

			boolean bindPropertyname = false;

			if (propertyname == null) {
				query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_1);
			}
			else if (propertyname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_3);
			}
			else {
				bindPropertyname = true;

				query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_2);
			}

			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_GROUPID_2);

			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPropertyname) {
					qPos.add(propertyname);
				}

				qPos.add(groupId);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Configuration>(list);
				}
				else {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public Configuration findByPropertynameGroupIdCompanyId_First(
		String propertyname, long groupId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = fetchByPropertynameGroupIdCompanyId_First(propertyname,
				groupId, companyId, orderByComparator);

		if (configuration != null) {
			return configuration;
		}

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
	@Override
	public Configuration fetchByPropertynameGroupIdCompanyId_First(
		String propertyname, long groupId, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Configuration> list = findByPropertynameGroupIdCompanyId(propertyname,
				groupId, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public Configuration findByPropertynameGroupIdCompanyId_Last(
		String propertyname, long groupId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = fetchByPropertynameGroupIdCompanyId_Last(propertyname,
				groupId, companyId, orderByComparator);

		if (configuration != null) {
			return configuration;
		}

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
	@Override
	public Configuration fetchByPropertynameGroupIdCompanyId_Last(
		String propertyname, long groupId, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPropertynameGroupIdCompanyId(propertyname, groupId,
				companyId);

		if (count == 0) {
			return null;
		}

		List<Configuration> list = findByPropertynameGroupIdCompanyId(propertyname,
				groupId, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public Configuration[] findByPropertynameGroupIdCompanyId_PrevAndNext(
		long configurationId, String propertyname, long groupId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = findByPrimaryKey(configurationId);

		Session session = null;

		try {
			session = openSession();

			Configuration[] array = new ConfigurationImpl[3];

			array[0] = getByPropertynameGroupIdCompanyId_PrevAndNext(session,
					configuration, propertyname, groupId, companyId,
					orderByComparator, true);

			array[1] = configuration;

			array[2] = getByPropertynameGroupIdCompanyId_PrevAndNext(session,
					configuration, propertyname, groupId, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Configuration getByPropertynameGroupIdCompanyId_PrevAndNext(
		Session session, Configuration configuration, String propertyname,
		long groupId, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONFIGURATION_WHERE);

		boolean bindPropertyname = false;

		if (propertyname == null) {
			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_1);
		}
		else if (propertyname.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_3);
		}
		else {
			bindPropertyname = true;

			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_2);
		}

		query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_GROUPID_2);

		query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_COMPANYID_2);

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

		if (bindPropertyname) {
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
	 * Removes all the configurations where propertyname = &#63; and groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param propertyname the propertyname
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPropertynameGroupIdCompanyId(String propertyname,
		long groupId, long companyId) throws SystemException {
		for (Configuration configuration : findByPropertynameGroupIdCompanyId(
				propertyname, groupId, companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
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
	@Override
	public int countByPropertynameGroupIdCompanyId(String propertyname,
		long groupId, long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PROPERTYNAMEGROUPIDCOMPANYID;

		Object[] finderArgs = new Object[] { propertyname, groupId, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CONFIGURATION_WHERE);

			boolean bindPropertyname = false;

			if (propertyname == null) {
				query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_1);
			}
			else if (propertyname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_3);
			}
			else {
				bindPropertyname = true;

				query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_2);
			}

			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_GROUPID_2);

			query.append(_FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPropertyname) {
					qPos.add(propertyname);
				}

				qPos.add(groupId);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_1 =
		"configuration.propertyname IS NULL AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_2 =
		"configuration.propertyname = ? AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_PROPERTYNAME_3 =
		"(configuration.propertyname IS NULL OR configuration.propertyname = '') AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_GROUPID_2 =
		"configuration.groupId = ? AND ";
	private static final String _FINDER_COLUMN_PROPERTYNAMEGROUPIDCOMPANYID_COMPANYID_2 =
		"configuration.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PROPERTYNAME =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPropertyname",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME =
		new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED,
			ConfigurationImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPropertyname", new String[] { String.class.getName() },
			ConfigurationModelImpl.PROPERTYNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PROPERTYNAME = new FinderPath(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
			ConfigurationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPropertyname",
			new String[] { String.class.getName() });

	/**
	 * Returns all the configurations where propertyname = &#63;.
	 *
	 * @param propertyname the propertyname
	 * @return the matching configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Configuration> findByPropertyname(String propertyname)
		throws SystemException {
		return findByPropertyname(propertyname, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<Configuration> findByPropertyname(String propertyname,
		int start, int end) throws SystemException {
		return findByPropertyname(propertyname, start, end, null);
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
	@Override
	public List<Configuration> findByPropertyname(String propertyname,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME;
			finderArgs = new Object[] { propertyname };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PROPERTYNAME;
			finderArgs = new Object[] {
					propertyname,
					
					start, end, orderByComparator
				};
		}

		List<Configuration> list = (List<Configuration>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Configuration configuration : list) {
				if (!Validator.equals(propertyname,
							configuration.getPropertyname())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CONFIGURATION_WHERE);

			boolean bindPropertyname = false;

			if (propertyname == null) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1);
			}
			else if (propertyname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3);
			}
			else {
				bindPropertyname = true;

				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ConfigurationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPropertyname) {
					qPos.add(propertyname);
				}

				if (!pagination) {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Configuration>(list);
				}
				else {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public Configuration findByPropertyname_First(String propertyname,
		OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = fetchByPropertyname_First(propertyname,
				orderByComparator);

		if (configuration != null) {
			return configuration;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("propertyname=");
		msg.append(propertyname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchConfigurationException(msg.toString());
	}

	/**
	 * Returns the first configuration in the ordered set where propertyname = &#63;.
	 *
	 * @param propertyname the propertyname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching configuration, or <code>null</code> if a matching configuration could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration fetchByPropertyname_First(String propertyname,
		OrderByComparator orderByComparator) throws SystemException {
		List<Configuration> list = findByPropertyname(propertyname, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public Configuration findByPropertyname_Last(String propertyname,
		OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = fetchByPropertyname_Last(propertyname,
				orderByComparator);

		if (configuration != null) {
			return configuration;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("propertyname=");
		msg.append(propertyname);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchConfigurationException(msg.toString());
	}

	/**
	 * Returns the last configuration in the ordered set where propertyname = &#63;.
	 *
	 * @param propertyname the propertyname
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching configuration, or <code>null</code> if a matching configuration could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration fetchByPropertyname_Last(String propertyname,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPropertyname(propertyname);

		if (count == 0) {
			return null;
		}

		List<Configuration> list = findByPropertyname(propertyname, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public Configuration[] findByPropertyname_PrevAndNext(
		long configurationId, String propertyname,
		OrderByComparator orderByComparator)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = findByPrimaryKey(configurationId);

		Session session = null;

		try {
			session = openSession();

			Configuration[] array = new ConfigurationImpl[3];

			array[0] = getByPropertyname_PrevAndNext(session, configuration,
					propertyname, orderByComparator, true);

			array[1] = configuration;

			array[2] = getByPropertyname_PrevAndNext(session, configuration,
					propertyname, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Configuration getByPropertyname_PrevAndNext(Session session,
		Configuration configuration, String propertyname,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONFIGURATION_WHERE);

		boolean bindPropertyname = false;

		if (propertyname == null) {
			query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1);
		}
		else if (propertyname.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3);
		}
		else {
			bindPropertyname = true;

			query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2);
		}

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

		if (bindPropertyname) {
			qPos.add(propertyname);
		}

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
	 * Removes all the configurations where propertyname = &#63; from the database.
	 *
	 * @param propertyname the propertyname
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPropertyname(String propertyname)
		throws SystemException {
		for (Configuration configuration : findByPropertyname(propertyname,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(configuration);
		}
	}

	/**
	 * Returns the number of configurations where propertyname = &#63;.
	 *
	 * @param propertyname the propertyname
	 * @return the number of matching configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPropertyname(String propertyname)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PROPERTYNAME;

		Object[] finderArgs = new Object[] { propertyname };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONFIGURATION_WHERE);

			boolean bindPropertyname = false;

			if (propertyname == null) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1);
			}
			else if (propertyname.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3);
			}
			else {
				bindPropertyname = true;

				query.append(_FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPropertyname) {
					qPos.add(propertyname);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_1 = "configuration.propertyname IS NULL";
	private static final String _FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_2 = "configuration.propertyname = ?";
	private static final String _FINDER_COLUMN_PROPERTYNAME_PROPERTYNAME_3 = "(configuration.propertyname IS NULL OR configuration.propertyname = '')";

	public ConfigurationPersistenceImpl() {
		setModelClass(Configuration.class);
	}

	/**
	 * Caches the configuration in the entity cache if it is enabled.
	 *
	 * @param configuration the configuration
	 */
	@Override
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
	@Override
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
	@Override
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
	 * @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration remove(long configurationId)
		throws NoSuchConfigurationException, SystemException {
		return remove((Serializable)configurationId);
	}

	/**
	 * Removes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the configuration
	 * @return the configuration that was removed
	 * @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
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

			if (!session.contains(configuration)) {
				configuration = (Configuration)session.get(ConfigurationImpl.class,
						configuration.getPrimaryKeyObj());
			}

			if (configuration != null) {
				session.delete(configuration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (configuration != null) {
			clearCache(configuration);
		}

		return configuration;
	}

	@Override
	public Configuration updateImpl(
		com.rcs.configuration.model.Configuration configuration)
		throws SystemException {
		configuration = toUnwrappedModel(configuration);

		boolean isNew = configuration.isNew();

		ConfigurationModelImpl configurationModelImpl = (ConfigurationModelImpl)configuration;

		Session session = null;

		try {
			session = openSession();

			if (configuration.isNew()) {
				session.save(configuration);

				configuration.setNew(false);
			}
			else {
				session.merge(configuration);
			}
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
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						configurationModelImpl.getOriginalPropertyname(),
						configurationModelImpl.getOriginalGroupId(),
						configurationModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PROPERTYNAMEGROUPIDCOMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID,
					args);

				args = new Object[] {
						configurationModelImpl.getPropertyname(),
						configurationModelImpl.getGroupId(),
						configurationModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PROPERTYNAMEGROUPIDCOMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAMEGROUPIDCOMPANYID,
					args);
			}

			if ((configurationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						configurationModelImpl.getOriginalPropertyname()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PROPERTYNAME,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PROPERTYNAME,
					args);

				args = new Object[] { configurationModelImpl.getPropertyname() };

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
	 * @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchConfigurationException, SystemException {
		Configuration configuration = fetchByPrimaryKey(primaryKey);

		if (configuration == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchConfigurationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return configuration;
	}

	/**
	 * Returns the configuration with the primary key or throws a {@link com.rcs.configuration.NoSuchConfigurationException} if it could not be found.
	 *
	 * @param configurationId the primary key of the configuration
	 * @return the configuration
	 * @throws com.rcs.configuration.NoSuchConfigurationException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration findByPrimaryKey(long configurationId)
		throws NoSuchConfigurationException, SystemException {
		return findByPrimaryKey((Serializable)configurationId);
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
		Configuration configuration = (Configuration)EntityCacheUtil.getResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
				ConfigurationImpl.class, primaryKey);

		if (configuration == _nullConfiguration) {
			return null;
		}

		if (configuration == null) {
			Session session = null;

			try {
				session = openSession();

				configuration = (Configuration)session.get(ConfigurationImpl.class,
						primaryKey);

				if (configuration != null) {
					cacheResult(configuration);
				}
				else {
					EntityCacheUtil.putResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
						ConfigurationImpl.class, primaryKey, _nullConfiguration);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ConfigurationModelImpl.ENTITY_CACHE_ENABLED,
					ConfigurationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return configuration;
	}

	/**
	 * Returns the configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param configurationId the primary key of the configuration
	 * @return the configuration, or <code>null</code> if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Configuration fetchByPrimaryKey(long configurationId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)configurationId);
	}

	/**
	 * Returns all the configurations.
	 *
	 * @return the configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Configuration> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<Configuration> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	@Override
	public List<Configuration> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
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
				sql = _SQL_SELECT_CONFIGURATION;

				if (pagination) {
					sql = sql.concat(ConfigurationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Configuration>(list);
				}
				else {
					list = (List<Configuration>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the configurations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Configuration configuration : findAll()) {
			remove(configuration);
		}
	}

	/**
	 * Returns the number of configurations.
	 *
	 * @return the number of configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CONFIGURATION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
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
						"value.object.listener.com.rcs.configuration.model.Configuration")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Configuration>> listenersList = new ArrayList<ModelListener<Configuration>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Configuration>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
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
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_CONFIGURATION = "SELECT configuration FROM Configuration configuration";
	private static final String _SQL_SELECT_CONFIGURATION_WHERE = "SELECT configuration FROM Configuration configuration WHERE ";
	private static final String _SQL_COUNT_CONFIGURATION = "SELECT COUNT(configuration) FROM Configuration configuration";
	private static final String _SQL_COUNT_CONFIGURATION_WHERE = "SELECT COUNT(configuration) FROM Configuration configuration WHERE ";
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
			@Override
			public Configuration toEntityModel() {
				return _nullConfiguration;
			}
		};
}