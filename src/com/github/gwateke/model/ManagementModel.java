package com.github.gwateke.model;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.model.event.ActionHandler;
import com.github.gwateke.model.event.ConfirmationHandler;
import com.github.gwateke.model.event.ErrorHandler;
import com.github.gwateke.model.event.MessageHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;


public interface ManagementModel {

	static enum Action {
		addNew, delete, refresh, search, clearSearch, startEdit, acceptEdit, cancelEdit;
	}
	
	ValueModel<Status> getStatus();
	void addNew();
	void delete();
	void refresh();
	void startBrowse();
	void cancelEdit();
	boolean cancelEdit(Command postCancel);
	ActionCommand getActionCommand(Action action);
	
	HandlerRegistration addErrorHandler(ErrorHandler handler);
	HandlerRegistration addActionHandler(ActionHandler handler);
	HandlerRegistration addMessageHandler(MessageHandler handler);
	HandlerRegistration addConfirmationHandler(ConfirmationHandler handler);
}
