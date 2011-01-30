package com.github.gwateke.binding.convert.support;

import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;


public class DoubleToString extends AbstractConverter {
	
	private static NumberFormat formatter = NumberFormat.getFormat("######0.###");
	
	
	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {Double.class, double.class};
//		return new String[] {"java.lang.Double", "double"};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {String.class};
//		return new String[] {"java.lang.String"};
	}

	
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		if (source == null) {
			return "";
		}
		return formatter.format( ((Number) source).doubleValue() );
	}

}
