package com.github.gwateke.ui.table.binding;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.model.table.TableSelectionModel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * Binding entre un CheckBox y el modelo de selección.
 */
public class CheckBoxBinding implements Binding<CheckBox>, ClickHandler, ListSelectionListener {

	private CheckBox checkBox;
	private TableSelectionModel<?> selectionModel;
	private int absoluteRow;
	private HandlerRegistration handlerRegistration;

	public CheckBoxBinding(CheckBox checkBox, TableSelectionModel<?> selectionModel, int absoluteRow) {
		selectionModel.addListener(this);
		this.checkBox = checkBox;
		this.selectionModel = selectionModel;
		this.absoluteRow = absoluteRow;
		this.handlerRegistration = this.checkBox.addClickHandler(this);
		
		refresh();
	}
	
	public void refresh() {
		checkBox.setValue( selectionModel.isSelected(absoluteRow) );
	}

	public void onClick(ClickEvent event) {
		if (checkBox.getValue()) {
			selectionModel.addSelection(absoluteRow, absoluteRow);
		}
		else {
			selectionModel.removeSelection(absoluteRow, absoluteRow);
		}
	}

	public void valueChanged(ListSelectionEvent event) {
		if (event.getFirstIndex() <= absoluteRow && absoluteRow <= event.getLastIndex()) {
			refresh();
		}
	}		
	
	public CheckBox getWidget() {
		return checkBox;
	}

	public void unbind() {
		handlerRegistration.removeHandler();
		selectionModel.removeListener(this);			
	}
}