package com.github.gwateke.model.event;

import com.github.gwateke.command.ActionCommand;
import com.google.gwt.event.shared.GwtEvent;


/**
 * Event fired by <code>ManagementModel</code> when an <code>ActionCommand</code> is executed.
 * 
 * @author juanjo
 */
public class ActionEvent extends GwtEvent<ActionHandler> {

	private static final Type<ActionHandler> TYPE = new Type<ActionHandler>();
	
	public static Type<ActionHandler> getType() {
		return TYPE;
	}

	
	private final ActionCommand actionCommand;
	
	
	public ActionEvent(ActionCommand actionCommand) {
		this.actionCommand = actionCommand;
	}

	
	public ActionCommand getActionCommand() {
		return actionCommand;
	}


	@Override
	protected void dispatch(ActionHandler handler) {
		handler.onAction(this);
	}


	@Override
	public Type<ActionHandler> getAssociatedType() {
		return TYPE;
	}	
}
