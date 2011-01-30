package com.github.gwateke.context.support;

import java.util.Arrays;

import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.util.ObjectUtils;


public class DefaultMessageSourceResolvable implements MessageSourceResolvable {

	private final String[] codes;
	private final Object[] arguments;
	private final String defaultMessage;

	
	public DefaultMessageSourceResolvable(String code) {
		this(new String[] {code}, null, null);
	}

	
	public DefaultMessageSourceResolvable(String code, Object... arguments) {
		this(new String[] {code}, arguments, null);		
	}
	

	public DefaultMessageSourceResolvable(String[] codes) {
		this(codes, null, null);
	}


	public DefaultMessageSourceResolvable(String[] codes, String defaultMessage) {
		this(codes, null, defaultMessage);
	}


	public DefaultMessageSourceResolvable(String[] codes, Object[] arguments) {
		this(codes, arguments, null);
	}

	
	public DefaultMessageSourceResolvable(String[] codes, Object[] arguments, String defaultMessage) {
		this.codes = codes;
		this.arguments = arguments;
		this.defaultMessage = defaultMessage;
	}

	
	public String getCode() {
		return (this.codes != null && this.codes.length > 0) ? this.codes[this.codes.length - 1] : null;
	}

	
	public String[] getCodes() {
		return codes;
	}

	
	public Object[] getArguments() {
		return arguments;
	}


	public String getDefaultMessage() {
		return defaultMessage;
	}

	
	public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(getClass())) { 
            return false;
        }
        DefaultMessageSourceResolvable m2 = (DefaultMessageSourceResolvable) o;
        return ObjectUtils.nullSafeEquals(defaultMessage, m2.defaultMessage) && 
        		Arrays.equals(codes, m2.codes) &&
        		Arrays.equals(arguments, m2.arguments);
	}
}
