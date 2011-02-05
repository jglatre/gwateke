package com.github.gwateke.model.list.support;

import com.github.gwateke.context.MessageSource;
import com.github.gwateke.model.list.ListModel;
import com.github.gwateke.model.list.event.ListModelChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * Decorador de ListModel que traduce los elementos de la lista mediante un MessageSource.
 * 
 * @author juanjogarcia
 */
public class MessageSourceListModelDecorator implements ListModel<Object, String> {

	private MessageSource messageSource;
	private ListModel<Object, String> delegate;
	
	
	public MessageSourceListModelDecorator(MessageSource messageSource, ListModel<Object, String> delegate) {
		this.messageSource = messageSource;
		this.delegate = delegate;
	}

	
	public String getElementAt(int index) {
		return messageSource.getMessage( delegate.getElementAt(index) );
	}


	public Object getKeyAt(int index) {
		return delegate.getKeyAt(index);
	}
	

	public int getSize() {
		return delegate.getSize();
	}


	public HandlerRegistration addChangeHandler(ListModelChangeHandler handler) {
		return delegate.addChangeHandler( handler );
	}

}
