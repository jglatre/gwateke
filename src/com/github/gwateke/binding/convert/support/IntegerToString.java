package com.github.gwateke.binding.convert.support;

import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;


public class IntegerToString extends AbstractConverter {
	
	private static NumberFormat formatter = NumberFormat.getFormat("#########0");
	
	
	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {Integer.class, Long.class, int.class, long.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {String.class};
	}

	
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		if (source == null) {
			return "";
		}
		return formatter.format( ((Number) source).doubleValue() );
	}

}
