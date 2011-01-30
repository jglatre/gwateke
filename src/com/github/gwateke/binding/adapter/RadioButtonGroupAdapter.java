package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.github.gwateke.ui.RadioButtonGroup;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;


public class RadioButtonGroupAdapter extends AbstractValueModelAdapter<Object>
		implements Binding<RadioButtonGroup>, ValueChangeHandler<Object> {

	private RadioButtonGroup group;
	private HandlerRegistration handlerRegistration; 

	
	@SuppressWarnings("unchecked")
	public RadioButtonGroupAdapter(RadioButtonGroup group, ValueModel<?> valueModel) {
		super((ValueModel<Object>) valueModel);
		this.group = group;
		handlerRegistration = this.group.addValueChangeHandler(this);
		initalizeAdaptedValue();
	}
	

	@Override
	protected void valueModelValueChanged(Object newValue) {
		group.setValue(newValue);
	}

	
	public void onValueChange(ValueChangeEvent<Object> event) {
		adaptedValueChanged( event.getValue() );
	}

	
	public RadioButtonGroup getWidget() {
		return group;
	}
	
	
	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}	
}
