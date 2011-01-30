package com.github.gwateke.binding.adapter;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.user.client.ui.FocusWidget;


public class FocusWidgetEnablementAdapter extends AbstractValueModelAdapter<Boolean> {

	private FocusWidget focusWidget;
	
	
	public FocusWidgetEnablementAdapter(FocusWidget focusWidget, ValueModel<Boolean> valueModel) {
		super(valueModel);
		this.focusWidget = focusWidget;
		initalizeAdaptedValue();		
	}

	
	@Override
	protected void valueModelValueChanged(Boolean newValue) {
		focusWidget.setEnabled( newValue != null ? newValue : false );
	}

}
