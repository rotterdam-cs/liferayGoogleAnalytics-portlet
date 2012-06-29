package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoogleAnalyticsParamAccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private String html;
	private boolean selected = false;
	private List <GoogleAnalyticsParamPropertiesDTO> webProperties = new ArrayList<GoogleAnalyticsParamPropertiesDTO>();
	
	
	public List<GoogleAnalyticsParamPropertiesDTO> getWebProperties() {
		return webProperties;
	}
	public void setWebProperties(List<GoogleAnalyticsParamPropertiesDTO> webProperties) {
		this.webProperties = webProperties;
	}
	public void addWebProperties(GoogleAnalyticsParamPropertiesDTO webProperty) {
		this.webProperties.add(webProperty);
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