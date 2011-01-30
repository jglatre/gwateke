package com.github.gwateke.binding.convert.support;

import java.util.Map;


public class BooleanToObject extends AbstractConverter {

	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {Boolean.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {Object.class};
	}

	
	@Override
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		return source != null ? (Boolean) source : Boolean.FALSE;
	}

}
