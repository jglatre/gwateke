package com.github.gwateke.model.table;


import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.data.query.Order;
import com.github.gwateke.model.table.event.TableModelListener;



/**
 * Data model for <code>Table</code> component.
 * 
 * @author juanjo
 */
public interface TableModel<T> extends Pageable {

	/**
	 * @return number of rows.
	 */
	int getRowCount();
	
	/**
	 * @return number of columns.
	 */
	int getColumnCount();
	
	TableColumn getTableColumn(int column);
	
    T getRowValue(int row);
	
	ValueModel<Criterion> getFilterModel();
	ValueModel<Order> getOrderModel();	
	
	void addListener(TableModelListener listener);
	void removeListener(TableModelListener listener);
}
