package com.github.gwateke.ui;

import com.github.gwateke.context.UiContext;
import com.github.gwateke.model.event.ConfirmationEvent;
import com.github.gwateke.model.event.ConfirmationHandler;
import com.github.gwateke.ui.dialog.ConfirmationDialog;


/**
 * Gestor de eventos de confirmación que muestra un cuadro de diálogo.
 * 
 * @author juanjogarcia
 */
public class UserConfirmationHandler implements ConfirmationHandler {		

	private final UiContext uiContext;

	
	public UserConfirmationHandler(UiContext uiContext) {
		this.uiContext = uiContext;
	}


	public void onConfirmation(ConfirmationEvent event) {
		new ConfirmationDialog( event, uiContext ).center();
	}
}