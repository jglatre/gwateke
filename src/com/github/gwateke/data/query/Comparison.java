package com.github.gwateke.data.query;

import java.util.HashMap;
import java.util.Map;


public class Comparison<T> implements Criterion {

	public static final String FIELD = "field";
	public static final String VALUE = "value";
	
	public static final String EQUALS = "eq";
	public static final String NOT_EQUALS = "neq";
	public static final String LIKE = "like";
	public static final String NULL = "null";
	public static final String NOT_NULL = "notnull";
	public static final String IN = "in";
	
	private String operator;
	private String propertyName;
	private T value;
	
	
	public Comparison(String operator, String propertyName, T value) {
		this.operator = operator;
		this.propertyName = propertyName;
		this.value = value;
	}
	
	
	public String getOperator() {
		return operator;
	}
	
	
	public String getField() {
		return propertyName;
	}
	
	
	public T getValue() {
		return value;
	}	
	
	
	public String toString() {
		return getField() + ' ' + getOperator() + ' ' + getValue();
	}
	
	
	@SuppressWarnings("unchecked")
	public Comparison<T> clone() {
		return new Comparison<T>(this.operator, this.propertyName, this.value);
	}
	
	
	public Map<?,?> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(OPERATOR, operator);
		map.put(FIELD, propertyName);
		map.put(VALUE, value);
		return map;
	}
	
	
	public static class Equals<T> extends Comparison<T> {
		public Equals(String propertyName, T value) {
			super(EQUALS, propertyName, value);
		}
	}
	
	
	public static class NotEquals<T> extends Comparison<T> {
		public NotEquals(String propertyName, T value) {
			super(NOT_EQUALS, propertyName, value);
		}
	}
	
	
	public static class Like extends Comparison<String> {
		public Like(String propertyName, String value) {
			super(LIKE, propertyName, value);
		}
	}
	
	
	public static class Null extends Comparison<Object> {
		public Null(String propertyName) {
			super(NULL, propertyName, null);
		}
	}
	

	public static class NotNull extends Comparison<Object> {
		public NotNull(String propertyName) {
			super(NOT_NULL, propertyName, null);
		}
	}
	
	
	public static class In<T> extends Comparison<Iterable<T>> {
		public In(String propertyName, Iterable<T> value) {
			super(IN, propertyName, value);
		}
	}
}
