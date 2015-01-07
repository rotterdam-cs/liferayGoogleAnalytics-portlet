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

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.rcs.configuration.model.Configuration;
import com.rcs.configuration.service.ConfigurationLocalServiceUtil;

/**
 * @author rotterdamcs
 * @generated
 */
public abstract class ConfigurationActionableDynamicQuery
	extends BaseActionableDynamicQuery {
	public ConfigurationActionableDynamicQuery() throws SystemException {
		setBaseLocalService(ConfigurationLocalServiceUtil.getService());
		setClass(Configuration.class);

		setClassLoader(com.rcs.configuration.service.ClpSerializer.class.getClassLoader());

		setPrimaryKeyPropertyName("configurationId");
	}
}