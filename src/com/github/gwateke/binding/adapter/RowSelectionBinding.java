package com.github.gwateke.binding.adapter;

import java.util.logging.Logger;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;


/**
 * Binding between a boolean ValueModel and a row position in a ListSelectionModel. 
 * 
 */
public class RowSelectionBinding extends AbstractValueModelAdapter<Boolean> implements ListSelectionListener {
	
	private final Logger log = Logger.getLogger( getClass().getName() );
	
	private ListSelectionModel selectionModel;
	
	/** absolute position in ListSelectionModel. */
	private int row;

	
	public RowSelectionBinding(ListSelectionModel selectionModel, int row, ValueModel<Boolean> valueModel) {
		super(valueModel);
		this.row = row;
		this.selectionModel = selectionModel;
		this.selectionModel.addListSelectionListener(this);
		updateValueModel();
	}
	
	
	public void unbind() {
		super.unbind();
		selectionModel.removeListSelectionListener(this);
	}		

	
	protected void valueModelValueChanged(Boolean newValue) {		
		log.fine("valueModelValueChanged " + row + " " + this);
		
		if (newValue) {
			selectionModel.addSelectionInterval(row, row);
		}
		else {
			selectionModel.removeSelectionInterval(row, row);
		}
	}		

	
	public void valueChanged(ListSelectionEvent event) {
		updateValueModel();
	}		
	
	
	private void updateValueModel() {
		adaptedValueChanged( selectionModel.isSelectedIndex(row) );
	}
}
