package com.github.gwateke.binding.adapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.ValueHolder;


public class SelectionInList<T> {
	
	private Logger log = Logger.getLogger( getClass().getName() );

	private static final int NO_SELECTION_INDEX = -1;
	
	private List<T> listItems;
	private ValueModel<T> selectionHolder;
	private ValueModel<Integer> selectionIndexHolder;
	private PropertyChangeListener selectionChangeHandler = new SelectionChangeHandler();
	private PropertyChangeListener selectionIndexChangeHandler = new SelectionIndexChangeHandler();
	

	public SelectionInList(List<T> listItems, ValueModel<T> selectionHolder) {
		this.listItems = listItems;
		this.selectionHolder = selectionHolder;
		this.selectionIndexHolder = new ValueHolder<Integer>(NO_SELECTION_INDEX);
		
		T initialSelection = selectionHolder.getValue();
		if (initialSelection != null) {
			selectionIndexHolder.setValue( listItems.indexOf(initialSelection) );
		}
		
		this.selectionHolder.addValueChangeListener(selectionChangeHandler);
		this.selectionIndexHolder.addValueChangeListener(selectionIndexChangeHandler);
		
		if (log.isLoggable(Level.FINE)) {
			log.fine("[SelectionInList] initialized, selection " + this.selectionHolder + ", index " + this.selectionIndexHolder);
		}
	}
	
	
	public ValueModel<T> getSelectionHolder() {
		return selectionHolder;
	}
	
	
	public ValueModel<Integer> getSelectionIndexHolder() {
		return selectionIndexHolder;
	}
	
	
	public void release() {
		selectionHolder.removeValueChangeListener(selectionChangeHandler);
		selectionIndexHolder.removeValueChangeListener(selectionIndexChangeHandler);
	}
	
	
	private class SelectionChangeHandler implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			Object newSelection = evt.getNewValue();
			int newIndex = listItems.indexOf(newSelection);
			
			if (log.isLoggable(Level.FINE)) {
				log.fine("[SelectionInList] selection changed, new index: " + newIndex);
			}
			
			selectionIndexHolder.removeValueChangeListener(selectionIndexChangeHandler);
			selectionIndexHolder.setValue(newIndex);
			selectionIndexHolder.addValueChangeListener(selectionIndexChangeHandler);
		}
	}
	
	
	private class SelectionIndexChangeHandler implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			int newIndex = (Integer) evt.getNewValue();
			T newSelection = getSafeElementAt(newIndex);	
			
			if (log.isLoggable(Level.FINE)) {
				log.fine("[SelectionInList] index changed, new selection: " + newSelection);
			}

			selectionHolder.removeValueChangeListener(selectionChangeHandler);
			selectionHolder.setValue(newSelection);
			selectionHolder.addValueChangeListener(selectionChangeHandler);
		}		
	}
	
	
	private T getSafeElementAt(int index) {
		return (0 <= index && index < listItems.size()) ? 
				listItems.get(index) : null;
	}
}
