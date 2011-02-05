package com.github.gwateke.model.list.event;

import com.google.gwt.event.shared.EventHandler;


public interface ListModelChangeHandler extends EventHandler {

	void onChange(ListModelChangeEvent event);
}
