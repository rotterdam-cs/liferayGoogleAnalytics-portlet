package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.rcs.common.ResourceBundleHelper;

public class MessagesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	List<MessageDTO> messages = new ArrayList<MessageDTO>();	
	
	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}

	public void addTranslatedError(String key, String value) {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setKey(key);
		messageDTO.setValue(value);
		this.messages.add(messageDTO);
	}
	
	public void addMessage(String key, Locale locale) {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setKey(key);
		messageDTO.setValue(ResourceBundleHelper.getKeyLocalizedValue(key, locale));
		this.messages.add(messageDTO);
	}
	
}
