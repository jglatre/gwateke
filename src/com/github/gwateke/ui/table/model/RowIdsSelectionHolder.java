package com.github.gwateke.ui.table.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.gwateke.core.closure.Closure;


/**
 * @author juanjogarcia
 *
 * @param <T> tipo de identificador.
 */
public class RowIdsSelectionHolder<T> implements TableSelectionModel<T> {
	
	private final Log log = LogFactory.getLog(getClass());

	private final Closure<T, Integer> rowIdGetter;
	private Map<Integer, T> selectedIds = new HashMap<Integer, T>();
	private List<ListSelectionListener> listeners = new ArrayList<ListSelectionListener>();

	
	/**
	 * @param rowIdGetter callback que obtiene el identificador de una fila a partir de una posición absoluta.
	 */
	public RowIdsSelectionHolder(Closure<T, Integer> rowIdGetter) {
		this.rowIdGetter = rowIdGetter;
	}
	
	
	/**
	 * @return los identificadores seleccionados.
	 */
	public Collection<T> getSelectedIds() {
		return selectedIds.values();
	}


	/**
	 * @return número de filas seleccionadas.
	 */
	public int getSelectedRowCount() {
		return selectedIds.size();
	}
	
	
	public void clearSelection() {
		if (!selectedIds.isEmpty()) {
			final int min = Collections.min(selectedIds.keySet());
			final int max = Collections.max(selectedIds.keySet());
			selectedIds.clear();
			fireValueChanged(min, max);
		}
	}
	
	
	public void addSelection(int first, int last) {
		boolean changed = false;
		for (int i = first; i <= last; i++) {
			final T rowId = rowIdGetter.call(i);
			if (rowId != null) { 
				selectedIds.put( i, rowId );
				changed = true;
			}
			else {
				log.warn("No hay identificador para la posición absoluta: " + i);
			}
		}
		if (changed) {
			fireValueChanged( first, last );
		}
	}
	
	
	public void removeSelection(int first, int last) {
		boolean changed = false;
		for (int i = first; i <= last; i++) {
			if (selectedIds.remove(i) != null) {
				changed = true;
			}
		}
		if (changed) {
			fireValueChanged( first, last );
		}
	}
	
	
	/**
	 * @return si una posición absoluta está seleccionada.
	 */
	public boolean isSelected(int row) {
		return selectedIds.containsKey(row);
	}
	

	public void addListener(ListSelectionListener listener) {
		listeners.add(listener);
	}


	public void removeListener(ListSelectionListener listener) {
		listeners.remove(listener);		
	}
	
	//----------------------------------------------------------------------------------------------
	
	protected void fireValueChanged(int firstIndex, int lastIndex) {
		final ListSelectionEvent event = new ListSelectionEvent(this, firstIndex, lastIndex, false);
		for (ListSelectionListener listener : listeners) {
			listener.valueChanged(event);
		}
	}
	
}
