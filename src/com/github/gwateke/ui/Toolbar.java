package com.github.gwateke.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.command.adapter.ButtonAdapter;
import com.github.gwateke.command.adapter.KeyPressAdapter;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;


/**
 * Barra de botones genérica. 
 * 
 * @author juanjo
 */
public class Toolbar extends FlowPanel {
	
	private FlexTable flex = new FlexTable();	
	private MouseListener hoveringStyler = new HoveringStyler();
	private boolean vertical = false;
	private int cellCount = 0;
	private List<Binding<?>> bindings = new ArrayList<Binding<?>>();

	
	public Toolbar() {		
		super.add(flex);
	}
	
	
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}
	
	
	public void addItem(Widget widget) {
		if (widget instanceof Button) {
			FocusPanel panel = new FocusPanel(widget);
			panel.setStylePrimaryName("toolbarItem");
			panel.addMouseListener(hoveringStyler);
			widget = panel;
		}
		
		if (vertical) {
			flex.setWidget(cellCount++, 0, widget);			
		}
		else {
			flex.setWidget(0, cellCount++, widget);
		}
	}
	
	
	public void addItem(TextBoxBase widget, ActionCommand command, Closure<Map<?,?>, ?> parameterProvider) {
		bindings.add( new KeyPressAdapter(widget, command, parameterProvider, KeyCodes.KEY_ENTER) );
		addItem(widget);
	}
	
	
	public void addItem(Button button, ActionCommand command) {
		bindings.add( new ButtonAdapter(button, command) );
		addItem(button);
	}

	
	public void addItem(Button button, ActionCommand command, Closure<Map<?,?>, ?> parameterProvider) {
		bindings.add( new ButtonAdapter(button, command, parameterProvider) );
		addItem(button);
	}
}
