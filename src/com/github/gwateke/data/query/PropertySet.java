package com.github.gwateke.data.query;

import java.util.HashSet;


public class PropertySet extends HashSet<Property> implements QueryElement {

	
	public PropertySet(String... properties) {
		for (String property : properties) {
			add(property);
		}
	}
	
	
	public void add(String name) {
		add( new Property(name) );
	}
	

	public <R> R accept(QueryVisitor<R> visitor) {
		return visitor.visit(this);
	}
	
}
