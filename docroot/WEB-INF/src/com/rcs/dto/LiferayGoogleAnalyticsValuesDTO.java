package com.rcs.dto;

import java.io.Serializable;

public class LiferayGoogleAnalyticsValuesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String title;
	private LiferayGoogleAnalyticsValueDTO cur;
	private LiferayGoogleAnalyticsValueDTO prev;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LiferayGoogleAnalyticsValueDTO getCur() {
		return cur;
	}
	public void setCur(LiferayGoogleAnalyticsValueDTO cur) {
		this.cur = cur;
	}
	public LiferayGoogleAnalyticsValueDTO getPrev() {
		return prev;
	}
	public void setPrev(LiferayGoogleAnalyticsValueDTO prev) {
		this.prev = prev;
	}
	
	

}
