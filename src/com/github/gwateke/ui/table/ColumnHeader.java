package com.github.gwateke.ui.table;

import java.util.MissingResourceException;

import com.github.gwateke.context.Images;
import com.github.gwateke.context.MessageSource;
import com.github.gwateke.model.table.TableColumn;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;


/**
 * 
 */
public class ColumnHeader extends HorizontalPanel implements HasValueChangeHandlers<Boolean> {
	
	private String orderBy;
	private Boolean order;
	private Label label = new Label();
	private Image ascending;
	private Image descending;
	private MouseHighlighter mouseListener = new MouseHighlighter();
	
	
	public ColumnHeader(TableColumn column, MessageSource messageSource, Images images) {
		setStylePrimaryName("lotura-ColumnHeader");
		setVerticalAlignment(ALIGN_MIDDLE);
		
		this.orderBy = column.getOrderBy();

		try {
			label.setText( messageSource.getRequiredMessage( column.getId() + ".short" ) );
			label.setTitle( messageSource.getMessage( column.getId() ) );
		} 
		catch (MissingResourceException e) {
			label.setText( messageSource.getMessage( column.getId() ) );
			label.setTitle("");
		}

		setSortable( column.isSortable() );

		ascending = images.downArrow().createImage();
		descending = images.upArrow().createImage();			
				
		add(label);
	}
		
	
	public String getOrderBy() {
		return orderBy;
	}
	

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}


	public void setSortable(boolean sortable) {
		if (sortable) {
			addStyleDependentName("sortable");
			label.addMouseOverHandler(mouseListener);
			label.addMouseOutHandler(mouseListener);
		}
		else {
			removeStyleDependentName("sortable");
		}
	}
	
	
	public void toggle() {
		this.order = (this.order == null) ? Boolean.TRUE : !this.order;
		ValueChangeEvent.fire(this, order);
	}
	
	
	public void setOrder(Boolean order) {
		if (getWidgetCount() == 2) {
			remove(1);
		}
		if (order != null) {
			add( order ? ascending : descending );
		}
		this.order = order;
	}
	
	
	private class MouseHighlighter implements MouseOverHandler, MouseOutHandler {
		public void onMouseOver(MouseOverEvent event) {
			addStyleDependentName("sortable-hovering");
		}
		public void onMouseOut(MouseOutEvent event) {
			removeStyleDependentName("sortable-hovering");
		}
	}

}
