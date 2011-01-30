package com.github.gwateke.ui.list.model;

import java.util.ArrayList;
import java.util.List;



public abstract class AbstractListModel<K, E> implements ListModel<K, E> {

	private List<ListModelListener> listeners = new ArrayList<ListModelListener>();
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


	public void addListener(ListModelListener listener) {
		listeners.add(listener);
	}

	
	protected final void fireDataChanged() {
		for (ListModelListener listener : listeners) {
			listener.onDataChanged(this);
		}
	}                                                                                                                         
	
}
