package com.github.gwateke.util;


public abstract class StringUtils {

	
	public static String uncapitalize(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
}
