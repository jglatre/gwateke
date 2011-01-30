package com.github.gwateke.command.adapter;


import com.github.gwateke.command.ActionCommand;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;



public class MenuItemAdapter extends AbstractActionCommandBinding<MenuItem> {

	
	public MenuItemAdapter(MenuItem menuItem, ActionCommand command) {
		super(menuItem, command, null);
		
		getWidget().setCommand( new Command() {
			public void execute() {
				executeCommand();
			}
		} );
	}


	protected void actionCommandChanged(ActionCommand command) {
		getWidget().setVisible( command.isVisible() );
		// TODO icono, texto, tooltip, etc.
	}
	
}
