package com.github.gwateke.ui.table.model;

import java.util.Collection;

import javax.swing.event.ListSelectionListener;


public interface TableSelectionModel<T> {

	void clearSelection();
	void addSelection(int first, int last);
	void removeSelection(int first, int last);
	boolean isSelected(int absoluteRow);
	Collection<T> getSelectedIds();
	int getSelectedRowCount();
	
	void addListener(ListSelectionListener listener);
	void removeListener(ListSelectionListener listener);
}
