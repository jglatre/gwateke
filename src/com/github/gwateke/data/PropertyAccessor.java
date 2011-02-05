package com.github.gwateke.data;


public interface PropertyAccessor<T, I> {

	I getId();
	Object get(String propertyPath);
	T getDomainObject();
	
}
