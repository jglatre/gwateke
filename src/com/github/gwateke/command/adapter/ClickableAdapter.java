package com.github.gwateke.command.adapter;

import java.util.Map;

import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;


public class ClickableAdapter<W extends HasClickHandlers> extends AbstractActionCommandBinding<W> {

	private HandlerRegistration handlerRegistration;


	public ClickableAdapter(W widget, ActionCommand command) {
		this(widget, command, null);
	}
	
	
	public ClickableAdapter(W widget, ActionCommand command, Closure<Map<?, ?>, ?> parameterProvider) {
		super(widget, command, parameterProvider);

		handlerRegistration = getWidget().addClickHandler( new ClickHandler() {
			public void onClick(ClickEvent event) {
				executeCommand();
			}
		});
	}


	@Override
	public void unbind() {
		super.unbind();
		handlerRegistration.removeHandler();
	}

	
	@Override
	protected void actionCommandChanged(ActionCommand command) {
	}

}
