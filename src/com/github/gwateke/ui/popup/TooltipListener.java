package com.github.gwateke.ui.popup;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Widget;


public class TooltipListener implements MouseOverHandler, MouseOutHandler {

	private Tooltip tooltip;
	private String text;
	private String styleName;
	private int delay;

	
	public TooltipListener(String text, int delay, String styleName) {
		this.text = text;
		this.delay = delay;
		this.styleName = styleName;
	}


	public void onMouseOver(MouseOverEvent event) {
		if (tooltip != null) {
			tooltip.hide();
		}

		Widget sender = (Widget) event.getSource();
		int offsetX = new Double(sender.getOffsetWidth() * 0.666666d).intValue();
		int offsetY = sender.getOffsetHeight();
		tooltip = new Tooltip(sender, offsetX, offsetY, text, delay, styleName);
		tooltip.show();
	}


	public void onMouseOut(MouseOutEvent event) {
		if (tooltip != null) {
			tooltip.hide();
		}
	}

}
