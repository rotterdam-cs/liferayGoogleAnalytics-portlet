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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ConfigurationService}.
 * </p>
 *
 * @author    RCS - Pablo Rendon
 * @see       ConfigurationService
 * @generated
 */
public class ConfigurationServiceWrapper implements ConfigurationService,
	ServiceWrapper<ConfigurationService> {
	public ConfigurationServiceWrapper(
		ConfigurationService configurationService) {
		_configurationService = configurationService;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ConfigurationService getWrappedConfigurationService() {
		return _configurationService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedConfigurationService(
		ConfigurationService configurationService) {
		_configurationService = configurationService;
	}

	public ConfigurationService getWrappedService() {
		return _configurationService;
	}

	public void setWrappedService(ConfigurationService configurationService) {
		_configurationService = configurationService;
	}

	private ConfigurationService _configurationService;
}