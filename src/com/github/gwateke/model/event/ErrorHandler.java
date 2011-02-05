package com.github.gwateke.model.event;

import com.google.gwt.event.shared.EventHandler;


public interface ErrorHandler extends EventHandler {

	void onError(ErrorEvent event);
}
