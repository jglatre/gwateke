package com.github.gwateke.binding;

import java.util.Map;


/**
 * Simple interface for accessing metadata about a particular property.
 * 
 * EXPERIMENTAL - not yet fit for general use
 * @author Keith Donald
 */
public interface PropertyMetadataAccessStrategy {
	
    boolean isReadable(String propertyName);

    boolean isWriteable(String propertyName);

    String getPropertyType(String propertyName);

    /**
     * Returns custom metadata that may be associated with the specified
     * property path. 
     */
    Object getUserMetadata(String propertyName, String key);

    /**
     * Returns all custom metadata associated with the specified property
     * in the form of a Map.
     *
     * @return Map containing String keys - this method may or may not return
     *         <code>null</code> if there is no custom metadata associated
     *         with the property.
     */
    Map<String, ?> getAllUserMetadata(String propertyName);
}