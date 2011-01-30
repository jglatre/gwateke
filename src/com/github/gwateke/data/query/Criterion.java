package com.github.gwateke.data.query;

import java.util.Map;


public interface Criterion {
	public static final String OPERATOR = "op";
	
	Map<?,?> toMap();
	<T extends Criterion> T clone();
}
