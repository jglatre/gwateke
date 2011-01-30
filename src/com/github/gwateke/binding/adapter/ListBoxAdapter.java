package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;


public class ListBoxAdapter extends AbstractValueModelAdapter<Object> implements Binding<ListBox>, ChangeHandler {

	private ListBox listBox;
	private HandlerRegistration handlerRegistration; 
	
	
	@SuppressWarnings("unchecked")
	public ListBoxAdapter(ListBox listBox, ValueModel<?> valueModel) {
		super((ValueModel<Object>) valueModel);
		this.listBox = listBox;
		handlerRegistration = this.listBox.addChangeHandler(this);
		initalizeAdaptedValue();
	}
	
	
	protected void valueModelValueChanged(Object newValue) {
		listBox.setSelectedIndex( findValue(newValue) ); 
	}
	

	public void onChange(ChangeEvent event) {
		adaptedValueChanged( listBox.getValue( listBox.getSelectedIndex() ) );
	}

	
	private int findValue(Object value) {
		String strValue = String.valueOf(value);		
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getValue(i).equals(strValue)) {
				return i;
			}
		}
		return -1;
	}


	public ListBox getWidget() {
		return listBox;
	}
	
	
	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}
	
}
