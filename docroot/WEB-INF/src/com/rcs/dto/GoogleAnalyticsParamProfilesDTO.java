package com.rcs.dto;

import java.io.Serializable;

public class GoogleAnalyticsParamProfilesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private String html;
	private boolean selected = false;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}	
	
}
