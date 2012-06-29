package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoogleAnalyticsParamPropertiesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private String html;
	private boolean selected = false;
	private List <GoogleAnalyticsParamProfilesDTO> profiles = new ArrayList<GoogleAnalyticsParamProfilesDTO>();
	
	
	public List<GoogleAnalyticsParamProfilesDTO> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<GoogleAnalyticsParamProfilesDTO> profiles) {
		this.profiles = profiles;
	}
	public void addProfiles(GoogleAnalyticsParamProfilesDTO profile) {
		this.profiles.add(profile);
	}	
	
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