package com.github.gwateke.binding.convert.support;

import java.util.Map;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;


public class StringToJSONValue extends AbstractConverter {


	public Class<?>[] getSourceClasses() {
		return new Class<?>[] {String.class};
	}

	
	public Class<?>[] getTargetClasses() {
		return new Class<?>[] {JSONValue.class};
	}


	protected Object doConvert(Object source, String targetClass, Map<?,?> context) throws Exception {
		return source != null ? JSONParser.parse((String) source) : JSONNull.getInstance();
	}
}
