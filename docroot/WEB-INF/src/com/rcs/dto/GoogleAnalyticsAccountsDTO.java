package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoogleAnalyticsAccountsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List <GoogleAnalyticsParamAccountDTO> accounts = new ArrayList<GoogleAnalyticsParamAccountDTO>();
	boolean isSuccess = false;
	String message = "";
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public List<GoogleAnalyticsParamAccountDTO> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<GoogleAnalyticsParamAccountDTO> accounts) {
		this.accounts = accounts;
	}
	public void addAccounts(GoogleAnalyticsParamAccountDTO account) {
		this.accounts.add(account);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void appendMessage(String message) {
		this.message += "<br />" + message;
	}
	
		
}