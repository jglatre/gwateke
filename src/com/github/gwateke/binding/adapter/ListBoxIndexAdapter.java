package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;


public class ListBoxIndexAdapter extends AbstractValueModelAdapter<Integer> implements Binding<ListBox>, ChangeHandler {

	private ListBox listBox;
	private HandlerRegistration handlerRegistration; 
	
	
	public ListBoxIndexAdapter(ListBox listBox, ValueModel<Integer> valueModel) {
		super(valueModel);
		this.listBox = listBox;
		handlerRegistration = this.listBox.addChangeHandler(this);
		initalizeAdaptedValue();
	}
	
	
	protected void valueModelValueChanged(Integer newValue) {
		listBox.setSelectedIndex(newValue); 
	}
	

	public void onChange(ChangeEvent event) {
		adaptedValueChanged( listBox.getSelectedIndex() );
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
