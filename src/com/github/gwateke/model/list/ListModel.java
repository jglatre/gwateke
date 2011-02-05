package com.github.gwateke.model.list;

import com.github.gwateke.model.list.event.ListModelChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;



public interface ListModel<K, E> {

	int getSize();
	E getElementAt(int index);
	K getKeyAt(int index);
	HandlerRegistration addChangeHandler(ListModelChangeHandler handler);
}
