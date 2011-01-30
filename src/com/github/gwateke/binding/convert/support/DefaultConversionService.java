package com.github.gwateke.binding.convert.support;

import java.util.ArrayList;
import java.util.List;

import com.github.gwateke.binding.convert.ConversionService;
import com.github.gwateke.binding.convert.Converter;
import com.github.gwateke.core.closure.Closure;



public class DefaultConversionService implements ConversionService {

	private List<Converter> converters = new ArrayList<Converter>();
	
	
	public DefaultConversionService() {
		addDefaultConverters();
	}
	
	
	protected void addDefaultConverters() {
		addConverter( new BooleanToObject() );
		addConverter( new DateToString() );
		addConverter( new DoubleToString() );
		addConverter( new IntegerToString() );
		addConverter( new ObjectToString() );
		addConverter( new ObjectToBoolean() );
		addConverter( new StringToDouble() );
		addConverter( new StringToInteger() );
		addConverter( new StringToDate() );
		addConverter( new StringToObject() );
		addConverter( new StringToJSONValue() );
	}
	
	
	public void addConverter(Converter converter) {
		converters.add(converter);
	}
	
	
	public Closure<?,?> getConversionClosure(Class<?> sourceClass, Class<?> targetClass) {
		for (Converter converter : converters) {
			if (contains(converter.getSourceClasses(), sourceClass) &&
					contains(converter.getTargetClasses(), targetClass)) {
				return new ConverterClosure(converter, targetClass.getName());
			}
		}
		return null;
	}
	
	
	public Closure<?,?> getConversionClosure(String sourceClass, String targetClass) {
		for (Converter converter : converters) {
			if (contains(converter.getSourceClasses(), sourceClass) &&
					contains(converter.getTargetClasses(), targetClass)) {
				return new ConverterClosure(converter, targetClass);
			}
		}
		return null;
	}

	
	private static class ConverterClosure implements Closure<Object,Object> {
		private Converter converter;
		private String targetClass;
		
		public ConverterClosure(Converter converter, String targetClass) {
			this.converter = converter;
			this.targetClass = targetClass;
		}
		
		public Object call(Object source) {
			return converter.convert(source, targetClass, null);
		}		
	}

	
	private static boolean contains(Class<?>[] classes, String claz) {
		for (int i = 0; i < classes.length; i++) {
			if (classes[i].getName().equals(claz)) {
				return true;
			}
		}
		return false;
	}

	
	private static boolean contains(Class<?>[] classes, Class<?> claz) {
		for (int i = 0; i < classes.length; i++) {
			if (classes[i].equals(claz)) {
				return true;
			}
		}
		return false;
	}

}
