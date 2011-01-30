package com.github.gwateke.data.query;

import java.util.HashMap;
import java.util.Map;


public class Order {

	public static final String FIELD = "field";
	public static final String ASC = "asc";
	
	private String field;
	private boolean ascending;
	
	
	public Order(String field, boolean asc) {
		setField(field);
		setAscending(asc);
	}
	
	
	public String getField() {
		return field;
	}
	
	
	public void setField(String field) {
		this.field = field;
	}
	
	
	public boolean isAscending() {
		return ascending;
	}
	
	
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	
	
	public void toggle() {
		setAscending(!isAscending());
	}
	
	
	public Map<?,?> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FIELD, field);
		map.put(ASC, ascending);
		return map;
	}

	
	public String toString() {
		return getField() + ' ' + (isAscending() ? "asc" : "desc");
	}
}
