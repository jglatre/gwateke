package com.github.gwateke.ui.form;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_BACKSPACE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DOWN;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_END;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_HOME;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_LEFT;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_PAGEDOWN;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_PAGEUP;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_RIGHT;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_TAB;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_UP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBoxBase;


public abstract class KeyboardFilter implements KeyDownHandler, KeyPressHandler {

	protected final Logger logger = Logger.getLogger( getClass().getName() );

	private static final Set<Integer> CONTROL_KEYS = new HashSet<Integer>( Arrays.asList(new Integer[] { 
			KEY_BACKSPACE, KEY_DELETE, KEY_DOWN, KEY_END, KEY_ENTER, KEY_ESCAPE, KEY_HOME, KEY_LEFT, 
			KEY_PAGEDOWN, KEY_PAGEUP, KEY_RIGHT, KEY_TAB, KEY_UP
	}) );
	
	private boolean skipKeyPress;
	
	
	public void onKeyDown(KeyDownEvent event) {
		skipKeyPress = isControlKey( event.getNativeKeyCode() );
	}
	
	
	public void onKeyPress(KeyPressEvent event) {
		if (skipKeyPress) {
            return;
        }
		
		final TextBoxBase textBox = (TextBoxBase) event.getSource();
		if (!isValid(textBox, event.getCharCode())) {
			textBox.cancelKey();
		}
	}

	
	protected boolean isControlKey(int keyCode) {
		return CONTROL_KEYS.contains(keyCode);
	}
	
	
	protected abstract boolean isValid(HasValue<String> widget, char keyCode);
}
