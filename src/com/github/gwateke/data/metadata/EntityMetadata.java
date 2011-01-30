package com.github.gwateke.data.metadata;

import java.util.List;

import com.github.gwateke.binding.PropertyMetadataAccessStrategy;


public interface EntityMetadata extends Iterable<FieldMetadata>, PropertyMetadataAccessStrategy {

	List<String> getAllowedActions();
	List<String> getFieldNames();
	FieldMetadata getFieldMetadata(String fieldName);
}
