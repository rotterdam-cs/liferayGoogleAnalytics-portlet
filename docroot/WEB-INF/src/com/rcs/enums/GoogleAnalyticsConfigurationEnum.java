package com.rcs.enums;

public enum GoogleAnalyticsConfigurationEnum {
	
	CLIENT_ID("client_id"),
	APIKEY("api_key"),//@@TODO Not used?
	CLIENT_SECRET("client_secret"),
	ACCOUNT_ID("account_id"),
	PROPERTY_ID("property_id"),
	PROFILE_ID("profile_id"),
	TOKEN("token"),
	REFRESHTOKEN("refreshtoken");
	
	private String key;

    private GoogleAnalyticsConfigurationEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
	
}
