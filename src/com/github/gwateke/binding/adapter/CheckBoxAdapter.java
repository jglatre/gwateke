package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;


public class CheckBoxAdapter extends AbstractValueModelAdapter<Boolean> implements Binding<CheckBox>, ClickHandler {

	private CheckBox checkBox;
	private HandlerRegistration handlerRegistration;
	
	
	public CheckBoxAdapter(CheckBox checkBox, ValueModel<Boolean> valueModel) {
		super(valueModel);
		this.checkBox = checkBox;
		this.handlerRegistration = this.checkBox.addClickHandler(this);
		initalizeAdaptedValue();
	}
	
	
	protected void valueModelValueChanged(Boolean newValue) {
		checkBox.setValue(newValue != null ? newValue : false);
	}
	

	public void onClick(ClickEvent event) {
		adaptedValueChanged( checkBox.getValue() );
	}


	public CheckBox getWidget() {
		return checkBox;
	}

	
	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}

}
