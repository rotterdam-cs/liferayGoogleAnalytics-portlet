package com.rcs.googleanalytics.enums;

import java.util.Locale;

import com.google.gson.Gson;
import com.rcs.googleanalytics.dto.MessagesDTO;

public enum MessagesEnum {
	ERROR_PROCESSING_DATA("com.rcs.general.error.processing.data"),
	ERROR_UNAUTHORIZED_ACCESS("com.rcs.googleanalytics.error.unauthorized.access"),
	ERROR_TIMEOUT_WONG_PARAMETERS("com.rcs.googleanalytics.error.timeout.wrong.parameters"),
	ERROR_GENERAL("com.rcs.general.error"),
	ERROR_NO_ACCOUNT_FOUND("com.rcs.googleanalytics.error.no.accounts.found"),
	ERROR_QUERYING_ACCOUNTS("com.rcs.googleanalytics.error.querying.accounts"),
	ERROR_NO_WEBPROPERTIES_FOUND("com.rcs.googleanalytics.error.no.webproperties.found"),
	ERROR_QUERYING_WEBPROPERTIES("com.rcs.googleanalytics.error.querying.webproperties"),
	ERROR_NO_PROFILES_FOUND("com.rcs.googleanalytics.error.no.profiles.found"),
	ERROR_QUERYING_PROFILES("com.rcs.googleanalytics.error.querying.profiles"),
	MESSAGE_AUTHORIZED_ACCESS("com.rcs.googleanalytics.authorized.access");	
	
	private String key;

    private MessagesEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
    public static String getMessagesDTO(Locale locale) {
    	MessagesDTO messageDTO = new MessagesDTO();
    	for (MessagesEnum s : MessagesEnum.values()) {
    		messageDTO.addMessage(s.getKey(), locale);            
        }
    	Gson gson = new Gson();
	    return gson.toJson(messageDTO);
    }
}
