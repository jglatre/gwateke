package com.github.gwateke.ui.dialog;


import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.context.UiContext;
import com.github.gwateke.context.support.DefaultMessageSourceResolvable;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ErrorDialog extends AbstractModalDialog {
	
	private static final String TITLE = "dialog.error.title";
	
	private Closure<?,?> acceptClosure;
	private HTML content;

	
	public ErrorDialog(String messageKey, UiContext uiContext) {
		this( new DefaultMessageSourceResolvable(messageKey), uiContext );
	}

	
	public ErrorDialog(MessageSourceResolvable resolvable, UiContext uiContext) {
		super(TITLE, uiContext);
		
		String message = getMessageSource().getMessage(resolvable);
		if (message.trim().startsWith("<html>")) {
			content.setHTML(message);
		}
		else {
			content.setText(message);
		}
	}
	
	
	public void setAcceptAction(Closure<?,?> closure) {
		this.acceptClosure = closure;
	}

	
	@Override
	protected Widget createContentPanel() {
		content = new HTML();
		return content;
	}
	
	
	@Override
	protected Widget createButtonsPanel() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.setHeight("40px");

		Button accept = new Button();
		accept.setText( getMessageSource().getMessage("dialog.accept") );
		accept.addClickHandler( new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				if (acceptClosure != null) {
					acceptClosure.call(null);
				}
			}
		});
		panel.add(accept);

		return panel;
	}
}


