package com.github.gwateke.model.list.event;

import com.google.gwt.event.shared.GwtEvent;


public class ListModelChangeEvent extends GwtEvent<ListModelChangeHandler> {

	private static final Type<ListModelChangeHandler> TYPE = new Type<ListModelChangeHandler>();
	
	public static Type<ListModelChangeHandler> getType() {
		return TYPE;
	}

	
	public ListModelChangeEvent() {	
	}
	
	
	@Override
	protected void dispatch(ListModelChangeHandler handler) {
		handler.onChange(this);
	}

	
	@Override
	public Type<ListModelChangeHandler> getAssociatedType() {
		return TYPE;
	}

}
