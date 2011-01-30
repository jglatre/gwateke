package com.github.gwateke.data.metadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author juanjo
 */
public class FieldMetadata {

	private Map<String, Object> metadata;
	
	
	public FieldMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
	
	
	public String getName() {
		return (String) metadata.get("name");
	}
	
	
	public String getType() {
		return (String) metadata.get("type");
	}
	
	
	public boolean isPersistent() {
		return (Boolean) metadata.get("persistent");
	}
	
	
	public boolean isOptional() {
		return (Boolean) metadata.get("optional");
	}
	
	
	public boolean isIdentity() {
		return (Boolean) metadata.get("identity");
	}
	
	
	public boolean isOneToMany() {
		return (Boolean) metadata.get("oneToMany");
	}
	
	
	public boolean isManyToOne() {
		return (Boolean) metadata.get("manyToOne");	
	}
	
	
	public boolean isOneToOne() {
		return (Boolean) metadata.get("oneToOne");	
	}
	
	
	public boolean isAssociation() {
		return (Boolean) metadata.get("association");
	}
	
	
	public String getReferencedType() {
		return (String) metadata.get("referencedType");		
	}
		
	
	public List<?> getInList() {
		return (List<?>) metadata.get("inList");
	}


	public int getMinSize() {
		return (Integer) metadata.get("minSize");
	}

	
	public int getMaxSize() {
		return (Integer) metadata.get("maxSize");
	}
	
	
	public FieldMetadata set(String name, Object value) {
		metadata.put(name, value);
		return this;
	}

	
	public Map<String, ?> toMap() {
		return Collections.unmodifiableMap(metadata);
	}
}
