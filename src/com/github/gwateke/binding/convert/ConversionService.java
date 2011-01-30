package com.github.gwateke.binding.convert;

import com.github.gwateke.core.closure.Closure;


public interface ConversionService {

	Closure<?,?> getConversionClosure(Class<?> sourceClass, Class<?> targetClass);
	Closure<?,?> getConversionClosure(String sourceClass, String targetClass);
}
