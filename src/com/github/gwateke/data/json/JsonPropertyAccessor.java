package com.github.gwateke.data.json;

import com.github.gwateke.data.PropertyAccessor;
import com.google.gwt.json.client.JSONObject;


public class JsonPropertyAccessor implements PropertyAccessor<JSONObject, Long> {

	private final JSONObject object;
	
	
	public JsonPropertyAccessor(JSONObject object) {
		this.object = object;
	}
	
	
	public Long getId() {
		return Double.valueOf( object.get("id").isNumber().doubleValue() ).longValue();
	}

	
	public Object get(String propertyPath) {
		return object.get(propertyPath);
	}

	
	public JSONObject getDomainObject() {
		return object;
	}

}
