package com.rcs.common;

public enum GoogleAnalyticsConfigurationEnum {
	
	CLIENT_ID("client_id"),
	APIKEY("api_key"),
	ACCOUNT_ID("account_id"),
	PROPERTY_ID("property_id"),
	PROFILE_ID("profile_id");
	
	private String key;

    private GoogleAnalyticsConfigurationEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
	
}
