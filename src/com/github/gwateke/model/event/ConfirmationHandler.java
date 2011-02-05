package com.github.gwateke.model.event;

import com.google.gwt.event.shared.EventHandler;


public interface ConfirmationHandler extends EventHandler {

	void onConfirmation(ConfirmationEvent event);
}
