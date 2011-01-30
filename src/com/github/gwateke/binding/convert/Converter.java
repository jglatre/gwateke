package com.github.gwateke.binding.convert;

import java.util.Map;


public interface Converter {
	
	Class<?>[] getSourceClasses();
	Class<?>[] getTargetClasses();
	Object convert(Object source, String targetClass, Map<?,?> context) throws ConversionException;
}
