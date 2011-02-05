package com.github.gwateke.model.event;

import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.context.support.DefaultMessageSourceResolvable;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Command;


public class ConfirmationEvent extends GwtEvent<ConfirmationHandler> {

	private static final Type<ConfirmationHandler> TYPE = new Type<ConfirmationHandler>();
	
	public static Type<ConfirmationHandler> getType() {
		return TYPE;
	}

	private final MessageSourceResolvable resolvable;
	private Command acceptCommand;
	private boolean accepted = false;
	
	
	public ConfirmationEvent(String messageCode, Command acceptCommand) {
		this( new DefaultMessageSourceResolvable(messageCode), acceptCommand );
	}
	
	
	public ConfirmationEvent(MessageSourceResolvable resolvable, Command acceptCommand) {
		this.resolvable = resolvable;
		this.acceptCommand = acceptCommand;
	}
	
	
	public MessageSourceResolvable getMessageSourceResolvable() {
		return resolvable;
	}
	

	public boolean isAccepted() {
		return accepted;
	}
	
	
	public void accept() {
		acceptCommand.execute();
		accepted = true;
	}


	@Override
	protected void dispatch(ConfirmationHandler handler) {
		handler.onConfirmation(this);
	}


	@Override
	public Type<ConfirmationHandler> getAssociatedType() {
		return getType();
	}
	
}
