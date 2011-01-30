package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.user.client.ui.Label;


public class LabelAdapter extends AbstractValueModelAdapter<String> implements Binding<Label> {

	private Label label;
	
	
	public LabelAdapter(Label label, ValueModel<String> valueModel) {
		super(valueModel);
		this.label = label;
		initalizeAdaptedValue();
	}
	

	protected void valueModelValueChanged(String newValue) {
		label.setText( newValue != null ? newValue : "");
	}
	

	public Label getWidget() {
		return label;
	}

}
