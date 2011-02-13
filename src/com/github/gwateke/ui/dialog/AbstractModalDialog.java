package com.github.gwateke.ui.dialog;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_LEFT;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_TOP;

import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.context.ImageSource;
import com.github.gwateke.context.MessageSource;
import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.context.UiContext;
import com.github.gwateke.context.support.DefaultMessageSourceResolvable;
import com.github.gwateke.ui.Toolbar;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Clase base par los cuadros de diálogo modales.
 * 
 * @author juanjogarcia
 */
public abstract class AbstractModalDialog extends DialogBox {

	private final UiContext uiContext;
	

	private ActionCommand accept = new ActionCommand("accept") {
		@Override protected void doExecuteCommand() {
			onAccept();
		}		
	};
	
	private ActionCommand cancel = new ActionCommand("cancel") {
		@Override protected void doExecuteCommand() {
			onCancel();
		}		
	};

	
	public AbstractModalDialog(UiContext uiContext) {
		this( "title", uiContext );
	}
	
	
	public AbstractModalDialog(String titleKey, UiContext uiContext) {
		this( new DefaultMessageSourceResolvable(titleKey), uiContext );
	}
	
	
	public AbstractModalDialog(MessageSourceResolvable resolvableTitle, UiContext uiContext) {
		super(false, true);
		setText( uiContext.getMessageSource().getMessage(resolvableTitle) );
		
		this.uiContext = uiContext;
		
		HTMLTable table = new FlexTable(); 
		table.setWidget(0, 0, createContentPanel());
	    table.setWidget(1, 0, createButtonsPanel());
	    table.setSize("300px", "160px");
		table.getCellFormatter().setHeight(0, 0, "70%");
	    table.getCellFormatter().setWidth(0, 0, "100%");
	    table.getCellFormatter().setAlignment(0, 0, ALIGN_LEFT, ALIGN_TOP);		
		table.getCellFormatter().setHeight(1, 0, "30%");
	    table.getCellFormatter().setWidth(1, 0, "100%");
	    table.getCellFormatter().setAlignment(1, 0, ALIGN_CENTER, ALIGN_MIDDLE);		

	    setWidget(table);
	    setAnimationEnabled(true);
	    
	    accept.setEnabled(true);
	    accept.setVisible(true);
	    cancel.setEnabled(true);
	    cancel.setVisible(true);
	}


	public Widget getContentPanel() {
		HTMLTable table = (HTMLTable) super.getWidget();
		return table.getWidget(0, 0);
	}
	

	public Widget getButtonsPanel() {
		HTMLTable table = (HTMLTable) super.getWidget();
		return table.getWidget(1, 0);
	}
	
	//----------------------------------------------------------------------------------------------

	protected Widget createContentPanel() {
		return new SimplePanel();
	}


	protected Widget createButtonsPanel() {
		return new AcceptCancelButtons( getMessageSource().getMessage("dialog.accept"), 
				getMessageSource().getMessage("dialog.cancel") );
	}
	
	
	protected void onAccept() {
		hide();
	}
	
	
	protected void onCancel() {
		hide();
	}
	
	
	protected ImageSource getImages() {
		return uiContext.getImageSource();
	}
	
	
	protected MessageSource getMessageSource() {
		return uiContext.getMessageSource();
	}
	
	
	protected ActionCommand getAcceptAction() {
		return accept;
	}
	
	
	protected ActionCommand getCancelAction() {
		return cancel;
	}

	
	protected class AcceptCancelButtons extends Toolbar { 
		private Button accept = new Button();
		private Button cancel = new Button();

		public AcceptCancelButtons(String acceptText, String cancelText) {
			setHeight("45px");
		
			accept.setText(acceptText);
			accept.setWidth("auto");
			addItem(accept, getAcceptAction());
			
			cancel.setText(cancelText);
			cancel.setWidth("auto");
			addItem(cancel, getCancelAction());
		}
	}

}
