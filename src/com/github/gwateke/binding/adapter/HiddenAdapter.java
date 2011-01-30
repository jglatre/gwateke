package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.user.client.ui.Hidden;


public class HiddenAdapter extends AbstractValueModelAdapter<String> implements Binding<Hidden> {

	private Hidden hidden;
	
	
	public HiddenAdapter(Hidden hidden, ValueModel<String> valueModel) {
		super(valueModel);
		this.hidden = hidden;
		initalizeAdaptedValue();
	}
	

	protected void valueModelValueChanged(String newValue) {
		hidden.setValue(newValue != null ? newValue : "");
	}
	

	public Hidden getWidget() {
		return hidden;
	}

}
