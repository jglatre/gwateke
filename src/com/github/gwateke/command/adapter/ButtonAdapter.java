package com.github.gwateke.command.adapter;

import java.util.Map;

import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.user.client.ui.Button;


public class ButtonAdapter extends ClickableAdapter<Button> {
	
	public ButtonAdapter(Button button, ActionCommand command) {
		this(button, command, null);
	}
	
	
	public ButtonAdapter(Button button, ActionCommand command, Closure<Map<?,?>, ?> parameterProvider) {
		super(button, command, parameterProvider);
	}
	

	@Override
	protected void actionCommandChanged(ActionCommand command) {
		super.actionCommandChanged(command);
		
		Button button = getWidget();
		button.setEnabled( command.isEnabled() );
		button.setVisible( command.isVisible() );
		// TODO icono, texto, tooltip, etc.
	}
	
}
