package com.github.gwateke.data.query;



public interface Criterion extends QueryElement {
	
//	public static final String OPERATOR = "op";
	
//	@Deprecated
//	Map<?,?> toMap();
	
//	JSONValue toJson();
	
	<T extends Criterion> T clone();
}
