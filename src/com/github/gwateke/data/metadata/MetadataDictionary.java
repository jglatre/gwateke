package com.github.gwateke.data.metadata;

import com.google.gwt.core.client.JavaScriptObject;


public class MetadataDictionary extends JavaScriptObject {
	
	protected MetadataDictionary() {}
	
	
	public static native MetadataDictionary fromJson(String json) /*-{
		return eval('(' + json + ')');
	}-*/;
	
	
	public final native EntityMetadata getEntity(String entityName) /*-{
		return this[ entityName ];
	}-*/;

}
