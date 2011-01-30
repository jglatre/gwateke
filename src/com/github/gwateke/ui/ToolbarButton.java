package com.github.gwateke.ui;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;


public class ToolbarButton extends Button {

	public ToolbarButton(AbstractImagePrototype prototype) {
		setHTML("<table><tr><td>" + prototype.getHTML() + "</td></tr></table>");
	}

	
	public ToolbarButton(AbstractImagePrototype prototype, String text) {
		setHTML("<table cellpadding='0' cellspacing='2'><tr>" +
				"<td>" + prototype.getHTML() + "</td>" +
				"<td>" + text + "</td>" +
				"</tr></table>");
	}
	
	
	@Override
	public void setEnabled(boolean b) {
		if (b) {
			removeStyleDependentName("disabled");
//			setStyleName("gwt-Button");
			super.setEnabled(true);
		}
		else {
			addStyleDependentName("disabled");
			super.setEnabled(false);
		}
	}

}
