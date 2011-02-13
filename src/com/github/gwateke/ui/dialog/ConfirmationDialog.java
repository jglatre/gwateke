package com.github.gwateke.ui.dialog;


import com.github.gwateke.context.UiContext;
import com.github.gwateke.model.event.ConfirmationEvent;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * 
 * @author juanjo
 */
public class ConfirmationDialog extends AbstractModalDialog { 
	
	private Label label;
	private ConfirmationEvent event;
	
	
	public ConfirmationDialog(ConfirmationEvent event, UiContext uiContext) {
		super("dialog.confirmation.title", uiContext);
		this.event = event;
		label.setText( getMessageSource().getMessage( event.getMessageSourceResolvable() ) );
	}
	
	
	@Override
	protected void onAccept() {
		event.accept();
		hide();
	}
	
	
	@Override
	protected Widget createContentPanel() {
		label = new Label();
		return new FocusPanel(label);
	}
	
	
	@Override
	protected Widget createButtonsPanel() {
		return new AcceptCancelButtons( getMessageSource().getMessage("dialog.yes"), 
				getMessageSource().getMessage("dialog.no") );
	}
}
