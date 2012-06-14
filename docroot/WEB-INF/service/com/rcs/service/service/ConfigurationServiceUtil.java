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
 * The utility for the configuration remote service. This utility wraps {@link com.rcs.service.service.impl.ConfigurationServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author RCS - Pablo Rendon
 * @see ConfigurationService
 * @see com.rcs.service.service.base.ConfigurationServiceBaseImpl
 * @see com.rcs.service.service.impl.ConfigurationServiceImpl
 * @generated
 */
public class ConfigurationServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.rcs.service.service.impl.ConfigurationServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void clearService() {
		_service = null;
	}

	public static ConfigurationService getService() {
		if (_service == null) {
			Object object = PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ConfigurationService.class.getName());
			ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					"portletClassLoader");

			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(object,
					ConfigurationService.class.getName(), portletClassLoader);

			_service = new ConfigurationServiceClp(classLoaderProxy);

			ClpSerializer.setClassLoader(portletClassLoader);

			ReferenceRegistry.registerReference(ConfigurationServiceUtil.class,
				"_service");
			MethodCache.remove(ConfigurationService.class);
		}

		return _service;
	}

	public void setService(ConfigurationService service) {
		MethodCache.remove(ConfigurationService.class);

		_service = service;

		ReferenceRegistry.registerReference(ConfigurationServiceUtil.class,
			"_service");
		MethodCache.remove(ConfigurationService.class);
	}

	private static ConfigurationService _service;
}