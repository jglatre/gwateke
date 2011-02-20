package com.github.gwateke.binding.value.event;

import com.google.gwt.event.shared.GwtEvent;


public abstract class CommitTriggerEvent extends GwtEvent<CommitTriggerHandler> {

	public static final Type<CommitTriggerHandler> TYPE = new Type<CommitTriggerHandler>();

	@Override
	public Type<CommitTriggerHandler> getAssociatedType() {
		return TYPE;
	}
}
