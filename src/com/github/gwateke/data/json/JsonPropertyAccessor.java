package com.github.gwateke.data.json;

import com.github.gwateke.data.PropertyAccessor;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


public class JsonPropertyAccessor implements PropertyAccessor<JSONObject, JSONString> {

	private final JSONObject object;
	
	
	public JsonPropertyAccessor(JSONObject object) {
		this.object = object;
	}
	
	
	public JSONString getId() {
		return object.get("id").isString();
	}

	
	public Object get(String propertyPath) {
		return object.get(propertyPath);
	}

	
	public JSONObject getDomainObject() {
		return object;
	}

}
