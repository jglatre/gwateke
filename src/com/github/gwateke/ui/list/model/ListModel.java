package com.github.gwateke.ui.list.model;



public interface ListModel<K, E> {

	int getSize();
	E getElementAt(int index);
	K getKeyAt(int index);
	void addListener(ListModelListener listener);
}
