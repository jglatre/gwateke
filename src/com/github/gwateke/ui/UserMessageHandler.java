package com.github.gwateke.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.gwateke.context.UiContext;
import com.github.gwateke.model.event.MessageEvent;
import com.github.gwateke.model.event.MessageHandler;
import com.github.gwateke.ui.dialog.ErrorDialog;
import com.github.gwateke.ui.popup.InfoPopup;



public class UserMessageHandler implements MessageHandler {

	protected final Logger log = Logger.getLogger( getClass().getName() );

	private UiContext uiContext;
//	private LoadingPopup loadingPopup = new LoadingPopup();
	
	
	public UserMessageHandler(UiContext uiContext) {
		this.uiContext = uiContext;
	}

	
	public void onMessage(MessageEvent event) {
		switch (event.getMessageType()) {
		case AUTO:
			new InfoPopup( resolveMessage(event) ).show();
			break;
		case OPEN:
//			loadingPopup.show( resolveMessage(event) );
			break;
		case CLOSE:
//			loadingPopup.close();
			break;
		case MODAL:
			new ErrorDialog( event.getMessageSourceResolvable(), uiContext ).center();
			break;
		default:
			log.log(Level.SEVERE, "Unknown event type: " + event.getMessageType());
		}
	}

	
	protected String resolveMessage(MessageEvent event) {
		return uiContext.getMessageSource().getMessage( event.getMessageSourceResolvable() );
	}
}
