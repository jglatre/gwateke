package com.github.gwateke.text;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractDerivedValueModel;



public class MessageFormatValueModel extends AbstractDerivedValueModel<Object, String> {

	private String pattern;
	private String value;

	
	public MessageFormatValueModel(String pattern, ValueModel<Object>... sourceValueModels) {
		super(sourceValueModels);
		this.pattern = pattern;
		refreshValue();
	}		

	
	protected void sourceValuesChanged(ValueModel<Object> source) {
		refreshValue();
	}

	
	public String getValue() {
		return value;
	}

	
	private void refreshValue() {
		Object oldValue = this.value;
		this.value = MessageFormat.format(pattern, getSourceValues());
		fireValueChange(oldValue, this.value);
	}
}