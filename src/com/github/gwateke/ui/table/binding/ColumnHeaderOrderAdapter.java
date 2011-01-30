package com.github.gwateke.ui.table.binding;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.github.gwateke.data.query.Order;
import com.github.gwateke.ui.table.ColumnHeader;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * Binding between a <code>ColumnHeader</code> and a <code>ValueModel<Order></code>. 
 *
 */
public class ColumnHeaderOrderAdapter extends AbstractValueModelAdapter<Order> implements ValueChangeHandler<Boolean> {

	private ColumnHeader columnHeader;
	private HandlerRegistration handlerRegistration;

	
	public ColumnHeaderOrderAdapter(ColumnHeader columnHeader, ValueModel<Order> valueModel) {
		super(valueModel);
		this.columnHeader = columnHeader;
		this.handlerRegistration = this.columnHeader.addValueChangeHandler(this);
		initalizeAdaptedValue();
	}

	
	protected void valueModelValueChanged(Order newValue) {
		boolean sameColumn = newValue != null && newValue.getField().equals(columnHeader.getOrderBy());
		columnHeader.setOrder(sameColumn ? newValue.isAscending() : null);
	}
	

	public void onValueChange(ValueChangeEvent<Boolean> event) {
		adaptedValueChanged( new Order(columnHeader.getOrderBy(), event.getValue()) );
	}


	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}

}
