package com.github.gwateke.data.metadata;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.gwateke.binding.PropertyMetadataAccessStrategy;
import com.github.gwateke.util.JSONUtil;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;


public final class EntityMetadata extends JavaScriptObject implements Iterable<FieldMetadata>, PropertyMetadataAccessStrategy {

	protected EntityMetadata() {}
	
	
	public static native EntityMetadata fromJson(String json) /*-{
		return eval('(' + json + ')');
	}-*/;

	
	public List<String> getAllowedActions() {
		return new AbstractList<String>() {
			private JsArrayString array = getActions();
			@Override
			public String get(int index) {
				return array.get(index);
			}
			@Override
			public int size() {
				return array.length();
			}
		};
	}
	
	
	public Set<String> getFieldNames() {
		return new JSONObject( getFields() ).keySet();
	}
	
	
	public native FieldMetadata getFieldMetadata(String fieldName) /*-{
		return this.fields[ fieldName ];
	}-*/;
	

	public Iterator<FieldMetadata> iterator() {
		return new Iterator<FieldMetadata>() {
			Iterator<String> names = getFieldNames().iterator();

			public boolean hasNext() {
				return names.hasNext();
			}

			public FieldMetadata next() {
				return getFieldMetadata(names.next());
			}

			public void remove() {}			
		};
	}


	public Map<String, ?> getAllUserMetadata(String propertyName) {
		return JSONUtil.toJavaMap( new JSONObject( getField(propertyName) ) );
	}


	public String getPropertyType(String propertyName) {
		return getFieldMetadata(propertyName).getType();
	}


	public Object getUserMetadata(String propertyName, String key) {
		JSONValue value = new JSONObject( getField(propertyName) ).get(key);
		return JSONUtil.toJavaValue(value);
	}


	public boolean isReadable(String propertyName) {
		return getField(propertyName) != null;
	}


	public boolean isWriteable(String propertyName) {
		return getField(propertyName) != null;
	}

	//--------------------------------------------------------------------
	
	private native JsArrayString getActions() /*-{
		return this.actions;
	}-*/;
	
	private native JavaScriptObject getFields() /*-{
		return this.fields;
	}-*/;
	
	private native JavaScriptObject getField(String fieldName) /*-{
		return this.fields[ fieldName ];
	}-*/;

}
