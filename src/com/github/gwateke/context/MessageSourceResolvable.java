package com.github.gwateke.context;

public interface MessageSourceResolvable {
	String[] getCodes();
	Object[] getArguments();
	String getDefaultMessage();
}
