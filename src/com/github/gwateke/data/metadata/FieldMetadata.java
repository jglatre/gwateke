package com.github.gwateke.data.metadata;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * 
 * 
 * @author juanjo
 */
public class FieldMetadata extends JavaScriptObject {
	
	protected FieldMetadata() {}
	
	
	public final native String getName() /*-{
		return this.name;
	}-*/;
	
	
	public final native String getType() /*-{
		return this.type;
	}-*/;
	
	
	public final native boolean isPersistent() /*-{
		return this.persistent;
	}-*/;
	
	
	public final native boolean isOptional() /*-{
		return this.optional;
	}-*/;
	
	
	public final native boolean isIdentity() /*-{
		return this.identity;
	}-*/;
	
	
	public final native boolean isOneToMany() /*-{
		return this.oneToMany;
	}-*/;
	
	
	public final native boolean isManyToOne() /*-{
		return this.manyToOne;	
	}-*/;
	
	
	public final native boolean isOneToOne() /*-{
		return this.oneToOne;	
	}-*/;
	
	
	public final native boolean isAssociation() /*-{
		return this.association;
	}-*/;
	
	
	public final native String getReferencedType() /*-{
		return this.referencedType;		
	}-*/;
		
	
//	public List<?> getInList() {
//		return (List<?>) metadata.get("inList");
//	}


	public final native int getMinSize() /*-{
		return this.minSize;
	}-*/;

	
	public final native int getMaxSize() /*-{
		return this.maxSize;
	}-*/;
	
	
	public final native FieldMetadata set(String name, Object value) /*-{
		this[ name ] = value;
		return this;
	}-*/;

	
//	public Map<String, ?> toMap() {
//		return Collections.unmodifiableMap(metadata);
//	}
}
