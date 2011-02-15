package com.github.gwateke.ui;


import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.command.adapter.MenuItemAdapter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;


/**
 * A ToolbarButton that displays a PopupMenu when clicked.
 * 
 * @author juanjogarcia
 */
public class PopupMenuButton extends ToolbarButton {
	
	private MenuBar menu = new MenuBar(true);
	private PopupPanel panel = new PopupPanel(true);
	private PopupPanel.PositionCallback positionCallback = new PopupPanel.PositionCallback() {
		public void setPosition(int offsetWidth, int offsetHeight) {
			final int left = getAbsoluteLeft();
			final int top = getAbsoluteTop() + getOffsetHeight();
			panel.setPopupPosition(left, top);
		}
	};
	
	
	public PopupMenuButton(AbstractImagePrototype prototype, String text) {
		super(prototype, text);
		
		menu.setStyleName("popupMenu");
		panel.setWidget(menu);
		panel.setAnimationEnabled(true);
		addClickHandler( new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.setPopupPositionAndShow(positionCallback);			
			}
		} );
	}
	
	
	public MenuItem addItem(String text, ActionCommand command) {
		return addItem( new MenuItem(text, (Command) null), command );
	}
	
	
	public MenuItem addItem(MenuItem item, ActionCommand command) {
		new MenuItemAdapter(item, command);
		return menu.addItem(item);
	}
	
	
	public MenuItem addItem(String text, Command command) {
		return menu.addItem(text, command);
	}		
}
