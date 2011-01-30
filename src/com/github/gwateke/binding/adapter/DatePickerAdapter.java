package com.github.gwateke.binding.adapter;

import java.util.Date;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.datepicker.client.DatePicker;



public class DatePickerAdapter extends AbstractValueModelAdapter<Date> implements Binding<DatePicker> {

	private DatePicker datePicker;
	private HandlerRegistration handlerRegistration;
	
	
	public DatePickerAdapter(DatePicker datePicker, ValueModel<Date> valueModel) {
		super(valueModel);
		this.datePicker = datePicker;
		this.handlerRegistration = datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				adaptedValueChanged( event.getValue() );
			}
		});
	}
	
	
	@Override
	protected void valueModelValueChanged(Date newValue) {
		datePicker.setValue(newValue);		
	}

	
	public DatePicker getWidget() {
		return datePicker;
	}


	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}

}
