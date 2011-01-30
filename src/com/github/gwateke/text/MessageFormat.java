package com.github.gwateke.text;


public class MessageFormat {
	
	public static String format(String text, Object... params) {                                              

		for (int i = 0; i < params.length; i++) {                                                                   
			String p = params[i] == null ? "" : params[i].toString();                                                                                
			text = text.replaceAll("\\{" + i + "}", p);                                                              
		}                                                                                               
		return text;                                                                                            
	}   
	
}
