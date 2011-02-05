package com.github.gwateke.model.event;

import com.google.gwt.event.shared.GwtEvent;


public class ErrorEvent extends GwtEvent<ErrorHandler> {

	private static final Type<ErrorHandler> TYPE = new Type<ErrorHandler>();
	
	public static Type<ErrorHandler> getType() {
		return TYPE;
	}

	
	private final Throwable error;
	
	
	public ErrorEvent(Throwable error) {
		this.error = error;
	}
	
	
	public Throwable getError() {
		return error;
	}
	
	
	@Override
	protected void dispatch(ErrorHandler handler) {
		handler.onError(this);
	}

	
	@Override
	public Type<ErrorHandler> getAssociatedType() {
		return TYPE;
	}

}
