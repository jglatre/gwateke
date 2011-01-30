package com.github.gwateke.binding.convert.support;

import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;


public class StringToInteger extends AbstractConverter {

	private static NumberFormat formatter = NumberFormat.getFormat("#########0");
	

	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {String.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {Integer.class, int.class};
	}


	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		try {
			double value = formatter.parse( (String) source );
			return new Integer( new Double(value).intValue() );
		} 
		catch (NumberFormatException e) {
			return new Integer(0);
		}
	}
	
}
