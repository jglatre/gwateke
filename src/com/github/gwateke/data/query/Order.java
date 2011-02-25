package com.github.gwateke.data.query;



public class Order implements QueryElement {

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
	
	
	public String toString() {
		return getField() + ' ' + (isAscending() ? "asc" : "desc");
	}


	public <R> R accept(QueryVisitor<R> visitor) {
		return visitor.visit(this);
	}
}
