package com.github.gwateke.model.event;

import com.google.gwt.event.shared.EventHandler;


public interface ActionHandler extends EventHandler {
	
	void onAction(ActionEvent event);
}
