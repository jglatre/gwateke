package com.github.gwateke.ui.popup;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PopupPanel;


public class TimerHiddingPopupPanel extends PopupPanel {

	public static final int DELAY_FOREVER = 0;
	
	private final int delay;
	
	
	public TimerHiddingPopupPanel(int delay) {
		super(true);
		this.delay = delay;
	}

	
	public void show() {
		super.show();

		if (delay > DELAY_FOREVER) {
			new Timer() {
				public void run() {
					hide();
				}
			}.schedule(delay);
		}		
	}
}
