package com.github.gwateke.ui.form.support;

import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;


public class BindableSuggestBox extends SuggestBox implements HasName, HasAllKeyHandlers {

	public BindableSuggestBox() {
		super();
	}
	
	
	public BindableSuggestBox(SuggestOracle oracle) {
		super(oracle);
	}
	

	public String getName() {
		return getTextBox().getName();
	}


	public void setName(String name) {
		getTextBox().setName(name);
	}


	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return getTextBox().addKeyUpHandler(handler);
	}


	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return getTextBox().addKeyDownHandler(handler);
	}


	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return getTextBox().addKeyPressHandler(handler);
	}
}
