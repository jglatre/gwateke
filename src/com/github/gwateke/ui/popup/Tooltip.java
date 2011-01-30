package com.github.gwateke.ui.popup;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


public class Tooltip extends TimerHiddingPopupPanel {
	
	private static final String DEFAULT_STYLE = "tooltip";
	
	
	public Tooltip(int absoluteX, int absoluteY, final String text) {
		this(absoluteX, absoluteY, text, DELAY_FOREVER);
	}
	
	
	public Tooltip(int absoluteX, int absoluteY, final String text, final int delay) {
		this(null, absoluteX, absoluteY, text, delay, DEFAULT_STYLE);
	}
	
	
	public Tooltip(Widget sender, int offsetX, int offsetY, 
			final String text, final int delay, final String styleName) {
		
		super(delay);

		setWidget( new HTML(text) );
		
		int left = offsetX;
		int top = offsetY;

		if (sender != null){
			left += sender.getAbsoluteLeft();
			top += sender.getAbsoluteTop();
		}
		setPopupPosition(left, top);
		setStyleName(styleName);	
		setAnimationEnabled(true);
	}

	
	public void setText(String text) {
		((HTML) getWidget()).setHTML(text);
	}
}