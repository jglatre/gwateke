package com.github.gwateke.data.query;



public class Property implements QueryElement {

	private String name;
	private String type;
	private PropertySet subproperties;
	
	
	public Property(String name) {
		this.name = name;
	}

	
	public Property(String name, String type) {
		this(name);
		this.type = type;
	}
	

	public Property(String name, String type, PropertySet subproperties) {
		this(name, type);
		this.subproperties = subproperties;
	}
	
	
	public String getName() {
		return this.name;
	}
	

	public String getType() {
		return this.type;
	}


	public PropertySet getSubproperties() {
		return this.subproperties;
	}


	public <R> R accept(QueryVisitor<R> visitor) {
		return visitor.visit(this);
	}

}
