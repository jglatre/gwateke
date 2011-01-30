package com.github.gwateke.ui.popup;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;


public class InfoPopup extends TimerHiddingPopupPanel {
	
	private static final int POPUP_WIDTH = 225;
	private static final int POPUP_HEIGHT = 75;
	private static final int POPUP_DELAY = 2500;


	public InfoPopup(final String text) {
		this(text, POPUP_DELAY);
		setAnimationEnabled(true);
	}
	
	
	public InfoPopup(final String text, final int delay) {
		super(delay);

		int offsetX = Window.getClientWidth() - POPUP_WIDTH - 10;
		int offsetY = Window.getClientHeight() - POPUP_HEIGHT - 10 - (1);
		
		Label label = new Label();
		Label label2 = new Label();
		Label label3 = new Label();
		FlexTable pan = new FlexTable();
	
		label.setText(text);
		pan.setWidget(0, 0, label2);
		pan.setWidget(1, 0, label);
		pan.setWidget(2, 0, label3);
		label2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		label.setStyleName("content");
		
		setPopupPosition(offsetX, offsetY);
		setPixelSize(POPUP_WIDTH, POPUP_HEIGHT);
		setStyleName("infoPanel");
		add(pan);	
	}
	
}
