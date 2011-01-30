package com.github.gwateke.ui.list;

import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.command.adapter.ClickableAdapter;
import com.github.gwateke.ui.HoveringStyler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class HtmlListBox extends VerticalPanel {

	private AbstractImagePrototype defaultIcon;
	private Widget lastSelected;
	
	
	public HtmlListBox() {
		setVerticalAlignment(ALIGN_MIDDLE);
	}
	
	
	public void setDefaultIcon(AbstractImagePrototype defaultIcon) {
		this.defaultIcon = defaultIcon;
	}
	
	
	public Label add(String text) {	
		return add(defaultIcon, text);
	}
	
	
	public Label add(AbstractImagePrototype icon, String text) {
		String html = "<table cellpadding='0' cellspacing='2'><tr>";
		if (icon != null) {
			html += "<td>" + icon.getHTML() + "</td>";
		}
		html += "<td>" + text + "</td></tr></table>";
		
		HTML label = new HTML(html);		
		label.addMouseListener( new HoveringStyler() );
		add(label);
		
		return label;
	}
	
	
	public Label add(AbstractImagePrototype icon, String text, ActionCommand command) {
		Label label = add(icon, text);
		new ClickableAdapter<Label>( label, command );
		return label;
	}
	
	
	public void setSelected(Widget widget) {
		if (widget != null) {
			widget.addStyleDependentName("selected");
		}
		if (lastSelected != null && lastSelected != widget) {
			lastSelected.removeStyleDependentName("selected");
		}
		lastSelected = widget;
	}
}