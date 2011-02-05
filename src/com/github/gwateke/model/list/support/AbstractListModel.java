package com.github.gwateke.model.list.support;

import com.github.gwateke.model.list.ListModel;
import com.github.gwateke.model.list.event.ListModelChangeEvent;
import com.github.gwateke.model.list.event.ListModelChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;



public abstract class AbstractListModel<K, E> implements ListModel<K, E> {

	private HandlerManager handlerManager;
	private boolean hasNull = false;
	private K nullKey;
	private E nullElement;

	
	public AbstractListModel() {
	}
	
	
	public boolean isNullable() {
		return this.hasNull;
	}
	
	
	public void setNullable(boolean nullable) {
		this.hasNull = nullable;
	}
	

	public final K getNullKey() {
		return nullKey;
	}


	public final void setNullKey(K nullKey) {
		this.nullKey = nullKey;
	}


	public final E getNullElement() {
		return nullElement;
	}


	public final void setNullElement(E nullElement) {
		this.nullElement = nullElement;
	}


	public HandlerRegistration addChangeHandler(ListModelChangeHandler handler) {
		return getHandlerManager().addHandler( ListModelChangeEvent.getType(), handler );
	}
	
	
	protected final void fireDataChanged() {
		getHandlerManager().fireEvent( new ListModelChangeEvent() );
	}                     
	
	
	protected HandlerManager getHandlerManager() {
		if (handlerManager == null) {
			handlerManager = new HandlerManager(this);
		}
		return handlerManager;
	}
	
}
