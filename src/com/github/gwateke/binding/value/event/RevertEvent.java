package com.github.gwateke.binding.value.event;


public class RevertEvent extends CommitTriggerEvent {
	
	@Override
	protected void dispatch(CommitTriggerHandler handler) {
		handler.revert();
	}
}
