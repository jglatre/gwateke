package com.github.gwateke.binding.convert.support;

import java.util.Map;


public class ObjectToString extends AbstractConverter {

	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {Object.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {String.class};
	}

	
	@Override
	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		return source != null ? source.toString() : "";
	}

}
