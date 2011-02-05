package com.github.gwateke.model.table.event;

import java.util.EventListener;

import com.github.gwateke.model.table.TableModel;




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
