package com.github.gwateke.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


public abstract class JSONUtil {

	public static <T> T parse(String jsonString) {
		return toJavaValue( JSONParser.parse(jsonString) );
	}	
	
	
	@SuppressWarnings("unchecked")
	public static <T> T toJavaValue(JSONValue value) {
		if (value == null || value.isNull() != null) {
			return null;
		}
		else if (value.isString() != null) {
			return (T) value.isString().stringValue();
		}
		else if (value.isNumber() != null) {
			return (T) new Double( value.isNumber().doubleValue() );
		}
		else if (value.isArray() != null) {
			return (T) toJavaList( value.isArray() );
		}
		else if (value.isBoolean() != null) {
			return (T) new Boolean( value.isBoolean().booleanValue() );
		}
		else if (value.isObject() != null) {
			return (T) toJavaMap( value.isObject() );
		}
		else {
			throw new JSONException();
		}
	}
	
	
	public static Map<?,?> toJavaMap(JSONObject jsonObject) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (String key : jsonObject.keySet()) {
			map.put( key, toJavaValue( jsonObject.get(key) ) );
		}
		
		return map;
	}


	public static List<?> toJavaList(JSONArray jsonArray) {
		List<Object> list = new ArrayList<Object>();
		
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add( toJavaValue( jsonArray.get(i) ) );
		}
		
		return list;
	}
	
	
	public static JSONValue toJsonValue(Object value) {
		if (value == null) {
			return JSONNull.getInstance();
		}
		if (value instanceof JSONValue) {
			return (JSONValue) value;
		}
		else if (value instanceof String) {
			return new JSONString( (String) value );
		}
		else if (value instanceof Number) {
			return new JSONNumber( ((Number) value).doubleValue() );
		}
		else if (value instanceof Iterable<?>) {
			JSONArray array = new JSONArray();
			int i = 0;
			for (Object item : (Iterable<?>) value) {
				array.set( i++, toJsonValue(item) );
			}
			return array;
		}
		else if (value instanceof Boolean) {
			return JSONBoolean.getInstance( (Boolean) value );
		}
		else {
			throw new JSONException();
		}
	}
}
