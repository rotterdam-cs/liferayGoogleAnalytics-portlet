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

package com.rcs.configuration.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.rcs.configuration.model.Configuration;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Configuration in entity cache.
 *
 * @author rotterdamcs
 * @see Configuration
 * @generated
 */
public class ConfigurationCacheModel implements CacheModel<Configuration>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{configurationId=");
		sb.append(configurationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", propertyname=");
		sb.append(propertyname);
		sb.append(", propertyvalue=");
		sb.append(propertyvalue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Configuration toEntityModel() {
		ConfigurationImpl configurationImpl = new ConfigurationImpl();

		configurationImpl.setConfigurationId(configurationId);
		configurationImpl.setGroupId(groupId);
		configurationImpl.setCompanyId(companyId);
		configurationImpl.setUserId(userId);

		if (userName == null) {
			configurationImpl.setUserName(StringPool.BLANK);
		}
		else {
			configurationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			configurationImpl.setCreateDate(null);
		}
		else {
			configurationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			configurationImpl.setModifiedDate(null);
		}
		else {
			configurationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (propertyname == null) {
			configurationImpl.setPropertyname(StringPool.BLANK);
		}
		else {
			configurationImpl.setPropertyname(propertyname);
		}

		if (propertyvalue == null) {
			configurationImpl.setPropertyvalue(StringPool.BLANK);
		}
		else {
			configurationImpl.setPropertyvalue(propertyvalue);
		}

		configurationImpl.resetOriginalValues();

		return configurationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		configurationId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		propertyname = objectInput.readUTF();
		propertyvalue = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(configurationId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (propertyname == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(propertyname);
		}

		if (propertyvalue == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(propertyvalue);
		}
	}

	public long configurationId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String propertyname;
	public String propertyvalue;
}