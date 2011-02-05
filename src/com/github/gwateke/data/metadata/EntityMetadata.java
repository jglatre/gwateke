package com.github.gwateke.data.metadata;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.gwateke.binding.PropertyMetadataAccessStrategy;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;


public final class EntityMetadata extends JavaScriptObject implements Iterable<FieldMetadata>, PropertyMetadataAccessStrategy {

	protected EntityMetadata() {}
	
	
	public List<String> getAllowedActions() {
		return null; //getActions().
	}
	
	
	public Set<String> getFieldNames() {
		return new JSONObject( getFields() ).keySet();
	}
	
	
	public native FieldMetadata getFieldMetadata(String fieldName) /*-{
		return this.fields[ fieldName ];
	}-*/;
	
	
	private native JsArray<?> getActions() /*-{
		return this.actions;
	}-*/;
	
	private native JavaScriptObject getFields() /*-{
		return this.fields;
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
		// TODO Auto-generated method stub
		return null;
	}


	public String getPropertyType(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}


	public Object getUserMetadata(String propertyName, String key) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isReadable(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean isWriteable(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}
}
