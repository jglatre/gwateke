package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBoxBase;


public class TextBoxAdapter extends AbstractValueModelAdapter<String> implements Binding<TextBoxBase>, ChangeHandler, KeyPressHandler {

	private TextBoxBase textBox;
	
	
	public TextBoxAdapter(TextBoxBase textBox, ValueModel<String> valueModel) {
		super(valueModel);
		this.textBox = textBox;
		this.textBox.addChangeHandler(this);
		this.textBox.addKeyPressHandler(this);
		
		initalizeAdaptedValue();
	}
	

	protected void valueModelValueChanged(String newValue) {
		textBox.setText( newValue != null ? newValue : "" );
	}

	
	public void onChange(ChangeEvent event) {
		adaptedValueChanged( textBox.getText() );
	}

	
	/**
	 * Es necesario actualizar el buffer en cada pulsación para el caso de que se
	 * produzca un "commit" sin pérdida de foco (p.ej. tecla ENTER).
	 */
	public void onKeyPress(KeyPressEvent event) {
		adaptedValueChanged( textBox.getText() );
	}


	public TextBoxBase getWidget() {
		return textBox;
	}

}
