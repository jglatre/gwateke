package com.github.gwateke.ui.table.model;


import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.data.query.Order;



/**
 * Modelo de datos para un componente <code>Table</code>.
 * 
 * @author juanjo
 */
public interface TableModel<T> extends Pageable {

	int getRowCount();
	int getColumnCount();
	
	TableColumn getTableColumn(int column);
	
    T getRowValue(int row);
	
	ValueModel<Criterion> getFilterModel();
	ValueModel<Order> getOrderModel();	
	
	void addListener(TableModelListener listener);
	void removeListener(TableModelListener listener);
}
