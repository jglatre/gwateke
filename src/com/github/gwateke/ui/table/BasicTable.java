package com.github.gwateke.ui.table;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.ValueHolder;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;


public class BasicTable extends FlexTable {
	
	private ValueModel<Integer> mouseOverRow = new ValueHolder<Integer>(-1);
	
	
	public BasicTable() {
		addDomHandler( new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				int rowIndex = getRowIndex(event);
				if (rowIndex != -1) {
					mouseOverRow.setValue( rowIndex );
				}
			}				
		}, MouseMoveEvent.getType() );
	}
	
	
	public ValueModel<Integer> getMouseOverRow() {
		return mouseOverRow;
	}
	
	
	/**
	 * Obtiene el número de fila en la que se ha producido un evento de ratón.
	 */
	public int getRowIndex(MouseEvent<?> event) {
		Element cell = getEventTargetCell((Event) event.getNativeEvent());
		if (cell != null) {
			Element row = DOM.getParent(cell);
			return DOM.getChildIndex(DOM.getParent(row), row);
		}
		return -1;
	}
	
	
	/**
	 * Obtiene un elemento de tipo TR a partir de su índice en la tabla.
	 */
	public Element getRowElement(int row) {
		return DOM.getChild(getBodyElement(), row);
	}
	
}
