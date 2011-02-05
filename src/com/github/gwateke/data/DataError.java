package com.github.gwateke.data;

import java.util.List;


public class DataError {

	private final String field;
	private final List<String> codes;
	private final List<?> arguments;
	
	
	public DataError(String field, List<String> codes, List<?> arguments) {
		this.field = field;
		this.codes = codes;
		this.arguments = arguments;
	}


	public String getField() {
		return field;
	}


	public List<String> getCodes() {
		return codes;
	}


	public List<?> getArguments() {
		return arguments;
	}
	
}
