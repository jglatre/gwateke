package com.github.gwateke.binding.convert.support;

import java.util.Map;


public class StringToObject extends AbstractConverter {

	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {String.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {Object.class};
	}

	
	@Override
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		assert source instanceof String;
		return source;
	}

}
