package com.github.gwateke.context;



public interface MessageSource {
	
	String getRequiredMessage(String key);
	String getMessage(String key, Object... params);
	String getMessage(String[] keys, Object... params);
	String getMessage(MessageSourceResolvable resolvable);
	MessageSource getMessageSource(String prefix);
}
