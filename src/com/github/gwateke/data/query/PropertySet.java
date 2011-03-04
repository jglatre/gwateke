package com.github.gwateke.data.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class PropertySet extends HashSet<Property> implements QueryElement {

	
	public PropertySet(String... properties) {
		for (String property : properties) {
			add(property);
		}
	}
	
	
	public void add(String name) {
		add( new Property(name) );
	}
	
	
	public void add(String name, String type) {
		add( new Property(name, type) );
	}
	
	
	public String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (Property property : this) {
			names.add( property.getName() );
		}
		return names.toArray(new String[names.size()]);
	}
	
	
	public String[] getDeepNames() {
		List<String> names = new ArrayList<String>();
		for (Property property : this) {
			String name = property.getName();
			String type = property.getType();
			if (type != null && "Object".equals(type)) {
				for (String subname: property.getSubproperties().getDeepNames()) {
					names.add(name + '.' + subname);
				}
			}
			else {
				names.add(name);
			}
		}
		return names.toArray(new String[names.size()]);
	}
	


	public <R> R accept(QueryVisitor<R> visitor) {
		return visitor.visit(this);
	}
	
}
