package com.github.gwateke.ui.table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.google.gwt.user.client.ui.HTMLTable;


/**
 * Escucha los cambios en una propiedad de tipo Integer para cambiar el estilo de la 
 * fila correspondiente.
 * 
 * @author juanjogarcia
 */
public class SingleRowHighlighter implements PropertyChangeListener {
	
	private final HTMLTable table;
	private final String styleName;
	

	public SingleRowHighlighter(HTMLTable table, String styleName) {
		this.table = table;
		this.styleName = styleName;
	}


	public void propertyChange(PropertyChangeEvent event) {
		int oldRow = (Integer) event.getOldValue();
		if (oldRow > 0) {
			table.getRowFormatter().removeStyleName(oldRow, styleName);
		}
		int newRow = (Integer) event.getNewValue();
		if (newRow > 0) {
			table.getRowFormatter().addStyleName(newRow, styleName);
		}
	}
}