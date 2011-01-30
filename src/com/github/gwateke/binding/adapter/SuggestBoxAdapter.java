package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;


public class SuggestBoxAdapter extends AbstractValueModelAdapter<String> implements Binding<SuggestBox>, SelectionHandler<Suggestion> {

	private SuggestBox suggestBox;
	private HandlerRegistration handlerRegistration;
	private Binding<TextBoxBase> textBoxBinding;
	
	
	public SuggestBoxAdapter(SuggestBox suggestBox, ValueModel<String> valueModel) {
		super(valueModel);
		this.suggestBox = suggestBox;
		this.handlerRegistration = this.suggestBox.addSelectionHandler(this);		
		this.textBoxBinding = new TextBoxAdapter( suggestBox.getTextBox(), valueModel );
		
		// no hace falta el initalizeAdaptedValue() porque ya lo hace el TextBoxAdapter
	}
	

	protected void valueModelValueChanged(String newValue) {
		suggestBox.setText( newValue != null ? newValue : "" );
	}


	public void onSelection(SelectionEvent<Suggestion> event) {
		adaptedValueChanged( event.getSelectedItem().getReplacementString() );
	}


	public SuggestBox getWidget() {
		return suggestBox;
	}

	
	@Override
	public void unbind() {
		handlerRegistration.removeHandler();
		textBoxBinding.unbind();
		super.unbind();
	}
}
