package com.github.gwateke.context;

import java.util.MissingResourceException;


public class RelativeMessageSource extends AbstractMessageSource {

	private final MessageSource parent;
	private final String keyPrefix;
		
	
	public RelativeMessageSource(MessageSource parent, String keyPrefix) {
		this.parent = parent;
		this.keyPrefix = keyPrefix;
	}

	
	public String getKeyPrefix() {
		return keyPrefix;
	}
	

	@Override
	public String getRequiredMessage(String key) {
		try {
			return parent.getRequiredMessage(keyPrefix + key);
		} 
		catch (MissingResourceException e) {
			return parent.getRequiredMessage(key);
		}
	}

}
