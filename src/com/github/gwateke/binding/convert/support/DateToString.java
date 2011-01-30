package com.github.gwateke.binding.convert.support;

import java.util.Date;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;


public class DateToString extends AbstractConverter {

	private static DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd-MM-yyyy HH:mm");
	
	
	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {Date.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {String.class};
	}

	
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		return source == null ? "" : dateTimeFormat.format((Date) source);
	}

}
