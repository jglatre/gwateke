package com.github.gwateke.command.adapter;

import java.util.Map;

import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.UIObject;


/**
 * Asocia una pulsación de teclado sobre un Widget con la ejecución de un comando.
 * 
 * @author juanjogarcia
 */
public class KeyPressAdapter extends AbstractActionCommandBinding<HasKeyPressHandlers> { 

	private final int keyCode;

	public KeyPressAdapter(HasKeyPressHandlers widget, ActionCommand command, int keyCode) {
		this(widget, command, null, keyCode);
	}
	
	
	public KeyPressAdapter(HasKeyPressHandlers widget, ActionCommand command, Closure<Map<?,?>, ?> parameterProvider, int keyCode) {
		super(widget, command, parameterProvider);
		this.keyCode = keyCode;
		
		widget.addKeyPressHandler( new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (KeyPressAdapter.this.keyCode == event.getCharCode()) {
					executeCommand();
				}				
			}
		} );
	}
	

	protected void actionCommandChanged(ActionCommand command) {
		if (getWidget() instanceof UIObject) {
			UIObject widget = (UIObject) getWidget();
//			widget.setEnabled( command.isEnabled() );
			widget.setVisible( command.isVisible() );
		}
		// TODO icono, texto, tooltip, etc.
	}

}
