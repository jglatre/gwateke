package com.github.gwateke.ui.table.model;

import java.util.EventListener;




/**
 * Interface que han de implementar los componentes interesados en 
 * recibir eventos de un <code>TableModel</code>.
 * 
 * @author juanjo
 */
public interface TableModelListener extends EventListener {

	void onDataChanging(TableModel<?> sender);
	void onDataChanged(TableModel<?> sender);
	void onPageSizeChanged(TableModel<?> sender);
	void onDataFilteringOrSorting(TableModel<?> sender);
}
