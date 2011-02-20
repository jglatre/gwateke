package com.github.gwateke.binding.value.event;


public class CommitEvent extends CommitTriggerEvent {
	
	@Override
	protected void dispatch(CommitTriggerHandler handler) {
		handler.commit();
	}
}
