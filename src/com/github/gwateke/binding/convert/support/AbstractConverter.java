package com.github.gwateke.binding.convert.support;

import java.util.Collections;
import java.util.Map;

import com.github.gwateke.binding.convert.ConversionException;
import com.github.gwateke.binding.convert.Converter;



public abstract class AbstractConverter implements Converter {

	/**
	 * Convenience convert method that converts the provided source to the first
	 * target object supported by this converter. Useful when a converter only
	 * supports conversion to a single target.
	 * 
	 * @param source The source to convert
	 * @return the converted object
	 * @throws ConversionException a exception occured converting the source
	 * value
	 */
	public Object convert(Object source) throws ConversionException {
		return convert(source, getTargetClasses()[0].getName(), Collections.EMPTY_MAP);
	}

	
	/**
	 * Convenience convert method that converts the provided source to the
	 * target class specified with an empty conversion context.
	 * 
	 * @param source The source to convert
	 * @param targetClass the target class to convert the source to, must be one
	 * of the supported <code>targetClasses</code>
	 * @return the converted object
	 * @throws ConversionException a exception occured converting the source
	 * value
	 */
	public Object convert(Object source, String targetClass) throws ConversionException {
		return convert(source, targetClass, Collections.EMPTY_MAP);
	}

	/**
	 * Convenience convert method that converts the provided source to the first
	 * target object supported by this converter. Useful when a converter only
	 * supports conversion to a single target.
	 * 
	 * @param source The source to convert
	 * @param context the conversion context, useful for influencing the
	 * behavior of the converter.
	 * @return the converted object
	 * @throws ConversionException a exception occured converting the source
	 * value
	 */
	public Object convert(Object source, Map<?,?> context) throws ConversionException {
		return convert(source, getTargetClasses()[0].getName(), context);
	}

	
	public Object convert(Object source, String targetClass, Map<?,?> context) throws ConversionException {
		try {
			if (context == null) {
				context = Collections.EMPTY_MAP;
			}
			return doConvert(source, targetClass, context);
		}
		catch (ConversionException e) {
			throw e;
		}
		catch (Throwable e) {
			if (targetClass == null) {
				targetClass = getTargetClasses()[0].getName();
			}
			throw new ConversionException("Converting " + source + " to " + targetClass, e);
		}
	}

	/**
	 * Template method subclasses should override to actually perform the type
	 * conversion.
	 * @param source the source to convert from
	 * @param targetClass the target type to convert to
	 * @param context an optional conversion context that may be used to
	 * influence the conversion process, guaranteed to be non-null.
	 * @return the converted source value
	 * @throws Exception an exception occured, will be wrapped in a conversion
	 * exception if necessary
	 */
	protected abstract Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception;

}
