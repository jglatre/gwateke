package com.github.gwateke.model;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.command.ActionCommand;
import com.google.gwt.user.client.Command;


public interface ManagementModel {

	static enum Action {
		addNew, delete, refresh, search, clearSearch, startEdit, acceptEdit, cancelEdit;
	}
	
	ValueModel<Status> getStatus();
	void startBrowse();
	void cancelEdit();
	boolean cancelEdit(Command postCancel);
	ActionCommand getActionCommand(Action action);
}
