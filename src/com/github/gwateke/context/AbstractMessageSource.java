package com.github.gwateke.context;

import java.util.MissingResourceException;

import com.github.gwateke.text.MessageFormat;


public abstract class AbstractMessageSource implements MessageSource {

	public String getMessage(String key, Object... params) {
		return getMessage( new String[] {key}, params );
	}

	
	public String getMessage(String[] keys, Object... params) {
		for (String key : keys) {
			try {
				final String message = getRequiredMessage(key);
				return (params != null && params.length > 0) ? 
						MessageFormat.format( message, params ) : message;
			}
			catch (MissingResourceException e) {
				continue;
			}
		}
		return '*' + keys[ keys.length - 1 ] + '*';
	}

	
	public String getMessage(MessageSourceResolvable resolvable) {
		return getMessage( resolvable.getCodes(), resolvable.getArguments() );
	}
	
	
	public abstract String getRequiredMessage(String key);

	
	public MessageSource getMessageSource(String keyPrefix) {
		return new RelativeMessageSource(this, keyPrefix); 
	}
}
