package com.github.gwateke.ui.list.model.context;

import com.github.gwateke.context.MessageSource;
import com.github.gwateke.ui.list.model.ListModel;
import com.github.gwateke.ui.list.model.ListModelListener;


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


	public void addListener(ListModelListener listener) {
		delegate.addListener(listener);
	}

}
