package com.rcs.common;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;

public class PortalInstanceIdentifier {
	
	private Long groupId = null;
	private Long companyId = null;
	private Long userId = null;
	
	public long getGroupId() {
		
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	public long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	public void setCompanyIdByUserId(long userId){
		try {
			User user = UserUtil.findByPrimaryKey(userId);
			this.companyId = user.getCompanyId();
		} catch (NoSuchUserException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateParameters() {
		boolean result = false;
		if (this.groupId != null && this.companyId != null){
			result = true;
		}
		return result;
	}
	
	public boolean validateFullParameters() {
		boolean result = false;
		if (validateParameters() && this.userId != null){
			result = true;
		}
		return result;
	}
	
}
