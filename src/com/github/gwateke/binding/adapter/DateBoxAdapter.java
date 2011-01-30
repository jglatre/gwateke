package com.github.gwateke.binding.adapter;

import java.util.Date;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.datepicker.client.DateBox;


public class DateBoxAdapter extends AbstractValueModelAdapter<Date> implements Binding<DateBox> {

	private final DateBox dateBox;
	private HandlerRegistration handlerRegistration;
	
	
	public DateBoxAdapter(DateBox dateBox, ValueModel<Date> valueModel) {
		super(valueModel);
		this.dateBox = dateBox;
		this.handlerRegistration = dateBox.addValueChangeHandler( new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				adaptedValueChanged( event.getValue() );
			}
		} );
		
		initalizeAdaptedValue();
	}
	

	@Override
	protected void valueModelValueChanged(Date newValue) {
		dateBox.setValue(newValue);
	}

	
	public DateBox getWidget() {
		return dateBox;
	}

	
	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}

}
