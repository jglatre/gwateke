package com.github.gwateke.model;


/**
 * Posibles estados de un DataManagementModel.
 * 
 * @author juanjo
 */
public interface Status {
	
	Status INITIALIZING = BasicStatus.Initializing;
	Status BROWSE = BasicStatus.Browse;
	Status EDIT = BasicStatus.Edit;

	enum BasicStatus implements Status {
		Initializing,
		Browse, 
		Edit;
	}
}
