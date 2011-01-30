package com.github.gwateke.binding.convert.support;

import java.util.Map;

import com.google.gwt.i18n.client.NumberFormat;


public class StringToDouble extends AbstractConverter {
	
	private static NumberFormat formatter = NumberFormat.getFormat("######0.###");
	
	
	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {String.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {Double.class, double.class};
//		return new String[] {"java.lang.Double", "double"};
	}

	
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		try {
			return new Double( formatter.parse( (String) source ) );
		} 
		catch (NumberFormatException e) {
			return new Double(0);
		}
	}
}
