package com.github.gwateke.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;


/**
 * Grupo de RadioButtons que permite asociar un valor a cada uno de ellos.
 * 
 * @author juanjogarcia
 */
public class RadioButtonGroup extends Composite implements HasName, HasValue<Object> {
	
	private String name;
	private FlowPanel groupPanel = new FlowPanel();
	
	
	public RadioButtonGroup(String name) {
		this.name = name;
		initWidget(groupPanel);
	}
	
	
	/**
	 * Añade un nuevo elemento al grupo.
	 * 
	 * @param label etiqueta del RadioButton.
	 * @param value valor asociado.
	 */
	public void addItem(String label, Object value) {
		groupPanel.add( new ValuedRadioButton(name, label, value) );
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Obtiene el valor asociado al RadioButton seleccionado.
	 */
	public Object getValue() {
		for (Widget w: groupPanel) {
			if (w instanceof ValuedRadioButton && ((RadioButton) w).getValue()) {
				return ((ValuedRadioButton) w).value;
			}
		}
		return null;
	}
	
	
	/**
	 * Selecciona un RadioButton a partir de su valor asociado. 
	 */
	public void setValue(Object value) {
		setValue(value, false);
	}
	
	
	public void setValue(Object value, boolean fireEvents) {
		Object oldValue = getValue();
		if (value.equals(oldValue)) {
			return;
		}

		for (Widget w: groupPanel) {
			if (w instanceof ValuedRadioButton && ((ValuedRadioButton) w).value.equals(value)) {
				((RadioButton) w).setValue(true);
			}
		}
		
		if (fireEvents) {
			ValueChangeEvent.fire(this, value);
		}
	}


	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Object> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}
	
	
	/**
	 * RadioButton con valor asociado. Además lanza un evento de cambio de valor del grupo cada
	 * vez que es seleccionado.
	 */
	private class ValuedRadioButton extends RadioButton {
		final Object value;
		
		public ValuedRadioButton(String name, String label, Object value) {
			super(name, label);
			this.value = value;
			
			addValueChangeHandler( new ValueChangeHandler<Boolean>() {
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) {
						ValueChangeEvent.fire(RadioButtonGroup.this, ValuedRadioButton.this.value);
					}
				}				
			});
		}
	}

}
